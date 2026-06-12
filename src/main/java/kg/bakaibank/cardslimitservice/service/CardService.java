package kg.bakaibank.cardslimitservice.service;

import kg.bakaibank.cardslimitservice.repository.CardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CardService {
    private final CardRepository cardRepository;
}
