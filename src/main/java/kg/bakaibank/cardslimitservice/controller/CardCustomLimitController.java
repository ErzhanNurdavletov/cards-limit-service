package kg.bakaibank.cardslimitservice.controller;

import jakarta.validation.Valid;
import kg.bakaibank.cardslimitservice.payload.request.CardLimitRequest;
import kg.bakaibank.cardslimitservice.payload.request.PaymentPermissionRequest;
import kg.bakaibank.cardslimitservice.payload.response.CardLimitResponse;
import kg.bakaibank.cardslimitservice.payload.response.PaymentPermissionResponse;
import kg.bakaibank.cardslimitservice.service.CardCustomLimitService;
import kg.bakaibank.cardslimitservice.service.facade.CardFacade;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/cards")
@Slf4j
public class CardCustomLimitController {

    private final CardCustomLimitService cardCustomLimitService;
    private final CardFacade cardFacade;

    @PutMapping("/{cardId}/limits/{limitId}")
    public ResponseEntity<?> updateCardLimit(@PathVariable UUID cardId,
                                             @PathVariable UUID limitId,
                                             @Valid @RequestBody CardLimitRequest request) {
        log.info("PUT /api/v1/cards/{}/limits/{} - updateCardLimit request={}", cardId, limitId, request);
        CardLimitResponse response = cardFacade.updateCardCustomLimit(cardId, limitId, request);
        log.info("PUT /api/v1/cards/{}/limits/{} - updateCardLimit response={}", cardId, limitId, response);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/{cardId}/current-limits")
    public ResponseEntity<Set<CardLimitResponse>> getAllCustomLimits(@PathVariable UUID cardId) {
        Set<CardLimitResponse> responses = cardCustomLimitService.findAllByCardId(cardId);
        log.info("GET /api/v1/cards/{}/current-limits ", cardId);
        return ResponseEntity.status(HttpStatus.OK).body(responses);
    }

    @PostMapping("/{cardId}/limits/{limitId}/limit-check")
    public ResponseEntity<?> checkIfPaymentNotExceedLimit(@PathVariable UUID cardId,
                                                          @PathVariable UUID limitId,
                                      @Valid @RequestBody PaymentPermissionRequest request) {
        PaymentPermissionResponse response = cardCustomLimitService.checkIfPaymentInLimit(cardId, limitId, request);
        log.info("POST /api/v1/cards/{}/limits/{}/limit-check" +
            " - checkIfPaymentNotExceedLimit response={}", cardId, limitId, response);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
