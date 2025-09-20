package com.usuario.minhaaplicacao.app.service;

import com.usuario.minhaaplicacao.app.entity.dto.ClientDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClientService {
  public ClientDTO create(ClientDTO dto) {
    return dto;
  }

  public ResponseEntity<Void> delete(Long id) {
    return null;
  }

  public ClientDTO serchById(Long id) {
    return null;
  }

  public List<ClientDTO> serchAll() {
    return null;
  }

  public ClientDTO putID(Long id) {
    return null;
  }
}
