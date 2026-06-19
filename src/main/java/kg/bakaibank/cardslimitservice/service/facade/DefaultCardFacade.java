package kg.bakaibank.cardslimitservice.service.facade;

import kg.bakaibank.cardslimitservice.entity.Card;
import kg.bakaibank.cardslimitservice.entity.CardCustomLimit;
import kg.bakaibank.cardslimitservice.entity.Limit;
import kg.bakaibank.cardslimitservice.mapper.CardMapper;
import kg.bakaibank.cardslimitservice.payload.request.CardCreateRequest;
import kg.bakaibank.cardslimitservice.payload.request.CardLimitRequest;
import kg.bakaibank.cardslimitservice.payload.request.PaymentPermissionRequest;
import kg.bakaibank.cardslimitservice.payload.response.CardLimitResponse;
import kg.bakaibank.cardslimitservice.payload.response.CardResponse;
import kg.bakaibank.cardslimitservice.payload.response.PaymentPermissionResponse;
import kg.bakaibank.cardslimitservice.service.CardCustomLimitService;
import kg.bakaibank.cardslimitservice.service.CardService;
import kg.bakaibank.cardslimitservice.service.LimitService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DefaultCardFacade implements CardFacade {

    private final CardService cardService;
    private final LimitService limitService;
    private final CardCustomLimitService cardCustomLimitService;
    private final CardMapper cardMapper;

    @Override
    @Transactional
    public CardResponse createCard(CardCreateRequest request) {
        Set<Limit> defaultLimits = limitService.getDefaultLimits();
        Card card = cardService.createCardWithoutLimits(request);

        for (Limit defaultLimit : defaultLimits) {
            cardCustomLimitService.addDefaultLimitToCard(card, defaultLimit);
        }
        return cardMapper.toResponse(card);
    }

    @Override
    @Transactional
    public CardLimitResponse updateCardCustomLimit(UUID cardId, UUID limitId,
                                                   CardLimitRequest request) {
        cardService.checkIsCardActive(cardId);
        Limit limit = limitService.getLimitEntityById(limitId);
        return cardCustomLimitService.updateCardLimit(cardId, limit, request);
    }
}
