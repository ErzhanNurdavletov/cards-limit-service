package kg.bakaibank.cardslimitservice.service;

import jakarta.persistence.EntityNotFoundException;
import kg.bakaibank.cardslimitservice.entity.*;
import kg.bakaibank.cardslimitservice.entity.enums.CardStatus;
import kg.bakaibank.cardslimitservice.exception.CardIsBlockedException;
import kg.bakaibank.cardslimitservice.mapper.CardMapper;
import kg.bakaibank.cardslimitservice.payload.request.CardCreateRequest;
import kg.bakaibank.cardslimitservice.payload.request.CardUpdateRequest;
import kg.bakaibank.cardslimitservice.payload.response.CardResponse;
import kg.bakaibank.cardslimitservice.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.util.HashSet;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class CardService {
    private final CardRepository cardRepository;
    private final ClientService clientService;
    private final CardIssueTypeService cardIssueTypeService;
    private final CardMapper cardMapper;

    @Transactional
    public CardResponse updateCard(UUID id, CardUpdateRequest request) {
        Card card = cardRepository.findCardById(id)
            .orElseThrow(() -> new EntityNotFoundException("Card with id: " + id + " not found"));
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
            .orElseThrow(() -> new EntityNotFoundException("Card with id: " + id + " not found"));
        log.debug("Given card info with id: {}", id);
        return cardMapper.toResponse(card);
    }

    public void checkIsCardActive(UUID cardId) {
        Card card = cardRepository.findCardById(cardId)
            .orElseThrow(() -> new EntityNotFoundException("Card with id: " + cardId + " not found"));
        if (card.getStatus() != CardStatus.ACTIVE) {
            throw new CardIsBlockedException("CardId " + cardId + " is blocked");
        }
    }

    public Card createCardWithoutLimits(CardCreateRequest request) {
        Card card = Card.builder()
            .client(clientService.getClientEntityById(request.clientId()))
            .status(request.status())
            .type(request.type())
            .customLimits(new HashSet<>())
            .issueType(cardIssueTypeService.findCardIssueTypeByName(request.cardIssueTypeName()))
            .maskedPan(request.maskedPan())
            .openedAt(OffsetDateTime.now())
            .closedAt(OffsetDateTime.now().plusYears(3))
            .accountId(request.accountId())
            .build();
        cardRepository.save(card);
        return card;
    }
}