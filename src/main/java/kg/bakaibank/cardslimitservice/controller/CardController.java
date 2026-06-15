package kg.bakaibank.cardslimitservice.controller;

import jakarta.validation.Valid;
import kg.bakaibank.cardslimitservice.payload.request.CardCreateRequest;
import kg.bakaibank.cardslimitservice.payload.request.CardLimitUpdateRequest;
import kg.bakaibank.cardslimitservice.payload.request.CardUpdateRequest;
import kg.bakaibank.cardslimitservice.payload.response.CardLimitResponse;
import kg.bakaibank.cardslimitservice.payload.response.CardResponse;
import kg.bakaibank.cardslimitservice.service.CardCustomLimitService;
import kg.bakaibank.cardslimitservice.service.CardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;
import java.util.UUID;

@RestController
@RequestMapping("api/v1/cards")
@RequiredArgsConstructor
@Slf4j
public class CardController {
    private final CardService cardService;
    private final CardCustomLimitService cardCustomLimitService;

    @PostMapping
    public ResponseEntity<?> createCard(@Valid @RequestBody CardCreateRequest request) {
        CardResponse response = cardService.createCard(request);
        log.info("POST /api/v1/cards - createCard response={}", response);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{cardId}")
    public ResponseEntity<?> updateCard(@PathVariable UUID cardId,
                                         @Valid @RequestBody CardUpdateRequest request) {
        CardResponse response = cardService.updateCard(cardId, request);
        log.info("PUT /api/v1/cards/{} - updateCard response={}", cardId, response);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

//    @DeleteMapping("/{cardId}")
//    public ResponseEntity<?> deleteCard(@PathVariable UUID cardId) {
//        CardResponse response = cardService.deleteCard(cardId);
//        log.info("DELETE /api/v1/cards/{} - deleteCard response={}", cardId, response);
//        return ResponseEntity.status(HttpStatus.OK).body(response);
//    }

    @GetMapping("/{cardId}")
    public ResponseEntity<?> getCard(@PathVariable UUID cardId) {
        CardResponse response = cardService.getCardById(cardId);
        log.info("GET /api/v1/cards/{} - getCard response={}", cardId, response);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/{cardId}/current-limits")
    public ResponseEntity<?> getCardLimits(@PathVariable UUID cardId) {
        Set<CardLimitResponse> responses = cardService.getCardLimits(cardId);
        log.info("GET /api/v1/cards/{}/current-limits ", cardId);
        return ResponseEntity.status(HttpStatus.OK).body(responses);
    }

    @PutMapping("/{cardId}/limits/{limitId}")
    public ResponseEntity<?> updateCardLimit(@PathVariable UUID cardId,
                                             @PathVariable UUID limitId,
                                             @Valid @RequestBody CardLimitUpdateRequest request) {
        CardLimitResponse response = cardCustomLimitService.updateCardLimit(cardId, limitId, request);
        log.info("PUT /api/v1/cards/{}/limits/{} - updateCardLimit response={}", cardId, limitId, response);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
