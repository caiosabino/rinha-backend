package com.example.rinha_backend.dto.transacoes;

public class TransacoesReponse {
    private Long limite;
    private Long saldo;

    public TransacoesReponse(Long limite, Long saldo) {
        this.limite = limite;
        this.saldo = saldo;
    }

    public Long getLimite() {
        return limite;
    }

    public Long getSaldo() {
        return saldo;
    }

    public void setLimite(Long limite) {
        this.limite = limite;
    }

    public void setSaldo(Long saldo) {
        this.saldo = saldo;
    }
}
