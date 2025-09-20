package com.usuario.minhaaplicacao.app.entity.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class AddressDto {

  private String cep;

  private String logradouro;

  private String bairro;

  @JsonProperty("localidade")
  private String cidade;

  @JsonProperty("uf")
  private String estado;

  private String erro;
}
