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
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.ResourceAccessException;

@Service
public class UsuariosService {
    @Autowired
    private UsuariosRepository usuariosRepository;

    public Optional<Usuarios> findById(Long id) {
        return usuariosRepository.findById(id);
    }

    public Usuarios save(Usuarios usuarios) {
        return usuariosRepository.save(usuarios);
    }
}
