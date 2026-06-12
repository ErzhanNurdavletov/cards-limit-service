package kg.bakaibank.cardslimitservice.mapper;

import kg.bakaibank.cardslimitservice.dto.ClientRequest;
import kg.bakaibank.cardslimitservice.dto.ClientResponse;
import kg.bakaibank.cardslimitservice.dto.ClientUpdateRequest;
import kg.bakaibank.cardslimitservice.entity.Client;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ClientMapper {
    Client toEntity(ClientRequest clientRequest);
    ClientResponse toResponse(Client client);
    Client updateEntity(Client client, ClientUpdateRequest request);
}
