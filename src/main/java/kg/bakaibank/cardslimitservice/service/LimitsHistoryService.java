package kg.bakaibank.cardslimitservice.service;

import kg.bakaibank.cardslimitservice.entity.Card;
import kg.bakaibank.cardslimitservice.entity.Limit;
import kg.bakaibank.cardslimitservice.entity.LimitsHistory;
import kg.bakaibank.cardslimitservice.repository.LimitsHistoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class LimitsHistoryService {
    private final LimitsHistoryRepository limitsHistoryRepository;

    public void createLimitHistory(Card card, Limit limit) {
        LimitsHistory limitHistory = new LimitsHistory();
        limitHistory.setCardId(card.getId());
        limitHistory.setChangedAt(OffsetDateTime.now());
        limitHistory.setLimitId(limit.getId());
        limitHistory.setNewAmount(limit.getDefaultAmount());
        limitHistory.setNewCount(limit.getDefaultCount());
        limitsHistoryRepository.save(limitHistory);
        log.info("Created limit history record with id: {}", limitHistory.getId());
    }
}
