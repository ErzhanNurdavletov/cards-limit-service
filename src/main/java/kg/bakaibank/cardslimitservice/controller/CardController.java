package kg.bakaibank.cardslimitservice.controller;

import kg.bakaibank.cardslimitservice.service.CardService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CardController {
    private final CardService cardService;
}
