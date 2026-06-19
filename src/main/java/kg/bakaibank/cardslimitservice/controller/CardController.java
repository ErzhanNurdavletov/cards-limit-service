package kg.bakaibank.cardslimitservice.controller;

import jakarta.validation.Valid;
import kg.bakaibank.cardslimitservice.payload.request.CardCreateRequest;
import kg.bakaibank.cardslimitservice.payload.request.CardUpdateRequest;
import kg.bakaibank.cardslimitservice.payload.response.CardResponse;
import kg.bakaibank.cardslimitservice.service.facade.CardFacade;
import kg.bakaibank.cardslimitservice.service.CardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("api/v1/cards")
@RequiredArgsConstructor
@Slf4j
public class CardController {

    private final CardService cardService;
    private final CardFacade cardFacade;

    @PostMapping
    public ResponseEntity<?> createCard(@Valid @RequestBody CardCreateRequest request) {
        CardResponse response = cardFacade.createCard(request);
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

    @GetMapping("/{cardId}")
    public ResponseEntity<?> getCard(@PathVariable UUID cardId) {
        CardResponse response = cardService.getCardById(cardId);
        log.info("GET /api/v1/cards/{} - getCard response={}", cardId, response);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
