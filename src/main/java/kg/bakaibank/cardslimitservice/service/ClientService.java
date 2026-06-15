package kg.bakaibank.cardslimitservice.service;

import jakarta.persistence.EntityNotFoundException;
import kg.bakaibank.cardslimitservice.exception.IdNotMatchBodyClientIdException;
import kg.bakaibank.cardslimitservice.payload.request.ClientCreateRequest;
import kg.bakaibank.cardslimitservice.payload.response.ClientResponse;
import kg.bakaibank.cardslimitservice.entity.Client;
import kg.bakaibank.cardslimitservice.mapper.ClientMapper;
import kg.bakaibank.cardslimitservice.payload.request.ClientUpdateRequest;
import kg.bakaibank.cardslimitservice.repository.ClientRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class ClientService {

    private final ClientRepository clientRepository;
    private final ClientMapper clientMapper;

    @Transactional
    public ClientResponse createClient(ClientCreateRequest clientCreateRequest) {
        Client client = clientMapper.toEntity(clientCreateRequest);
        client.setCreatedAt(OffsetDateTime.now());
        clientRepository.save(client);
        log.info("Created client with id: {}", client.getId());
        return clientMapper.toResponse(client);
    }

    @Transactional
    public ClientResponse deleteClientById(UUID id) {
        Client client = clientRepository.findByDeletedAtIsNullAndId(id)
            .orElseThrow(() -> new EntityNotFoundException("Client not found"));
        client.setDeletedAt(OffsetDateTime.now());
        clientRepository.save(client);
        log.info("Marked as deleted client with id: {}", client.getId());
        return clientMapper.toResponse(client);
    }

    @Transactional(readOnly = true)
    public ClientResponse getClientById(UUID id) {
        Client client = clientRepository.findClientById(id).orElseThrow(EntityNotFoundException::new);
        return clientMapper.toResponse(client);
    }

    public Client getClientEntityById(UUID id) {
        return clientRepository.findClientById(id)
            .orElseThrow(EntityNotFoundException::new);
    }

    @Transactional
    public ClientResponse changeClientById(UUID id, ClientUpdateRequest request) {
        Client client = clientRepository.findClientById(id).orElseThrow(EntityNotFoundException::new);
        clientMapper.updateEntity(client, request);
        clientRepository.save(client);
        log.info("updated client with id: {}", client.getId());
        return clientMapper.toResponse(client);
    }
}
