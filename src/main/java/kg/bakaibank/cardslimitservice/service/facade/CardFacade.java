package kg.bakaibank.cardslimitservice.service.facade;

import kg.bakaibank.cardslimitservice.payload.request.CardCreateRequest;
import kg.bakaibank.cardslimitservice.payload.request.CardLimitRequest;
import kg.bakaibank.cardslimitservice.payload.response.CardLimitResponse;
import kg.bakaibank.cardslimitservice.payload.response.CardResponse;

import java.util.UUID;

public interface CardFacade {
    CardResponse createCard(CardCreateRequest request);
    CardLimitResponse updateCardCustomLimit(UUID cardId, UUID limitId,
                                            CardLimitRequest request);
}
