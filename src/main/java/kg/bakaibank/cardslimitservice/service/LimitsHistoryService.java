package kg.bakaibank.cardslimitservice.service;

import kg.bakaibank.cardslimitservice.repository.LimitsHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LimitsHistoryService {
    private final LimitsHistoryRepository limitsHistoryRepository;
}
