package kg.bakaibank.cardslimitservice.service;

import kg.bakaibank.cardslimitservice.entity.*;
import kg.bakaibank.cardslimitservice.entity.enums.CardStatus;
import kg.bakaibank.cardslimitservice.mapper.CardCustomLimitMapper;
import kg.bakaibank.cardslimitservice.payload.response.CardLimitResponse;
import kg.bakaibank.cardslimitservice.repository.CardRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@ExtendWith(MockitoExtension.class)
//@Transactional
public class CardServiceTest {

    @Mock
    private CardRepository cardRepository;

    @Mock
    private CardCustomLimitMapper cardCustomLimitMapper;

    @InjectMocks
    private CardService cardService;

    @Test
    public void getCardLimits_Success() {
        UUID cardId = UUID.randomUUID();

        CardCustomLimit limit1 = createTestCardLimitForCard(cardId,
            "test-limit-1", new BigDecimal("500.00"), 50);
        CardCustomLimit limit2 = createTestCardLimitForCard(cardId,
            "test-limit-2", new BigDecimal("444.00"), 44);

        Set<CardCustomLimit> cardCustomLimits = new HashSet<>();
        cardCustomLimits.add(limit1);
        cardCustomLimits.add(limit2);

        Card card = Card.builder()
            .id(cardId)
            .status(CardStatus.ACTIVE)
            .customLimits(cardCustomLimits)
            .build();

        Set<CardLimitResponse> expectedResponses = new HashSet<>();
        expectedResponses.add(new CardLimitResponse(limit1.getLimit().getId(),
            "test-limit-1", new BigDecimal("500.00"), 50));
        expectedResponses.add(new CardLimitResponse(limit2.getLimit().getId(),
            "test-limit-2", new BigDecimal("444.00"), 44));

        Mockito.when(cardRepository.findCardById(cardId))
            .thenReturn(Optional.of(card));
        Mockito.when(cardCustomLimitMapper.toCardLimitsResponses(card.getCustomLimits()))
            .thenReturn(expectedResponses);

        Set<CardLimitResponse> actualResponses = cardService.getCardLimits(cardId);

        Assertions.assertNotNull(actualResponses);
        Assertions.assertEquals(expectedResponses.size(), actualResponses.size());
        Assertions.assertEquals(expectedResponses, actualResponses);

        Mockito.verify(cardRepository, Mockito.times(1)).findCardById(cardId);
        Mockito.verify(cardCustomLimitMapper, Mockito.times(1)).toCardLimitsResponses(card.getCustomLimits());
    }

    private CardCustomLimit createTestCardLimitForCard(UUID cardId,
                                                       String limitName,
                                                       BigDecimal amount,
                                                       Integer count) {
        UUID limitId = UUID.randomUUID();

        Limit limit = Limit.builder()
            .id(limitId)
            .name(limitName)
            .maxAmount(new BigDecimal("999999"))
            .maxCount(20_000)
            .build();

        CardCustomLimitCompositeKey id = new CardCustomLimitCompositeKey();
        id.setCardId(cardId);
        id.setLimitId(limitId);

        return CardCustomLimit.builder()
            .id(id)
            .limit(limit)
            .currentAmount(amount)
            .currentCount(count)
            .build();
    }
}
