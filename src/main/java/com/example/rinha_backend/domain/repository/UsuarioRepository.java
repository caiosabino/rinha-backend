package com.example.rinha_backend.domain.repository;

import com.example.rinha_backend.domain.entities.Usuario;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UsuarioRepository extends MongoRepository<Usuario, String> {

}
