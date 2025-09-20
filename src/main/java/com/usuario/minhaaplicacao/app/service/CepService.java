package com.usuario.minhaaplicacao.app.service;

import com.usuario.minhaaplicacao.app.entity.dto.AddressDto;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Service
public class CepService {
  private final RestTemplate rest = new RestTemplate();

  public Optional<AddressDto> buscaPorCep(String cep) {
    String url = "https://viacep.com.br/ws/" + cep + "/json/";
    try {
      ResponseEntity<AddressDto> resp = rest.getForEntity(url, AddressDto.class);
      if (resp.getStatusCode().is2xxSuccessful() && resp.getBody() != null && resp.getBody().getErro() == null) {
        return Optional.of(resp.getBody());
      }
    } catch (Exception ex) {}
    return Optional.empty();
  }
}
