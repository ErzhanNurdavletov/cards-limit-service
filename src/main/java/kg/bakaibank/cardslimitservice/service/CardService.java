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
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class CardService {
    private final CardRepository cardRepository;
    private final ClientService clientService;
    private final LimitService limitService;
    private final LimitsHistoryService limitsHistoryService;
    private final CardCustomLimitService cardCustomLimitService;
    private final CardIssueTypeService cardIssueTypeService;
    private final CardMapper cardMapper;
    private final CardCustomLimitMapper cardCustomLimitMapper;
    private final DefaultLimitsConfig defaultLimitsConfig;

    @Transactional
    public CardResponse createCard(CardCreateRequest request) {
        Client client = clientService.getClientEntityById(request.clientId());
        CardIssueType cardIssueType = cardIssueTypeService
            .findCardIssueTypeByName(request.cardIssueTypeName().trim());

        Card card = cardMapper.toEntity(request);
        card.setClient(client);
        card.setIssueType(cardIssueType);
        card.setId(UUID.randomUUID());
        card.setOpenedAt(OffsetDateTime.now());
        card.setClosedAt(OffsetDateTime.now().plusYears(3));
        card.setCustomLimits(new HashSet<>());
        cardRepository.save(card);

        Limit cashInLimit = limitService.getLimitByName(defaultLimitsConfig.getCashIn());
        Limit cashOutLimit = limitService.getLimitByName(defaultLimitsConfig.getCashOut());

        cardCustomLimitService.addDefaultLimitToCard(card, cashInLimit);
        cardCustomLimitService.addDefaultLimitToCard(card, cashOutLimit);

        limitsHistoryService.createLimitHistory(card, cashInLimit);
        limitsHistoryService.createLimitHistory(card, cashOutLimit);

        cardRepository.save(card);
        log.info("Saved card with id: {}", card.getId());
        return cardMapper.toCreateResponse(card, request.clientId(), cardIssueType.getName());
    }

    @Transactional
    public CardResponse updateCard(UUID id, CardUpdateRequest request) {
        Card card = cardRepository.findCardById(id)
            .orElseThrow(() -> new EntityNotFoundException("Card not found"));
        CardIssueType cardIssueType = cardIssueTypeService
            .findCardIssueTypeByName(request.cardIssueTypeName().trim());
        cardMapper.updateEntity(card, request);
        card.setIssueType(cardIssueType);
        cardRepository.save(card);
        log.info("Updated card with id: {}", id);
        return cardMapper.toResponse(card);
    }

    @Transactional(readOnly = true)
    public CardResponse getCardById(UUID id) {
        Card card = cardRepository.findCardById(id)
            .orElseThrow(() -> new EntityNotFoundException("Card not found"));
        log.debug("Given card info with id: {}", id);
        return cardMapper.toResponse(card);
    }

    @Transactional(readOnly = true)
    public Set<CardLimitResponse> getCardLimits(UUID cardId) {
        Card card = cardRepository.findCardById(cardId)
            .orElseThrow(() -> new EntityNotFoundException("Card not found"));
        return cardCustomLimitMapper.toCardLimitsResponses(card.getCustomLimits());
    }

    public Card getCardEntityById(UUID cardId) {
        return cardRepository.findCardById(cardId)
            .orElseThrow(() -> new EntityNotFoundException("Card not found"));
    }
}