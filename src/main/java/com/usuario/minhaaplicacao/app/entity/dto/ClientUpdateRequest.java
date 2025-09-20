package com.usuario.minhaaplicacao.app.entity.dto;

import com.usuario.minhaaplicacao.app.entity.Address;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClientUpdateRequest {
  @NotBlank(message = "Nome é obrigatório")
  private String name;

  @Valid
  private Address address;
}
