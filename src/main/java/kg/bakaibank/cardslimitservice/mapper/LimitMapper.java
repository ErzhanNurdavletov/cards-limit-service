package kg.bakaibank.cardslimitservice.mapper;

import kg.bakaibank.cardslimitservice.entity.Limit;
import kg.bakaibank.cardslimitservice.payload.request.LimitCreateRequest;
import kg.bakaibank.cardslimitservice.payload.request.LimitUpdateRequest;
import kg.bakaibank.cardslimitservice.payload.response.LimitResponse;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface LimitMapper {
    Limit toEntity(LimitCreateRequest request);
    LimitResponse toResponse(Limit limit);
    void updateEntity(@MappingTarget Limit limit, LimitUpdateRequest request);
}
