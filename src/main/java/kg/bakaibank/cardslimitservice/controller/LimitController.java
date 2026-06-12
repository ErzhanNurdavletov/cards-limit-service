package kg.bakaibank.cardslimitservice.controller;

import kg.bakaibank.cardslimitservice.service.LimitService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class LimitController {
    private final LimitService limitService;
}
