package com.research.watchsearchingwithredis.service;

import com.research.watchsearchingwithredis.dto.CreateWatchTypeDto;
import com.research.watchsearchingwithredis.dto.UpdateWatchTypeDto;
import com.research.watchsearchingwithredis.exception.ResourceNotFoundException;
import com.research.watchsearchingwithredis.exception.UnexpectedUpdateException;
import com.research.watchsearchingwithredis.model.WatchType;
import com.research.watchsearchingwithredis.repository.WatchTypeRepository;
import lombok.NonNull;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class WatchTypeService {
    private final WatchTypeRepository watchTypeRepository;

    public WatchTypeService(WatchTypeRepository watchTypeRepository) {
        this.watchTypeRepository = watchTypeRepository;
    }

    public List<WatchType> findAll() {
        return this.watchTypeRepository.findAll();
    }

    public Optional<WatchType> findById(UUID id) {
        return this.watchTypeRepository.findById(id);
    }

    public WatchType create(@NonNull CreateWatchTypeDto createWatchTypeDto) {
        try {
            WatchType newWatchType = new WatchType(
                    createWatchTypeDto.getName(),
                    LocalDateTime.now(),
                    LocalDateTime.now());
            return this.watchTypeRepository.save(newWatchType);
        }
        catch (DataIntegrityViolationException dataIntegrityViolationException) {
            throw new DataIntegrityViolationException(dataIntegrityViolationException.getMessage());
        }
    }

    public WatchType update(UUID id, @NonNull UpdateWatchTypeDto updateWatchTypeDto) {
        Optional<WatchType> targetingWatchType = this.watchTypeRepository.findById(id);
        if (targetingWatchType.isEmpty()) {
            throw new ResourceNotFoundException("Cannot find any watch type with the given ID");
        }

        WatchType updatedWatchType = targetingWatchType.get();
        updatedWatchType.setName(updatedWatchType.getName());
        updatedWatchType.setUpdateAt(LocalDateTime.now());
        try {
            Integer updatedRecord = this.watchTypeRepository.updateWatchType(
                    updatedWatchType.getId(),
                    updatedWatchType.getName(),
                    updatedWatchType.getUpdateAt());
            if (updatedRecord > 1) {
                throw new UnexpectedUpdateException("Unexpected number of updated record!");
            }
            return updatedWatchType;
        }
        catch (DataIntegrityViolationException dataIntegrityViolationException) {
            throw new DataIntegrityViolationException(dataIntegrityViolationException.getMessage());
        }
    }

    public void remove(UUID id) {
        Optional<WatchType> targetingWatchType = this.watchTypeRepository.findById(id);
        if (targetingWatchType.isEmpty()) {
            throw new ResourceNotFoundException("Cannot find any watch type with the given ID");
        }

        Integer updatedRecord = this.watchTypeRepository.updateDeleteAt(id, LocalDateTime.now());
        if (updatedRecord > 1) {
            throw new UnexpectedUpdateException("Unexpected number of updated record!");
        }
    }
}
