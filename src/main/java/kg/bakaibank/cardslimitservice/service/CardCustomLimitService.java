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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CardCustomLimitService {

    private final CardCustomLimitRepository cardCustomLimitRepository;
    private final CardCustomLimitMapper cardCustomLimitMapper;

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

        cardCustomLimit.setCurrentAmount(request.newAmount());
        cardCustomLimit.setCurrentCount(request.newCount());
        cardCustomLimitRepository.save(cardCustomLimit);
        return cardCustomLimitMapper.toResponse(cardCustomLimit);

    }

    @Transactional
    public void save(CardCustomLimit cardCustomLimit) {
        cardCustomLimitRepository.save(cardCustomLimit);
    }

    private CardCustomLimitCompositeKey getCompositeKey(Card card, Limit limit) {
        CardCustomLimitCompositeKey compositeKey = new CardCustomLimitCompositeKey();
        compositeKey.setCardId(card.getId());
        compositeKey.setLimitId(limit.getId());
        return compositeKey;
    }
}
