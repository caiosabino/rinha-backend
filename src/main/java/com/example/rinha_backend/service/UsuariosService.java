package com.example.rinha_backend.service;

import com.example.rinha_backend.domain.entities.Clientes;
import com.example.rinha_backend.domain.repository.ClientesRepository;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UsuariosService {
    @Autowired
    private ClientesRepository clientesRepository;

    public Optional<Clientes> findById(Long id) {
        return clientesRepository.findById(id);
    }

    public Clientes save(Clientes clientes) {
        return clientesRepository.save(clientes);
    }
}
