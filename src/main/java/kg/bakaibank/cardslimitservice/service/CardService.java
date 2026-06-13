package kg.bakaibank.cardslimitservice.service;

import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import kg.bakaibank.cardslimitservice.entity.Card;
import kg.bakaibank.cardslimitservice.entity.Client;
import kg.bakaibank.cardslimitservice.entity.Limit;
import kg.bakaibank.cardslimitservice.mapper.CardMapper;
import kg.bakaibank.cardslimitservice.payload.request.CardCreateRequest;
import kg.bakaibank.cardslimitservice.payload.request.CardUpdateRequest;
import kg.bakaibank.cardslimitservice.payload.response.CardResponse;
import kg.bakaibank.cardslimitservice.payload.response.LimitResponse;
import kg.bakaibank.cardslimitservice.repository.CardRepository;
import kg.bakaibank.cardslimitservice.repository.ClientRepository;
import kg.bakaibank.cardslimitservice.repository.LimitRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CardService {
    private final CardRepository cardRepository;
    private final ClientRepository clientRepository;
    private final CardMapper cardMapper;

    @Transactional
    public CardResponse createCard(CardCreateRequest request) {
        Card card = cardMapper.toEntity(request);
        Client client = clientRepository.findClientById(request.clientId())
            .orElseThrow(EntityNotFoundException::new);
        card.setClient(client);
        card.setOpenedAt(OffsetDateTime.now());
        cardRepository.save(card);
        return cardMapper.toResponse(card);
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
}
