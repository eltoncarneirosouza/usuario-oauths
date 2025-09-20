package com.usuario.minhaaplicacao.app.entity;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Address {
  @NotBlank(message = "CEP é obrigatório")
  @Pattern(regexp = "\\d{5}-?\\d{3}", message = "CEP inválido")
  private String cep;

  @NotBlank(message = "Logradouro é obrigatório")
  private String logradouro;

  @NotBlank(message = "Bairro é obrigatório")
  private String bairro;

  @NotBlank(message = "Cidade é obrigatória")
  private String cidade;

  @NotBlank(message = "Estado é obrigatório")
  private String estado;

  private String complemento;
  private String numero;
}
