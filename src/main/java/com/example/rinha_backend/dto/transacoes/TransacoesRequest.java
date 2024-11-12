package com.example.rinha_backend.dto.transacoes;

public class TransacoesRequest {
    private Double valor;
    private String tipo;
    private String descricao;

    public TransacoesRequest(Double valor, String tipo, String descricao) {
        this.valor = valor;
        this.tipo = tipo;
        this.descricao = descricao;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getTipo() {
        return tipo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void validateBodyParameters() {
        if(this.getValor() == null || this.getDescricao() == null || this.getTipo() == null){
            throw new IllegalArgumentException("parâmetros inválidos");
        }

        if(this.getDescricao().length() > 10) {
            throw new IllegalArgumentException("descrição deve ter no máximo 10 caracteres");
        }

        if(!this.getTipo().equals("c") && !this.getTipo().equals("d")){
            throw new IllegalArgumentException("tipo de transação inválido");
        }

        if(this.getValor() != Math.floor(this.getValor())){
            throw new IllegalArgumentException("valor deve ser um número inteiro");
        }
    }
}
