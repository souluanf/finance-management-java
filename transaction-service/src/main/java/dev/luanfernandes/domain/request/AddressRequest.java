package dev.luanfernandes.domain.request;

public record AddressRequest(
        String uf,
        String complemento,
        String ddd,
        String logradouro,
        String bairro,
        String localidade,
        String ibge,
        String siafi,
        String gia,
        String cep) {}
