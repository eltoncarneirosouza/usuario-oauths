package com.usuario.minhaaplicacao.app.repository;

import com.usuario.minhaaplicacao.app.entity.Client;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {
  boolean existsByCpf(@NotBlank(message = "CPF é obrigatório") String cpf);
}
