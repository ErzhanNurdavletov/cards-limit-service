package kg.bakaibank.cardslimitservice.service;

import kg.bakaibank.cardslimitservice.entity.Client;
import kg.bakaibank.cardslimitservice.entity.enums.ClientType;
import kg.bakaibank.cardslimitservice.mapper.ClientMapper;
import kg.bakaibank.cardslimitservice.payload.request.ClientCreateRequest;
import kg.bakaibank.cardslimitservice.payload.response.ClientResponse;
import kg.bakaibank.cardslimitservice.repository.ClientRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.OffsetDateTime;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
public class ClientServiceTest {

    @Mock
    private ClientRepository clientRepository;

    @Mock
    private ClientMapper clientMapper;

    @InjectMocks
    private ClientService clientService;

    @Test
    public void createClient_Success() {
        ClientCreateRequest request = new ClientCreateRequest("firstname",
            "lastname", null, ClientType.LEGAL, "+996200200200");

        Client clientAfterMapping = Client.builder()
            .type(ClientType.LEGAL)
            .firstname("firstname")
            .lastname("lastname")
            .phoneNumber("+996200200200")
            .build();

        Client savedClient = Client.builder()
            .id(UUID.randomUUID())
            .type(ClientType.LEGAL)
            .firstname("firstname")
            .lastname("lastname")
            .phoneNumber("+996200200200")
            .build();

        ClientResponse expectedResponse = new ClientResponse(
            savedClient.getId(),
            "firstname",
            "lastname",
            null,
            ClientType.LEGAL,
            savedClient.getCreatedAt(),
            null,
            "+996200200200");

        Mockito.when(clientRepository.save(any(Client.class))).thenReturn(savedClient);
        Mockito.when(clientMapper.toResponse(any(Client.class))).thenReturn(expectedResponse);
        Mockito.when(clientMapper.toEntity(any(ClientCreateRequest.class)))
            .thenReturn(clientAfterMapping);

        ClientResponse actualResponse = clientService.createClient(request);
        Assertions.assertEquals(expectedResponse, actualResponse);
    }
}
