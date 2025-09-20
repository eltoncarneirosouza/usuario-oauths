package com.usuario.minhaaplicacao.app.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Client {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  @NotBlank
  private String nome;
  @Column(nullable = false, unique = true)
  private String cpf;
  private String logradouro;
  private String bairro;
  private String cidade;
  private String estado;
  private String cep;
}