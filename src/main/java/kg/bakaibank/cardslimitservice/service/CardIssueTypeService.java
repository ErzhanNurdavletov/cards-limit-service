package kg.bakaibank.cardslimitservice.service;

import jakarta.persistence.EntityNotFoundException;
import kg.bakaibank.cardslimitservice.entity.CardIssueType;
import kg.bakaibank.cardslimitservice.repository.CardIssueTypeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class CardIssueTypeService {
    private final CardIssueTypeRepository cardIssueTypeRepository;

    public CardIssueType findCardIssueTypeByName(String name) {
        return cardIssueTypeRepository.findByNameIgnoreCase(name)
            .orElseThrow(() -> new EntityNotFoundException("cardIssueType with name: "
                + name + " not found"));
    }
}
