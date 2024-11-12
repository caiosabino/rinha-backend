package com.example.rinha_backend.domain.repository;

import com.example.rinha_backend.domain.entities.UltimasTransacoes;
import java.util.List;
import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UltimasTransacoesRepository extends MongoRepository<UltimasTransacoes, Long> {
    List<UltimasTransacoes> findTop10ByUsuarioIdOrderByRealizadaEmDesc(Long id);
   Optional<UltimasTransacoes> findFirstByUsuarioIdOrderByIdDesc(Long usuarioId);
}
