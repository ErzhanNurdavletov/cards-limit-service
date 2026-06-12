package kg.bakaibank.cardslimitservice.service;

import jakarta.persistence.EntityNotFoundException;
import kg.bakaibank.cardslimitservice.dto.ClientRequest;
import kg.bakaibank.cardslimitservice.dto.ClientResponse;
import kg.bakaibank.cardslimitservice.dto.ClientUpdateRequest;
import kg.bakaibank.cardslimitservice.entity.Client;
import kg.bakaibank.cardslimitservice.mapper.ClientMapper;
import kg.bakaibank.cardslimitservice.repository.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ClientService {

    private final ClientRepository clientRepository;
    private final ClientMapper clientMapper;

    @Transactional
    public ClientResponse createClient(ClientRequest clientRequest) {
        Client client = clientMapper.toEntity(clientRequest);
        client.setCreatedAt(OffsetDateTime.now());
        clientRepository.save(client);
        return clientMapper.toResponse(client);
    }

    @Transactional
    public ClientResponse deleteClientById(UUID id) {
        Client client = clientRepository.deleteClientById(id);
        return clientMapper.toResponse(client);
    }

    @Transactional(readOnly = true)
    public ClientResponse getClientById(UUID id) {
        Client client = clientRepository.findClientById(id).orElseThrow(EntityNotFoundException::new);
        return clientMapper.toResponse(client);
    }

    @Transactional
    public ClientResponse changeClientById(UUID id, ClientUpdateRequest request) {
        Client client = clientRepository.findClientById(id).orElseThrow(EntityNotFoundException::new);
        clientMapper.updateEntity(client, request);
        Client updatedClient = clientRepository.save(client);
        return clientMapper.toResponse(updatedClient);
    }
}
