package com.example.rinha_backend.service;

import com.example.rinha_backend.domain.entities.UltimasTransacoes;
import com.example.rinha_backend.domain.entities.Usuarios;
import com.example.rinha_backend.domain.repository.UltimasTransacoesRepository;
import com.example.rinha_backend.domain.repository.UsuariosRepository;
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
    private UsuariosRepository usuariosRepository;
    @Autowired
    private UltimasTransacoesRepository ultimasTransacoesRepository;

    @Transactional
    public TransacoesReponse processTransacao(Long id, TransacoesRequest body){
        validateBodyParameters(body);

        Usuarios usuarios = usuariosRepository.findById(id)
                                              .orElseThrow(() -> new ResourceAccessException("usuário não encontrado"));

        usuarios = processTransaction(usuarios, body);

        TransacoesReponse transacoesReponse = new TransacoesReponse(usuarios.getLimite(), usuarios.getSaldo());

        return transacoesReponse;
    }

    public ExtratoResponse processExtrato(Long id){
        Usuarios usuarios = usuariosRepository.findById(id)
                                              .orElseThrow(() -> new ResourceAccessException("usuário não encontrado"));

        List<UltimasTransacoes> ultimasTransacoes = ultimasTransacoesRepository.findTop10ByUsuarioIdOrderByRealizadaEmDesc(id);

        Saldo saldo = new Saldo(usuarios.getSaldo(), DateUtils.formatLocalDateTimeToString(LocalDateTime.now()), usuarios.getLimite());

        List<UltimasTransacoesDto> ultimasTransacoesDto = new ArrayList<>();
        ultimasTransacoes.forEach(ut -> ultimasTransacoesDto.add(new UltimasTransacoesDto(ut.getValor(), ut.getTipo(), ut.getDescricao(), DateUtils.formatLocalDateTimeToString(ut.getRealizadaEm()))));

        return new ExtratoResponse(saldo, ultimasTransacoesDto);
    }

    private void validateBodyParameters(TransacoesRequest body) {
        if(body.getValor() == null || body.getDescricao() == null || body.getTipo() == null){
            throw new IllegalArgumentException("parâmetros inválidos");
        }

        if(body.getDescricao().length() > 10) {
            throw new IllegalArgumentException("descrição deve ter no máximo 10 caracteres");
        }

        if(!body.getTipo().equals("c") && !body.getTipo().equals("d")){
            throw new IllegalArgumentException("tipo de transação inválido");
        }

        if(body.getValor() != Math.floor(body.getValor())){
            throw new IllegalArgumentException("valor deve ser um número inteiro");
        }
    }

    private Usuarios processTransaction(Usuarios usuarios, TransacoesRequest body) {
        if(body.getTipo().equals("c")){
            usuarios.setSaldo(usuarios.getSaldo() + body.getValor().longValue());
        } else if (body.getTipo().equals("d")) {
            usuarios.setSaldo(usuarios.getSaldo() - body.getValor()
                                                        .longValue());
        }

        if(usuarios.getLimite() + usuarios.getSaldo() < 0){
            throw new IllegalArgumentException("saldo insuficiente");
        }

        UltimasTransacoes ultimasTransacoes = new UltimasTransacoes(Long.parseLong(usuarios.getId()), body.getValor().longValue(), body.getTipo(), body.getDescricao(), LocalDateTime.now());
        ultimasTransacoesRepository.save(ultimasTransacoes);

        return usuariosRepository.save(usuarios);
    }
}
