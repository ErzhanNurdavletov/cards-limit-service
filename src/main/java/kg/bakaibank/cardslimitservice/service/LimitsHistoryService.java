package kg.bakaibank.cardslimitservice.service;

import kg.bakaibank.cardslimitservice.entity.Card;
import kg.bakaibank.cardslimitservice.entity.Limit;
import kg.bakaibank.cardslimitservice.entity.LimitsHistory;
import kg.bakaibank.cardslimitservice.repository.LimitsHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;

@Service
@RequiredArgsConstructor
public class LimitsHistoryService {
    private final LimitsHistoryRepository limitsHistoryRepository;

    public void createLimitHistory(Card card, Limit cashInLimit) {
        LimitsHistory cashInlimitsHistory = new LimitsHistory();
        cashInlimitsHistory.setCardId(card.getId());
        cashInlimitsHistory.setChangedAt(OffsetDateTime.now());
        cashInlimitsHistory.setLimitId(cashInLimit.getId());
        cashInlimitsHistory.setNewAmount(cashInLimit.getDefaultAmount());
        cashInlimitsHistory.setNewCount(cashInLimit.getDefaultCount());
        limitsHistoryRepository.save(cashInlimitsHistory);
    }
}
