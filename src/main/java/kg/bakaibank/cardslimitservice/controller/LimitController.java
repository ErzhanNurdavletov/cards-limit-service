package kg.bakaibank.cardslimitservice.controller;

import kg.bakaibank.cardslimitservice.entity.Limit;
import kg.bakaibank.cardslimitservice.payload.request.LimitCreateRequest;
import kg.bakaibank.cardslimitservice.payload.request.LimitUpdateRequest;
import kg.bakaibank.cardslimitservice.payload.response.LimitResponse;
import kg.bakaibank.cardslimitservice.service.LimitService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("api/v1/limits")
@RequiredArgsConstructor
public class LimitController {
    private final LimitService limitService;

    @PostMapping
    public ResponseEntity<?> createLimit(@RequestBody LimitCreateRequest request) {
        LimitResponse response = limitService.createLimit(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateLimit(@PathVariable UUID id,
                                         @RequestBody LimitUpdateRequest request) {
        LimitResponse response = limitService.changeLimitById(id, request);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteLimit(@PathVariable UUID id) {
        LimitResponse response = limitService.deleteLimit(id);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getLimit(@PathVariable UUID id) {
        LimitResponse response = limitService.getLimitById(id);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
