package com.example.rinha_backend.dto.extrato;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import java.time.LocalDateTime;

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class Saldo {
    private Long total;
    private String dataExtrato;
    private Long limite;

    public Saldo(Long total, String dataExtrato, Long limite) {
        this.total = total;
        this.dataExtrato = dataExtrato;
        this.limite = limite;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public String getDataExtrato() {
        return dataExtrato;
    }

    public void setDataExtrato(String dataExtrato) {
        this.dataExtrato = dataExtrato;
    }

    public Long getLimite() {
        return limite;
    }

    public void setLimite(Long limite) {
        this.limite = limite;
    }
}
