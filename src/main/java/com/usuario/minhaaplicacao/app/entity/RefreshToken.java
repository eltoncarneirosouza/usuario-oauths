package com.usuario.minhaaplicacao.app.entity;

import jakarta.persistence.*;

import java.time.Instant;

import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RefreshToken {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false, unique = true)
  private String token;

  @OneToOne
  private User user;

  private Instant expiryDate;
  private boolean revoked = false;
}