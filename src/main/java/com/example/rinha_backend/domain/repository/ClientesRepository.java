package com.example.rinha_backend.domain.repository;

import com.example.rinha_backend.domain.entities.Clientes;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ClientesRepository extends MongoRepository<Clientes, Long> {

}
