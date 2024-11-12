package com.example.rinha_backend.service;

import com.example.rinha_backend.domain.entities.UltimasTransacoes;
import com.example.rinha_backend.domain.entities.Usuario;
import com.example.rinha_backend.domain.repository.UltimasTransacoesRepository;
import com.example.rinha_backend.domain.repository.UsuarioRepository;
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
    private UsuarioRepository usuarioRepository;
    @Autowired
    private UltimasTransacoesRepository ultimasTransacoesRepository;

    @Transactional
    public TransacoesReponse processTransacao(String id, TransacoesRequest body){
        validateBodyParameters(body);

        Usuario usuario = usuarioRepository.findById(id)
                                           .orElseThrow(() -> new ResourceAccessException("usuário não encontrado"));

        usuario = processTransaction(usuario, body);

        TransacoesReponse transacoesReponse = new TransacoesReponse(usuario.getLimite(), usuario.getSaldo());

        return transacoesReponse;
    }

    public ExtratoResponse processExtrato(String id){
        Usuario usuario = usuarioRepository.findById(id)
                                           .orElseThrow(() -> new ResourceAccessException("usuário não encontrado"));

        List<UltimasTransacoes> ultimasTransacoes = ultimasTransacoesRepository.findTop10ByUsuarioIdOrderByRealizadaEmDesc(id);

        Saldo saldo = new Saldo(usuario.getSaldo(), DateUtils.formatLocalDateTimeToString(LocalDateTime.now()), usuario.getLimite());

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

    private Usuario processTransaction(Usuario usuario, TransacoesRequest body) {
        if(body.getTipo().equals("c")){
            usuario.setSaldo(usuario.getSaldo() + body.getValor().longValue());
        } else if (body.getTipo().equals("d")) {
            usuario.setSaldo(usuario.getSaldo() - body.getValor()
                                                      .longValue());
        }

        if(usuario.getLimite() + usuario.getSaldo() < 0){
            throw new IllegalArgumentException("saldo insuficiente");
        }

        UltimasTransacoes ultimasTransacoes = new UltimasTransacoes(usuario.getId(), body.getValor().longValue(), body.getTipo(), body.getDescricao(), LocalDateTime.now());
        ultimasTransacoesRepository.save(ultimasTransacoes);

        return usuarioRepository.save(usuario);
    }
}
