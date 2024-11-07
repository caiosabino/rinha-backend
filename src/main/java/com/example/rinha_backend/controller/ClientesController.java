package com.example.rinha_backend.controller;

import com.example.rinha_backend.dto.extrato.ExtratoResponse;
import com.example.rinha_backend.dto.transacoes.TransacoesReponse;
import com.example.rinha_backend.dto.transacoes.TransacoesRequest;
import com.example.rinha_backend.service.ClientesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/clientes")
public class ClientesController {
    @Autowired
    private ClientesService clientesService;

    @PostMapping("/{id}/transacoes")
    @ResponseStatus(HttpStatus.OK)
    public TransacoesReponse transacoes(@PathVariable String id, @RequestBody TransacoesRequest body) {
        return clientesService.processTransacao(id, body);
    }

    @GetMapping("/{id}/extrato")
    public ExtratoResponse extrato(@PathVariable String id) {
        return clientesService.processExtrato(id);
    }
}
