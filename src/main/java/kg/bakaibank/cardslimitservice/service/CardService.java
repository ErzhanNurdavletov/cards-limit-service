package kg.bakaibank.cardslimitservice.service;

import jakarta.persistence.EntityNotFoundException;
import kg.bakaibank.cardslimitservice.config.DefaultLimitsConfig;
import kg.bakaibank.cardslimitservice.entity.*;
import kg.bakaibank.cardslimitservice.mapper.CardCustomLimitMapper;
import kg.bakaibank.cardslimitservice.mapper.CardMapper;
import kg.bakaibank.cardslimitservice.payload.request.CardCreateRequest;
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
    private final ClientService clientService;
    private final LimitService limitService;
    private final LimitsHistoryService limitsHistoryService;
    private final CardCustomLimitService cardCustomLimitService;
    private final CardMapper cardMapper;
    private final CardCustomLimitMapper cardCustomLimitMapper;
    private final DefaultLimitsConfig defaultLimitsConfig;

    @Transactional
    public CardResponse createCard(CardCreateRequest request) {
        Client client = clientService.getClientEntityById(request.clientId());

        Card card = cardMapper.toEntity(request);
        card.setClient(client);
        card.setId(UUID.randomUUID());
        card.setOpenedAt(OffsetDateTime.now());
        // end date +3
        // card type table
        // card issue type
        card.setCustomLimits(new HashSet<>());
        cardRepository.save(card);

        Limit cashInLimit = limitService.getLimitByName(defaultLimitsConfig.getCashIn());
        Limit cashOutLimit = limitService.getLimitByName(defaultLimitsConfig.getCashOut());

        addDefaultLimitsToCard(card, cashInLimit);
        addDefaultLimitsToCard(card, cashOutLimit);

        limitsHistoryService.createLimitHistory(card, cashInLimit);
        limitsHistoryService.createLimitHistory(card, cashOutLimit);

        cardRepository.save(card);
        return cardMapper.toCreateResponse(card, request.clientId());
    }

    private void addDefaultLimitsToCard(Card card, Limit limit) {
        CardCustomLimit customCashInLimit =
            cardCustomLimitService.createCardCustomLimit(card, limit);
        cardCustomLimitService.save(customCashInLimit);
        card.getCustomLimits().add(customCashInLimit);
        if (limit.getCardsCustomLimits() == null) {
            limit.setCardsCustomLimits(new HashSet<>());
        }
        limit.getCardsCustomLimits().add(customCashInLimit);
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
}
