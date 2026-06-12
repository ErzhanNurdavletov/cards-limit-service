package kg.bakaibank.cardslimitservice.controller;

import kg.bakaibank.cardslimitservice.service.LimitsHistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class LimitHistoryController {
    private final LimitsHistoryService limitsHistoryService;
}
