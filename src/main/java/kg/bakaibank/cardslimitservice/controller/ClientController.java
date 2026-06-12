package kg.bakaibank.cardslimitservice.controller;

import kg.bakaibank.cardslimitservice.dto.ClientRequest;
import kg.bakaibank.cardslimitservice.dto.ClientResponse;
import kg.bakaibank.cardslimitservice.dto.ClientUpdateRequest;
import kg.bakaibank.cardslimitservice.service.ClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("api/v1/clients")
@RequiredArgsConstructor
public class ClientController {
    private final ClientService clientService;

    @PostMapping
    public ResponseEntity<?> createClient(
        @RequestBody ClientRequest clientRequest) {
        ClientResponse response = clientService.createClient(clientRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteClient(@PathVariable UUID id) {
        ClientResponse response = clientService.deleteClientById(id);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getClientById(@PathVariable UUID id) {
        ClientResponse clientResponse = clientService.getClientById(id);
        return ResponseEntity.status(HttpStatus.FOUND).body(clientResponse);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> changeClientById(@PathVariable UUID id, @RequestBody ClientUpdateRequest request) {
        ClientResponse clientResponse = clientService.changeClientById(id, request);
        return ResponseEntity.status(HttpStatus.OK).body(clientResponse);
    }
}
