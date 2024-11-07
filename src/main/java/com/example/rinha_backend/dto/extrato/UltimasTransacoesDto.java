package com.example.rinha_backend.dto.extrato;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import java.time.LocalDateTime;

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class UltimasTransacoesDto {
    private Long valor;
    private String tipo;
    private String descricao;
    private String realizadaEm;

    public UltimasTransacoesDto(Long valor, String tipo, String descricao, String realizadaEm) {
        this.valor = valor;
        this.tipo = tipo;
        this.descricao = descricao;
        this.realizadaEm = realizadaEm;
    }

    public Long getValor() {
        return valor;
    }

    public void setValor(Long valor) {
        this.valor = valor;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getRealizadaEm() {
        return realizadaEm;
    }

    public void setRealizadaEm(String realizadaEm) {
        this.realizadaEm = realizadaEm;
    }
}
