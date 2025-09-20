package com.usuario.minhaaplicacao.app.client;

import com.usuario.minhaaplicacao.app.entity.Address;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@RequiredArgsConstructor
public class ViaCEPClient {
  private static final String VIA_CEP_URL = "https://viacep.com.br/ws/%s/json/";
  private final RestTemplate restTemplate;

  public ViaCEPResponse consultarCEP(String cep) {
    String cepLimpo = cep.replaceAll("\\D", "");
    return restTemplate.getForObject(
          String.format(VIA_CEP_URL, cepLimpo),
          ViaCEPResponse.class
    );
  }

  public record ViaCEPResponse(
        String cep,
        String logradouro,
        String complemento,
        String bairro,
        String localidade,
        String uf,
        String ibge,
        String gia,
        String ddd,
        String siafi
  ) {
    public Address toAddress() {
      return Address.builder()
            .cep(cep)
            .logradouro(logradouro)
            .bairro(bairro)
            .cidade(localidade)
            .estado(uf)
            .build();
    }
  }
}
