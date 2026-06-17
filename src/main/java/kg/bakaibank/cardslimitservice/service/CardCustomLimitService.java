package kg.bakaibank.cardslimitservice.service;

import jakarta.persistence.EntityNotFoundException;
import kg.bakaibank.cardslimitservice.entity.Card;
import kg.bakaibank.cardslimitservice.entity.CardCustomLimit;
import kg.bakaibank.cardslimitservice.entity.CardCustomLimitCompositeKey;
import kg.bakaibank.cardslimitservice.entity.Limit;
import kg.bakaibank.cardslimitservice.exception.NewAmountCountMoreThanMaxLimitException;
import kg.bakaibank.cardslimitservice.mapper.CardCustomLimitMapper;
import kg.bakaibank.cardslimitservice.payload.request.CardLimitRequest;
import kg.bakaibank.cardslimitservice.payload.response.CardLimitResponse;
import kg.bakaibank.cardslimitservice.repository.CardCustomLimitRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class CardCustomLimitService {

    private final CardCustomLimitRepository cardCustomLimitRepository;
    private final LimitsHistoryService limitsHistoryService;
    private final CardCustomLimitMapper cardCustomLimitMapper;

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

    private CardCustomLimit createCardCustomLimit(Card card, Limit limit) {
        CardCustomLimitCompositeKey compositeKey = getCompositeKey(card, limit);
        CardCustomLimit customLimit = new CardCustomLimit();
        customLimit.setId(compositeKey);
        customLimit.setCard(card);
        customLimit.setLimit(limit);
        customLimit.setCurrentAmount(limit.getDefaultAmount());
        customLimit.setCurrentCount(limit.getDefaultCount());
        return customLimit;
    }

    private CardCustomLimitCompositeKey getCompositeKey(Card card, Limit limit) {
        CardCustomLimitCompositeKey compositeKey = new CardCustomLimitCompositeKey();
        compositeKey.setCardId(card.getId());
        compositeKey.setLimitId(limit.getId());
        log.debug("created composite key with cardId: {} and limitId: {}", card.getId(), limit.getId());
        return compositeKey;
    }

    private void validateNewAmountAndCount(BigDecimal newAmount, Integer newCount,
                                           BigDecimal maxAllowedAmount, Integer maxAllowedCount) {
        if (newAmount.compareTo(maxAllowedAmount) > 0 && newCount > maxAllowedCount) {
            throw new NewAmountCountMoreThanMaxLimitException("newAmount more than maxAllowedAmount and" +
                " newCount more than maxAllowedCount");
        } else if (newAmount.compareTo(maxAllowedAmount) > 0) {
            throw new NewAmountCountMoreThanMaxLimitException("newAmount more than maxAllowedAmount");
        } else if (newCount > maxAllowedCount) {
            throw new NewAmountCountMoreThanMaxLimitException("newCount more than maxAllowedCount");
        }
    }

    @Transactional
    public void save(CardCustomLimit cardCustomLimit) {
        cardCustomLimitRepository.save(cardCustomLimit);
        log.info("Saved card's custom limit with id: {}", cardCustomLimit.getId());
    }

    public CardCustomLimit findByCardIdAndLimitId(UUID cardId, UUID limitId) {
        return cardCustomLimitRepository.findByCardIdAndLimitId(cardId, limitId)
            .orElseThrow(() -> new EntityNotFoundException("Card's custom limit not found"));
    }

    @Transactional
    public Set<CardLimitResponse> findAllByCardId(UUID cardId) {
        Set<CardCustomLimit> cardCustomLimits = cardCustomLimitRepository.findAllByCardId(cardId)
            .orElseThrow(() -> new EntityNotFoundException("Card's custom limit not found"));

        return cardCustomLimitMapper.toCardLimitsResponses(cardCustomLimits);
    }

    public CardLimitResponse updateCardLimit(UUID cardId, Limit limit,
                                CardLimitRequest request) {

        CardCustomLimit cardLimit = findByCardIdAndLimitId(cardId, limit.getId());
        validateNewAmountAndCount(request.newAmount(), request.newCount(),
            limit.getMaxAmount(), limit.getMaxCount());

        BigDecimal oldAmount = cardLimit.getCurrentAmount();
        Integer oldCount = cardLimit.getCurrentCount();

        cardLimit.setCurrentAmount(request.newAmount());
        cardLimit.setCurrentCount(request.newCount());

        limitsHistoryService.createLimitHistory(cardLimit.getCard().getId(),
            limit.getId(), oldAmount, oldCount, request.newAmount(), request.newCount());
        return cardCustomLimitMapper.toResponse(cardLimit);
    }
}