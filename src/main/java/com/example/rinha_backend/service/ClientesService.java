package com.example.rinha_backend.service;

import com.example.rinha_backend.domain.entities.UltimasTransacoes;
import com.example.rinha_backend.domain.entities.Usuarios;
import com.example.rinha_backend.dto.extrato.ExtratoResponse;
import com.example.rinha_backend.dto.extrato.Saldo;
import com.example.rinha_backend.dto.extrato.UltimasTransacoesDto;
import com.example.rinha_backend.dto.transacoes.TransacoesReponse;
import com.example.rinha_backend.dto.transacoes.TransacoesRequest;
import com.example.rinha_backend.utils.DateUtils;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.ResourceAccessException;

@Service
public class ClientesService {
    @Autowired
    private UsuariosService usuariosService;
    @Autowired
    private UltimasTransacoesService ultimasTransacoesService;

    @Transactional
    public TransacoesReponse processTransacao(Long id, TransacoesRequest body){
        body.validateBodyParameters();

        Usuarios usuarios = usuariosService.findById(id)
                                           .orElseThrow(() -> new ResourceAccessException("usuário não encontrado"));;

        usuarios = doTransacao(usuarios, body);

        TransacoesReponse transacoesReponse = new TransacoesReponse(usuarios.getLimite(), usuarios.getSaldo());

        return transacoesReponse;
    }

    public ExtratoResponse processExtrato(Long id){
        Usuarios usuarios = usuariosService.findById(id)
                                           .orElseThrow(() -> new ResourceAccessException("usuário não encontrado"));

        List<UltimasTransacoes> ultimasTransacoes = ultimasTransacoesService.findTop10ByUsuarioIdOrderByRealizadaEmDesc(id);

        Saldo saldo = new Saldo(usuarios.getSaldo(), DateUtils.formatLocalDateTimeToString(LocalDateTime.now()), usuarios.getLimite());

        List<UltimasTransacoesDto> ultimasTransacoesDto = new ArrayList<>();
        ultimasTransacoes.forEach(ut -> ultimasTransacoesDto.add(new UltimasTransacoesDto(ut.getValor(), ut.getTipo(), ut.getDescricao(), DateUtils.formatLocalDateTimeToString(ut.getRealizadaEm()))));

        return new ExtratoResponse(saldo, ultimasTransacoesDto);
    }

    private Usuarios doTransacao(Usuarios usuarios, TransacoesRequest body) {
        if(body.getTipo().equals("c")){
            usuarios.setSaldo(usuarios.getSaldo() + body.getValor().longValue());
        } else if (body.getTipo().equals("d")) {
            usuarios.setSaldo(usuarios.getSaldo() - body.getValor()
                                                        .longValue());
        }

        if(usuarios.getLimite() + usuarios.getSaldo() < 0){
            throw new IllegalArgumentException("saldo insuficiente");
        }

        Long ultimasTranscoesId = generateUltimasTransacoesLastId(usuarios.getId());
        UltimasTransacoes ultimasTransacoes = new UltimasTransacoes(ultimasTranscoesId, usuarios.getId(), body.getValor().longValue(), body.getTipo(), body.getDescricao(), LocalDateTime.now());
        ultimasTransacoesService.save(ultimasTransacoes);

        return usuariosService.save(usuarios);
    }

    private Long generateUltimasTransacoesLastId(Long usuarioId) {
        UltimasTransacoes ultimasTransacoes = ultimasTransacoesService.findFirstByUsuarioIdOrderByIdDesc(usuarioId);
        return ultimasTransacoes == null ? 1 : ultimasTransacoes.getId() + 1;
    }
}
