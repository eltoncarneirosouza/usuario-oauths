package com.usuario.minhaaplicacao.app.controller;

import com.usuario.minhaaplicacao.app.entity.dto.ClientDTO;
import com.usuario.minhaaplicacao.app.service.ClientService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/clients")
@RequiredArgsConstructor
public class ClientController {
  private final ClientService clientService;

  @PostMapping
  @PreAuthorize("isAuthenticated()")
  public ResponseEntity<ClientDTO> create(@Valid @RequestBody ClientDTO dto) {
    return ResponseEntity.status(HttpStatus.CREATED).body(clientService.create(dto));
  }

  @GetMapping("/{id}")
  public ResponseEntity<ClientDTO> getById(@PathVariable Long id) {
    return ResponseEntity.status(HttpStatus.CREATED).body(clientService.serchById(id));
  }

  @GetMapping
  public ResponseEntity<List<ClientDTO>> list() {
    return ResponseEntity.status(HttpStatus.CREATED).body(clientService.serchAll());
  }

  @PutMapping("/{id}")
  public ResponseEntity<ClientDTO> update(@PathVariable Long id, @Valid @RequestBody ClientDTO dto) {
    return ResponseEntity.status(HttpStatus.CREATED).body(clientService.putID(id));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> delete(@PathVariable Long id) {
    return clientService.delete(id);
  }
}