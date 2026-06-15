package kg.bakaibank.cardslimitservice.service;

import jakarta.persistence.EntityNotFoundException;
import kg.bakaibank.cardslimitservice.entity.Card;
import kg.bakaibank.cardslimitservice.entity.CardCustomLimit;
import kg.bakaibank.cardslimitservice.entity.CardCustomLimitCompositeKey;
import kg.bakaibank.cardslimitservice.entity.Limit;
import kg.bakaibank.cardslimitservice.mapper.CardCustomLimitMapper;
import kg.bakaibank.cardslimitservice.payload.request.CardLimitUpdateRequest;
import kg.bakaibank.cardslimitservice.payload.response.CardLimitResponse;
import kg.bakaibank.cardslimitservice.repository.CardCustomLimitRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class CardCustomLimitService {

    private final CardCustomLimitRepository cardCustomLimitRepository;
    private final LimitsHistoryService limitsHistoryService;
    private final CardCustomLimitMapper cardCustomLimitMapper;

    @Transactional
    public void addDefaultLimitToCard(Card card, Limit limit) {
        CardCustomLimit customLimit = createCardCustomLimit(card, limit);
        cardCustomLimitRepository.save(customLimit);

        card.getCustomLimits().add(customLimit);
        if (limit.getCardsCustomLimits() == null) {
            limit.setCardsCustomLimits(new HashSet<>());
        }
        limit.getCardsCustomLimits().add(customLimit);

        log.info("Added default limit with id: {} to card with id: {}", limit.getId(), card.getId());
    }

    public CardCustomLimit createCardCustomLimit(Card card, Limit limit) {
        CardCustomLimitCompositeKey compositeKey = getCompositeKey(card, limit);

        CardCustomLimit customLimit = new CardCustomLimit();
        customLimit.setId(compositeKey);
        customLimit.setCard(card);
        customLimit.setLimit(limit);
        customLimit.setCurrentAmount(limit.getDefaultAmount());
        customLimit.setCurrentCount(limit.getDefaultCount());
        return customLimit;
    }

    @Transactional
    public CardLimitResponse updateCardLimit(UUID cardId, UUID limitId,
                                             CardLimitUpdateRequest request) {
        CardCustomLimitCompositeKey id = new CardCustomLimitCompositeKey();
        id.setCardId(cardId);
        id.setLimitId(limitId);
        CardCustomLimit cardCustomLimit = cardCustomLimitRepository.findByIdWithLimitName(id)
            .orElseThrow(EntityNotFoundException::new);

        BigDecimal oldAmount = cardCustomLimit.getCurrentAmount();
        Integer oldCount = cardCustomLimit.getCurrentCount();

        Card card = cardCustomLimit.getCard();
        Limit limit = cardCustomLimit.getLimit();
        limitsHistoryService.createLimitHistory(card, limit,
            oldAmount, oldCount,
            request.newAmount(), request.newCount());

        cardCustomLimit.setCurrentAmount(request.newAmount());
        cardCustomLimit.setCurrentCount(request.newCount());
        cardCustomLimitRepository.save(cardCustomLimit);

        log.info("Updated card custom limit with id: {}." +
                " oldAmount = {}, newAmount = {}. oldCount = {}, newCount = {}",
            id, oldAmount, cardCustomLimit.getCurrentAmount(),
            oldCount, cardCustomLimit.getCurrentCount());
        return cardCustomLimitMapper.toResponse(cardCustomLimit);
    }

    @Transactional
    public void save(CardCustomLimit cardCustomLimit) {
        cardCustomLimitRepository.save(cardCustomLimit);
        log.info("Saved card's custom limit with id: {}", cardCustomLimit.getId());
    }

    private CardCustomLimitCompositeKey getCompositeKey(Card card, Limit limit) {
        CardCustomLimitCompositeKey compositeKey = new CardCustomLimitCompositeKey();
        compositeKey.setCardId(card.getId());
        compositeKey.setLimitId(limit.getId());
        log.debug("created composite key with cardId: {} and limitId: {}", card.getId(), limit.getId());
        return compositeKey;
    }
}