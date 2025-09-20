package com.usuario.minhaaplicacao.app.entity.dto;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClientCreateRequest {
  @NotBlank(message = "Nome é obrigatório")
  private String name;

  @NotBlank(message = "Email é obrigatório")
  @Email(message = "Email inválido")
  private String email;

  @NotBlank(message = "Senha é obrigatória")
  private String password;

  private String cpf;

  @NotBlank(message = "CEP é obrigatório")
  private String cep;
}
