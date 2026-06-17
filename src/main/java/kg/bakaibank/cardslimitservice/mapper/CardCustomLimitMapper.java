package kg.bakaibank.cardslimitservice.mapper;

import kg.bakaibank.cardslimitservice.entity.CardCustomLimit;
import kg.bakaibank.cardslimitservice.payload.response.CardLimitResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.Set;

@Mapper(componentModel = "spring")
public interface CardCustomLimitMapper {
    Set<CardLimitResponse> toCardLimitsResponses(Set<CardCustomLimit> cardLimits);

    @Mapping(target = "limitId", source = "limit.id")
    @Mapping(target = "limitName", source = "limit.name")
    CardLimitResponse toResponse(CardCustomLimit cardCustomLimit);
}
