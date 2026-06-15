package kg.bakaibank.cardslimitservice.service;

import jakarta.persistence.EntityNotFoundException;
import kg.bakaibank.cardslimitservice.entity.Limit;
import kg.bakaibank.cardslimitservice.mapper.LimitMapper;
import kg.bakaibank.cardslimitservice.payload.request.LimitCreateRequest;
import kg.bakaibank.cardslimitservice.payload.request.LimitUpdateRequest;
import kg.bakaibank.cardslimitservice.payload.response.LimitResponse;
import kg.bakaibank.cardslimitservice.repository.LimitRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class LimitService {
    private final LimitRepository limitRepository;
    private final LimitMapper limitMapper;

    @Transactional
    public LimitResponse createLimit(LimitCreateRequest request) {
        Limit limit = limitMapper.toEntity(request);
        limitRepository.save(limit);
        return limitMapper.toResponse(limit);
    }

    @Transactional
    public LimitResponse deleteLimit(UUID id) {
        Limit limit = limitRepository.findByDeletedAtIsNullAndId(id)
            .orElseThrow(EntityNotFoundException::new);
        limit.setDeletedAt(OffsetDateTime.now());
        limitRepository.save(limit);
        return limitMapper.toResponse(limit);
    }

    @Transactional(readOnly = true)
    public LimitResponse getLimitById(UUID id) {
        Limit limit = limitRepository.findLimitById(id).orElseThrow(EntityNotFoundException::new);
        return limitMapper.toResponse(limit);
    }

    @Transactional
    public LimitResponse changeLimitById(UUID id, LimitUpdateRequest request) {
        Limit limit = limitRepository.findLimitById(id).orElseThrow(EntityNotFoundException::new);
        limitMapper.updateEntity(limit, request);
        limitRepository.save(limit);
        return limitMapper.toResponse(limit);
    }

    public Limit getLimitByName(String name) {
        return limitRepository.findLimitByName(name)
            .orElseThrow(EntityNotFoundException::new);
    }
}
