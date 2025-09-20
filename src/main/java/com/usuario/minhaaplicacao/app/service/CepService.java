package com.usuario.minhaaplicacao.app.service;

import com.usuario.minhaaplicacao.app.entity.Address;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class CepService {
  private static final String VIA_CEP_URL = "https://viacep.com.br/ws/%s/json/";
  private final RestTemplate restTemplate;

  public Address findAddressByCep(String cep) {
    String formattedCep = cep.replaceAll("\\D", "");
    ViaCepResponse response = restTemplate.getForObject(
          String.format(VIA_CEP_URL, formattedCep),
          ViaCepResponse.class
    );

    if (response != null) {
      return Address.builder()
            .cep(response.cep())
            .logradouro(response.logradouro())
            .bairro(response.bairro())
            .cidade(response.localidade())
            .estado(response.uf())
            .build();
    }
    return null;
  }

  private record ViaCepResponse(
        String cep,
        String logradouro,
        String bairro,
        String localidade,
        String uf
  ) {
  }
}
