package com.usuario.minhaaplicacao.app.service;

import com.usuario.minhaaplicacao.app.client.ViaCEPClient;
import com.usuario.minhaaplicacao.app.entity.Address;
import com.usuario.minhaaplicacao.app.entity.Client;
import com.usuario.minhaaplicacao.app.entity.dto.ClientCreateRequest;
import com.usuario.minhaaplicacao.app.entity.dto.ClientUpdateRequest;
import com.usuario.minhaaplicacao.app.exception.CPFValidationException;
import com.usuario.minhaaplicacao.app.exception.CpfAlreadyExistsException;
import com.usuario.minhaaplicacao.app.repository.ClientRepository;
import com.usuario.minhaaplicacao.app.util.CPFUtils;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ClientService {

  private final ClientRepository clientRepository;
  private final ViaCEPClient viaCEPClient;

  public Client save(ClientCreateRequest request) {
    try {
      CPFUtils.validate(request.getCpf());

      String formattedCpf = CPFUtils.formatCPF(request.getCpf());

      if (clientRepository.existsByCpf(formattedCpf)) {
        throw new CpfAlreadyExistsException(formattedCpf);
      }

      var viaCepResponse = viaCEPClient.consultarCEP(request.getCep());
      Address address = viaCepResponse.toAddress();

      Client client = Client.builder()
            .name(request.getName())
            .email(request.getEmail())
            .password(request.getPassword())
            .cpf(formattedCpf)
            .address(address)
            .build();

      return clientRepository.save(client);
    } catch (CPFValidationException e) {
      throw new IllegalArgumentException("Erro na validação do CPF: " + e.getMessage());
    }
  }

  public Client update(Long id, ClientUpdateRequest request) {
    return clientRepository.findById(id)
          .map(existingClient -> {
            existingClient.setName(request.getName());

            if (request.getAddress() != null && request.getAddress().getCep() != null) {
              var viaCepResponse = viaCEPClient.consultarCEP(request.getAddress().getCep());
              if (viaCepResponse != null) {
                Address address = viaCepResponse.toAddress();
                address.setNumero(request.getAddress().getNumero());
                address.setComplemento(request.getAddress().getComplemento());
                existingClient.setAddress(address);
              }
            }

            return clientRepository.save(existingClient);
          })
          .orElseThrow(() -> new EntityNotFoundException("Cliente não encontrado com ID: " + id));
  }

  @Transactional(readOnly = true)
  public Client findById(Long id) {
    return clientRepository.findById(id)
          .orElseThrow(() -> new EntityNotFoundException("Cliente não encontrado com ID: " + id));
  }

  @Transactional(readOnly = true)
  public List<Client> findAll() {
    return clientRepository.findAll();
  }

  public void delete(Long id) {
    if (!clientRepository.existsById(id)) {
      throw new EntityNotFoundException("Cliente não encontrado com ID: " + id);
    }
    clientRepository.deleteById(id);
  }
}
