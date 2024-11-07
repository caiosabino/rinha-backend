package com.example.rinha_backend.domain.repository;

import com.example.rinha_backend.domain.entities.UltimasTransacoes;
import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UltimasTransacoesRepository extends MongoRepository<UltimasTransacoes, String> {
    List<UltimasTransacoes> findTop10ByUsuarioIdOrderByRealizadaEmDesc(String id);
}
