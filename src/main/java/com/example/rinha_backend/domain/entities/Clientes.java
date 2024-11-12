package com.example.rinha_backend.domain.entities;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

@Document(collection = "clientes")
public class Clientes {
    @MongoId
    private Long id;
    private Long limite;
    private Long saldo;

    public Clientes(Long id, Long limite, Long saldo) {
        this.id = id;
        this.limite = limite;
        this.saldo = saldo;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getLimite() {
        return limite;
    }

    public void setLimite(Long limite) {
        this.limite = limite;
    }

    public Long getSaldo() {
        return saldo;
    }

    public void setSaldo(Long saldo) {
        this.saldo = saldo;
    }
}
