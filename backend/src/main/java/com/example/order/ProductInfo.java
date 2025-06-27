package com.example.order;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class ProductInfo {

    @Id
    @GeneratedValue
    private Long id;
    private String sku;
    private String rastreador;
    private String configuracao;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getSku() { return sku; }
    public void setSku(String sku) { this.sku = sku; }
    public String getRastreador() { return rastreador; }
    public void setRastreador(String rastreador) { this.rastreador = rastreador; }
    public String getConfiguracao() { return configuracao; }
    public void setConfiguracao(String configuracao) { this.configuracao = configuracao; }
}
