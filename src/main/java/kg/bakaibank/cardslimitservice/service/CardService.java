package kg.bakaibank.cardslimitservice.service;

import jakarta.persistence.EntityNotFoundException;
import kg.bakaibank.cardslimitservice.entity.*;
import kg.bakaibank.cardslimitservice.mapper.CardCustomLimitMapper;
import kg.bakaibank.cardslimitservice.mapper.CardMapper;
import kg.bakaibank.cardslimitservice.payload.request.CardCreateRequest;
import kg.bakaibank.cardslimitservice.payload.request.CardLimitUpdateRequest;
import kg.bakaibank.cardslimitservice.payload.request.CardUpdateRequest;
import kg.bakaibank.cardslimitservice.payload.response.CardLimitResponse;
import kg.bakaibank.cardslimitservice.payload.response.CardResponse;
import kg.bakaibank.cardslimitservice.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CardService {
    private final CardRepository cardRepository;
    private final ClientRepository clientRepository;
    private final LimitRepository limitRepository;
    private final LimitsHistoryRepository limitsHistoryRepository;
    private final CardCustomLimitRepository cardCustomLimitRepository;
    private final CardMapper cardMapper;
    private final CardCustomLimitMapper cardCustomLimitMapper;

    @Transactional
    public CardResponse createCard(CardCreateRequest request) {
        Client client = clientRepository.findClientById(request.clientId())
            .orElseThrow(EntityNotFoundException::new);

        Card card = cardMapper.toEntity(request);
        card.setClient(client);
        card.setOpenedAt(OffsetDateTime.now());
        card.setId(UUID.randomUUID());

        Limit cashInLimit = limitRepository.findLimitByName("standart-cashin")
            .orElseThrow(EntityNotFoundException::new);
        Limit cashOutLimit = limitRepository.findLimitByName("standart-cashout")
            .orElseThrow(EntityNotFoundException::new);

        cardRepository.save(card);
        CardCustomLimit customCashInLimit = createCardCustomLimit(card, cashInLimit);
        CardCustomLimit customCashOutLimit = createCardCustomLimit(card, cashOutLimit);
        cardCustomLimitRepository.save(customCashInLimit);
        cardCustomLimitRepository.save(customCashOutLimit);

        Set<CardCustomLimit> customLimits = new HashSet<>();
        customLimits.add(customCashInLimit);
        customLimits.add(customCashOutLimit);

        card.setCustomLimits(customLimits);

        if (cashInLimit.getCardsCustomLimits() == null) {
            cashInLimit.setCardsCustomLimits(new HashSet<>());
        }
        if (cashOutLimit.getCardsCustomLimits() == null) {
            cashOutLimit.setCardsCustomLimits(new HashSet<>());
        }

        cashInLimit.getCardsCustomLimits().add(customCashInLimit);
        cashOutLimit.getCardsCustomLimits().add(customCashOutLimit);

        createLimitHistory(card, cashInLimit);
        createLimitHistory(card, cashOutLimit);

        cardRepository.save(card);
        return cardMapper.toResponse(card);
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
        return compositeKey;
    }


    private void createLimitHistory(Card card, Limit cashInLimit) {
        LimitsHistory cashInlimitsHistory = new LimitsHistory();
        cashInlimitsHistory.setCardId(card.getId());
        cashInlimitsHistory.setChangedAt(OffsetDateTime.now());
        cashInlimitsHistory.setLimitId(cashInLimit.getId());
        cashInlimitsHistory.setNewAmount(cashInLimit.getDefaultAmount());
        cashInlimitsHistory.setNewCount(cashInLimit.getDefaultCount());
        limitsHistoryRepository.save(cashInlimitsHistory);
    }

    @Transactional
    public CardResponse deleteCard(UUID id) {
        Card card = cardRepository.findByDeletedAtIsNullAndId(id)
            .orElseThrow(EntityNotFoundException::new);
        card.setDeletedAt(OffsetDateTime.now());
        cardRepository.save(card);
        return cardMapper.toResponse(card);
    }

    @Transactional
    public CardResponse updateCard(UUID id, CardUpdateRequest request) {
        if (!id.equals(request.id())) {
            throw new IllegalArgumentException();
        }
        Card card = cardRepository.findCardById(id).orElseThrow(EntityNotFoundException::new);
        cardMapper.updateEntity(card, request);
        cardRepository.save(card);
        return cardMapper.toResponse(card);
    }

    @Transactional(readOnly = true)
    public CardResponse getCardById(UUID id) {
        Card card = cardRepository.findCardById(id).orElseThrow(EntityNotFoundException::new);
        return cardMapper.toResponse(card);
    }

    @Transactional(readOnly = true)
    public Set<CardLimitResponse> getCardLimits(UUID cardId) {
        Card card = cardRepository.findCardById(cardId).orElseThrow(EntityNotFoundException::new);

        if (card.getDeletedAt() != null) {
            throw new EntityNotFoundException();
        }
        return cardCustomLimitMapper.toCardLimitsResponses(card.getCustomLimits());
    }

    @Transactional
    public CardLimitResponse updateCardLimit(UUID cardId, UUID limitId,
                                             CardLimitUpdateRequest request) {
        CardCustomLimitCompositeKey id = new CardCustomLimitCompositeKey();
        id.setCardId(cardId);
        id.setLimitId(limitId);
        CardCustomLimit cardCustomLimit = cardCustomLimitRepository.findById(id)
            .orElseThrow(EntityNotFoundException::new);

        cardCustomLimit.setCurrentAmount(request.newAmount());
        cardCustomLimit.setCurrentCount(request.newCount());
        cardCustomLimitRepository.save(cardCustomLimit);
        return cardCustomLimitMapper.toResponse(cardCustomLimit);
    }
}
