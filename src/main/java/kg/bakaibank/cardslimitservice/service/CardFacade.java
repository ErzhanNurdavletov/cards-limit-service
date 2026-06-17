package kg.bakaibank.cardslimitservice.service;

import kg.bakaibank.cardslimitservice.entity.Card;
import kg.bakaibank.cardslimitservice.entity.Limit;
import kg.bakaibank.cardslimitservice.mapper.CardMapper;
import kg.bakaibank.cardslimitservice.payload.request.CardCreateRequest;
import kg.bakaibank.cardslimitservice.payload.request.CardLimitRequest;
import kg.bakaibank.cardslimitservice.payload.response.CardLimitResponse;
import kg.bakaibank.cardslimitservice.payload.response.CardResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CardFacade {

    private final CardService cardService;
    private final LimitService limitService;
    private final CardCustomLimitService cardCustomLimitService;
    private final CardMapper cardMapper;

    @Transactional
    public CardResponse createCard(CardCreateRequest request) {
        Set<Limit> defaultLimits = limitService.getDefaultLimits();
        Card card = cardService.createCardWithoutLimits(request);

        for (Limit defaultLimit : defaultLimits) {
            cardCustomLimitService.addDefaultLimitToCard(card, defaultLimit);
        }
        return cardMapper.toResponse(card);
    }

    @Transactional
    public CardLimitResponse updateCardCustomLimit(UUID cardId, UUID limitId,
                                                   CardLimitRequest request) {
        cardService.checkIsCardActive(cardId);
        Limit limit = limitService.getLimitEntityById(limitId);
        return cardCustomLimitService.updateCardLimit(cardId, limit, request);
    }
}
