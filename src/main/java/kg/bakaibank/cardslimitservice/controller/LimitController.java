package kg.bakaibank.cardslimitservice.controller;

import jakarta.validation.Valid;
import kg.bakaibank.cardslimitservice.payload.request.LimitCreateRequest;
import kg.bakaibank.cardslimitservice.payload.request.LimitUpdateRequest;
import kg.bakaibank.cardslimitservice.payload.response.LimitResponse;
import kg.bakaibank.cardslimitservice.service.LimitService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("api/v1/limits")
@RequiredArgsConstructor
@Slf4j
public class LimitController {
    private final LimitService limitService;

    @PostMapping
    public ResponseEntity<?> createLimit(@Valid @RequestBody LimitCreateRequest request) {
        LimitResponse response = limitService.createLimit(request);
        log.info("POST /api/v1/limits - createLimit response={}", response);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateLimit(@PathVariable UUID id,
                                         @Valid @RequestBody LimitUpdateRequest request) {
        LimitResponse response = limitService.changeLimitById(id, request);
        log.info("PUT /api/v1/limits/{} - updateLimit response={}", id, response);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteLimit(@PathVariable UUID id) {
        LimitResponse response = limitService.deleteLimit(id);
        log.info("DELETE /api/v1/limits/{} - deleteLimit response={}", id, response);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getLimit(@PathVariable UUID id) {
        LimitResponse response = limitService.getLimitById(id);
        log.info("GET /api/v1/limits/{} - getLimit response={}", id, response);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
