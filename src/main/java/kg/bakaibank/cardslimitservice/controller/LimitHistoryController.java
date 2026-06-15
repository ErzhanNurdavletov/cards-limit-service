package kg.bakaibank.cardslimitservice.controller;

import kg.bakaibank.cardslimitservice.service.LimitsHistoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class LimitHistoryController {
    private final LimitsHistoryService limitsHistoryService;
}
