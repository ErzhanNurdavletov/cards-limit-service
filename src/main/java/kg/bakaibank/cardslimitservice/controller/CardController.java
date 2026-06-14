package kg.bakaibank.cardslimitservice.controller;

import kg.bakaibank.cardslimitservice.payload.request.CardCreateRequest;
import kg.bakaibank.cardslimitservice.payload.request.CardLimitUpdateRequest;
import kg.bakaibank.cardslimitservice.payload.request.CardUpdateRequest;
import kg.bakaibank.cardslimitservice.payload.response.CardLimitResponse;
import kg.bakaibank.cardslimitservice.payload.response.CardResponse;
import kg.bakaibank.cardslimitservice.service.CardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;
import java.util.UUID;

@RestController
@RequestMapping("api/v1/cards")
@RequiredArgsConstructor
public class CardController {
    private final CardService cardService;

    @PostMapping
    public ResponseEntity<?> createCard(@RequestBody CardCreateRequest request) {
        CardResponse response = cardService.createCard(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{cardId}")
    public ResponseEntity<?> updateCard(@PathVariable UUID cardId,
                                         @RequestBody CardUpdateRequest request) {
        CardResponse response = cardService.updateCard(cardId, request);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping("/{cardId}")
    public ResponseEntity<?> deleteCard(@PathVariable UUID cardId) {
        CardResponse response = cardService.deleteCard(cardId);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/{cardId}")
    public ResponseEntity<?> getCard(@PathVariable UUID cardId) {
        CardResponse response = cardService.getCardById(cardId);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/{cardId}/current-limits")
    public ResponseEntity<?> getCardLimits(@PathVariable UUID cardId) {
        Set<CardLimitResponse> responses = cardService.getCardLimits(cardId);
        return ResponseEntity.status(HttpStatus.OK).body(responses);
    }

    @PutMapping("/{cardId}/limits/{limitId}")
    public ResponseEntity<?> updateCardLimit(@PathVariable UUID cardId,
                                             @PathVariable UUID limitId,
                                             @RequestBody CardLimitUpdateRequest request) {
        CardLimitResponse response = cardService.updateCardLimit(cardId, limitId, request);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
