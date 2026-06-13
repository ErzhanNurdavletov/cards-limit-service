package kg.bakaibank.cardslimitservice.mapper;

import kg.bakaibank.cardslimitservice.payload.request.ClientCreateRequest;
import kg.bakaibank.cardslimitservice.payload.response.ClientResponse;
import kg.bakaibank.cardslimitservice.payload.request.ClientUpdateRequest;
import kg.bakaibank.cardslimitservice.entity.Client;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ClientMapper {
    Client toEntity(ClientCreateRequest clientCreateRequest);
    ClientResponse toResponse(Client client);
    void updateEntity(@MappingTarget Client client, ClientUpdateRequest request);
}
