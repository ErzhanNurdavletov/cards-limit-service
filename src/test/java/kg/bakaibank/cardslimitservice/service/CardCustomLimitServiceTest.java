package kg.bakaibank.cardslimitservice.service;

import kg.bakaibank.cardslimitservice.entity.*;
import kg.bakaibank.cardslimitservice.entity.enums.CardStatus;
import kg.bakaibank.cardslimitservice.entity.enums.CardType;
import kg.bakaibank.cardslimitservice.entity.enums.ClientType;
import kg.bakaibank.cardslimitservice.exception.CardIsBlockedException;
import kg.bakaibank.cardslimitservice.mapper.CardCustomLimitMapper;
import kg.bakaibank.cardslimitservice.payload.request.CardLimitUpdateRequest;
import kg.bakaibank.cardslimitservice.payload.response.CardLimitResponse;
import kg.bakaibank.cardslimitservice.repository.CardCustomLimitRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
//@Transactional
public class CardCustomLimitServiceTest {

    @Mock
    private CardCustomLimitRepository cardCustomLimitRepository;
    @Mock
    private LimitsHistoryService limitsHistoryService;
    @Mock
    private CardCustomLimitMapper cardCustomLimitMapper;
    @InjectMocks
    private CardCustomLimitService cardCustomLimitService;

    @Test
    public void updateCardLimit_Success() {
        CardLimitUpdateRequest request = new CardLimitUpdateRequest(
            new BigDecimal("6767"),
            67);
        CardCustomLimit cardLimit = createTestCardLimit(CardStatus.ACTIVE);
        CardLimitResponse expectedResponse = new CardLimitResponse(
            cardLimit.getLimit().getId(),
            cardLimit.getLimit().getName(),
            request.newAmount(),
            request.newCount());

        Mockito.when(cardCustomLimitMapper.toResponse(any(CardCustomLimit.class)))
            .thenReturn(expectedResponse);
        Mockito.when(cardCustomLimitRepository
                .findByIdWithLimitName(any(CardCustomLimitCompositeKey.class)))
            .thenReturn(Optional.of(cardLimit));
        Mockito.when(cardCustomLimitRepository.save(any(CardCustomLimit.class)))
            .thenReturn(cardLimit);

        CardLimitResponse actualResponse = cardCustomLimitService
            .updateCardLimit(cardLimit.getCard().getId(),
                cardLimit.getLimit().getId(), request);

        Assertions.assertNotNull(actualResponse);
        Assertions.assertEquals(expectedResponse.limitName(), actualResponse.limitName());
        Assertions.assertEquals(request.newAmount(), actualResponse.currentAmount());
        Assertions.assertEquals(request.newCount(), actualResponse.currentCount());

        Mockito.verify(cardCustomLimitRepository, Mockito.times(1)).findByIdWithLimitName(any());
        Mockito.verify(limitsHistoryService, Mockito.times(1)).createLimitHistory(any(), any(), any(), any(), any(), any());
        Mockito.verify(cardCustomLimitMapper, Mockito.times(1)).toResponse(any());
    }

    @Test
    public void updateCardLimit_ThrowException() {
        CardLimitUpdateRequest request = new CardLimitUpdateRequest(
            new BigDecimal("6767"),
            67);

        CardCustomLimit cardLimit = createTestCardLimit(CardStatus.BLOCKED);

        Mockito.when(cardCustomLimitRepository
                .findByIdWithLimitName(any(CardCustomLimitCompositeKey.class)))
            .thenReturn(Optional.of(cardLimit));

        Assertions.assertThrows(CardIsBlockedException.class, () ->
            cardCustomLimitService.updateCardLimit(
                cardLimit.getCard().getId(),
                cardLimit.getLimit().getId(),
                request));

        Mockito.verify(cardCustomLimitRepository, Mockito.never()).save(any());
    }

    private CardCustomLimit createTestCardLimit(CardStatus status) {
        UUID cardId = UUID.randomUUID();
        UUID limitId = UUID.randomUUID();
        UUID clientId = UUID.randomUUID();
        UUID issueTypeId = UUID.randomUUID();

        Client client = Client.builder()
            .id(clientId)
            .type(ClientType.LEGAL)
            .firstname("firstname")
            .lastname("lastname")
            .phoneNumber("0700700700")
            .createdAt(OffsetDateTime.now())
            .build();

        CardIssueType issueType = CardIssueType.builder()
            .id(issueTypeId)
            .name("VISA")
            .build();

        Card card = Card.builder()
            .id(cardId)
            .client(client)
            .maskedPan("maskedPan")
            .type(CardType.DEBIT)
            .issueType(issueType)
            .status(status)
            .build();

        Limit limit = Limit.builder()
            .id(limitId)
            .name("test-limit")
            .maxAmount(new BigDecimal("999999"))
            .maxCount(20_000)
            .defaultAmount(new BigDecimal("9999"))
            .defaultCount(1_000)
            .build();

        CardCustomLimitCompositeKey id = new CardCustomLimitCompositeKey();
        id.setCardId(cardId);
        id.setLimitId(limitId);

        return CardCustomLimit.builder()
            .id(id)
            .card(card)
            .limit(limit)
            .currentAmount(new BigDecimal("11"))
            .currentCount(11)
            .build();
    }
}
