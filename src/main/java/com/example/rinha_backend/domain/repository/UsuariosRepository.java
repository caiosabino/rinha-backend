package com.example.rinha_backend.domain.repository;

import com.example.rinha_backend.domain.entities.Usuarios;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UsuariosRepository extends MongoRepository<Usuarios, Long> {

}
