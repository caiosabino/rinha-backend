package com.example.rinha_backend.service;

import com.example.rinha_backend.domain.entities.UltimasTransacoes;
import com.example.rinha_backend.domain.entities.Clientes;
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

        Clientes clientes = usuariosService.findById(id)
                                           .orElseThrow(() -> new ResourceAccessException("usuário não encontrado"));;

        clientes = doTransacao(clientes, body);

        TransacoesReponse transacoesReponse = new TransacoesReponse(clientes.getLimite(), clientes.getSaldo());

        return transacoesReponse;
    }

    public ExtratoResponse processExtrato(Long id){
        Clientes clientes = usuariosService.findById(id)
                                           .orElseThrow(() -> new ResourceAccessException("usuário não encontrado"));

        List<UltimasTransacoes> ultimasTransacoes = ultimasTransacoesService.findTop10ByUsuarioIdOrderByRealizadaEmDesc(id);

        Saldo saldo = new Saldo(clientes.getSaldo(), DateUtils.formatLocalDateTimeToString(LocalDateTime.now()), clientes.getLimite());

        List<UltimasTransacoesDto> ultimasTransacoesDto = new ArrayList<>();
        ultimasTransacoes.forEach(ut -> ultimasTransacoesDto.add(new UltimasTransacoesDto(ut.getValor(), ut.getTipo(), ut.getDescricao(), DateUtils.formatLocalDateTimeToString(ut.getRealizadaEm()))));

        return new ExtratoResponse(saldo, ultimasTransacoesDto);
    }

    private Clientes doTransacao(Clientes clientes, TransacoesRequest body) {
        if(body.getTipo().equals("c")){
            clientes.setSaldo(clientes.getSaldo() + body.getValor().longValue());
        } else if (body.getTipo().equals("d")) {
            clientes.setSaldo(clientes.getSaldo() - body.getValor()
                                                        .longValue());
        }

        if(clientes.getLimite() + clientes.getSaldo() < 0){
            throw new IllegalArgumentException("saldo insuficiente");
        }

        Long ultimasTranscoesId = generateUltimasTransacoesLastId(clientes.getId());
        UltimasTransacoes ultimasTransacoes = new UltimasTransacoes(ultimasTranscoesId, clientes.getId(), body.getValor().longValue(), body.getTipo(), body.getDescricao(), LocalDateTime.now());
        ultimasTransacoesService.save(ultimasTransacoes);

        return usuariosService.save(clientes);
    }

    private Long generateUltimasTransacoesLastId(Long usuarioId) {
        UltimasTransacoes ultimasTransacoes = ultimasTransacoesService.findFirstByUsuarioIdOrderByIdDesc(usuarioId);
        return ultimasTransacoes == null ? 1 : ultimasTransacoes.getId() + 1;
    }
}
