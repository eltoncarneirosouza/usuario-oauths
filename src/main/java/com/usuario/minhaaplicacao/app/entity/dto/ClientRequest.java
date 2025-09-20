package com.usuario.minhaaplicacao.app.entity.dto;

import com.usuario.minhaaplicacao.app.entity.Address;
import jakarta.validation.Valid;
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
public class ClientRequest {
  @NotBlank(message = "Nome é obrigatório")
  private String name;

  @Email(message = "Email deve ser válido")
  @NotBlank(message = "Email é obrigatório")
  private String email;

  @NotBlank(message = "Senha é obrigatório")
  private String password;

  @NotBlank(message = "CPF é obrigatório")
  private String cpf;

  @Valid
  private Address address;
}
