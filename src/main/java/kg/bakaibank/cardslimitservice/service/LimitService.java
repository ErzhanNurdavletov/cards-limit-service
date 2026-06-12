package kg.bakaibank.cardslimitservice.service;

import kg.bakaibank.cardslimitservice.repository.LimitRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LimitService {
    private final LimitRepository limitRepository;
}
