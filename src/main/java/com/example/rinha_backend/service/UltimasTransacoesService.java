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
public class UltimasTransacoesService {
    @Autowired
    private UltimasTransacoesRepository ultimasTransacoesRepository;

    private Long generateUltimasTransacoesLastId(Long usuarioId) {
        UltimasTransacoes ultimasTransacoes = ultimasTransacoesRepository.findFirstByUsuarioIdOrderByIdDesc(usuarioId).orElse(null);
        return ultimasTransacoes == null ? 1 : ultimasTransacoes.getId() + 1;
    }

    public List<UltimasTransacoes> findTop10ByUsuarioIdOrderByRealizadaEmDesc(Long id) {
        return ultimasTransacoesRepository.findTop10ByUsuarioIdOrderByRealizadaEmDesc(id);
    }

    public UltimasTransacoes findFirstByUsuarioIdOrderByIdDesc(Long id) {
        return ultimasTransacoesRepository.findFirstByUsuarioIdOrderByIdDesc(id).orElse(null);
    }


    public UltimasTransacoes save(UltimasTransacoes ultimasTransacoes) {
        return ultimasTransacoesRepository.save(ultimasTransacoes);
    }
}
