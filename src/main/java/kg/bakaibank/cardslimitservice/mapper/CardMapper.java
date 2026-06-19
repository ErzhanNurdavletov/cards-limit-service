package kg.bakaibank.cardslimitservice.mapper;

import kg.bakaibank.cardslimitservice.entity.Card;
import kg.bakaibank.cardslimitservice.payload.request.CardUpdateRequest;
import kg.bakaibank.cardslimitservice.payload.response.CardResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface CardMapper {
    @Mapping(target = "clientId", source = "card.client.id")
    @Mapping(target = "cardIssueTypeName", source = "card.issueType.name")
    CardResponse toResponse(Card card);

    void updateEntity(@MappingTarget Card card, CardUpdateRequest request);
}
