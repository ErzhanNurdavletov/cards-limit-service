package kg.bakaibank.cardslimitservice.mapper;

import kg.bakaibank.cardslimitservice.entity.Card;
import kg.bakaibank.cardslimitservice.payload.request.CardCreateRequest;
import kg.bakaibank.cardslimitservice.payload.request.CardUpdateRequest;
import kg.bakaibank.cardslimitservice.payload.response.CardResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.UUID;

@Mapper(componentModel = "spring")
public interface CardMapper {
    @Mapping(target = "client", ignore = true)
    Card toEntity(CardCreateRequest request);

    @Mapping(target = "clientId", source = "card.client.id")
    @Mapping(target = "cardIssueTypeName", source = "card.issueType.name")
    CardResponse toResponse(Card card);

    @Mapping(target = "id", source = "card.id")
    @Mapping(target = "clientId", source = "clientId")
    @Mapping(target = "cardIssueTypeName", source = "cardIssueTypeName")
    CardResponse toCreateResponse(Card card, UUID clientId, String cardIssueTypeName);

    void updateEntity(@MappingTarget Card card, CardUpdateRequest request);
}
