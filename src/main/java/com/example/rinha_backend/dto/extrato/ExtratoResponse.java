package com.example.rinha_backend.dto.extrato;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import java.util.List;

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class ExtratoResponse {
    private Saldo saldo;
    private List<UltimasTransacoesDto> ultimasTransacoeDtos;

    public ExtratoResponse(Saldo saldo, List<UltimasTransacoesDto> ultimasTransacoeDtos) {
        this.saldo = saldo;
        this.ultimasTransacoeDtos = ultimasTransacoeDtos;
    }

    public Saldo getSaldo() {
        return saldo;
    }

    public void setSaldo(Saldo saldo) {
        this.saldo = saldo;
    }

    public List<UltimasTransacoesDto> getUltimasTransacoes() {
        return ultimasTransacoeDtos;
    }

    public void setUltimasTransacoes(List<UltimasTransacoesDto> ultimasTransacoeDtos) {
        this.ultimasTransacoeDtos = ultimasTransacoeDtos;
    }
}
