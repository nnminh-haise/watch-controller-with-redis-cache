package com.research.watchsearchingwithredis.service;

import com.research.watchsearchingwithredis.dto.CreateWatchBrandDto;
import com.research.watchsearchingwithredis.dto.UpdateWatchBrandDto;
import com.research.watchsearchingwithredis.exception.ResourceNotFoundException;
import com.research.watchsearchingwithredis.exception.UnexpectedUpdateException;
import com.research.watchsearchingwithredis.model.WatchBrand;
import com.research.watchsearchingwithredis.repository.WatchBrandRepository;
import lombok.NonNull;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class WatchBrandService {
    private final WatchBrandRepository watchBrandRepository;

    public WatchBrandService(WatchBrandRepository watchBrandRepository) {
        this.watchBrandRepository = watchBrandRepository;
    }

    public List<WatchBrand> findAll() {
        return this.watchBrandRepository.findAll();
    }

    public Optional<WatchBrand> findById(UUID id) {
        return this.watchBrandRepository.findById(id);
    }

    public WatchBrand create(@NonNull CreateWatchBrandDto createWatchBrandDto) {
        try {
            WatchBrand newWatchBrand = new WatchBrand(
                    createWatchBrandDto.getName(),
                    LocalDateTime.now(),
                    LocalDateTime.now());
            return this.watchBrandRepository.save(newWatchBrand);
        }
        catch (DataIntegrityViolationException dataIntegrityViolationException) {
            throw new DataIntegrityViolationException(dataIntegrityViolationException.getMessage());
        }
    }

    public WatchBrand update(UUID id, @NonNull UpdateWatchBrandDto updateWatchBrandDto) {
        Optional<WatchBrand> targetingWatchBrand = this.watchBrandRepository.findById(id);
        if (targetingWatchBrand.isEmpty()) {
            throw new ResourceNotFoundException("Cannot find any watch brand with the given ID");
        }

        WatchBrand updatedWatchBrand = targetingWatchBrand.get();
        updatedWatchBrand.setName(updateWatchBrandDto.getName());
        updatedWatchBrand.setUpdateAt(LocalDateTime.now());
        try {
            Integer updatedRecord = this.watchBrandRepository.updateWatchBrand(
                    updatedWatchBrand.getId(),
                    updatedWatchBrand.getName(),
                    updatedWatchBrand.getUpdateAt());
            if (updatedRecord > 1) {
                throw new UnexpectedUpdateException("Unexpected number of updated record!");
            }
            return updatedWatchBrand;
        }
        catch (DataIntegrityViolationException dataIntegrityViolationException) {
            throw new DataIntegrityViolationException(dataIntegrityViolationException.getMessage());
        }
    }

    public void remove(UUID id) {
        Optional<WatchBrand> targetingWatchBrand = this.watchBrandRepository.findById(id);
        if (targetingWatchBrand.isEmpty()) {
            throw new ResourceNotFoundException("Cannot find any watch brand with the given ID");
        }

        Integer updatedRecord = this.watchBrandRepository.updateDeleteAt(id, LocalDateTime.now());
        if (updatedRecord > 1) {
            throw new UnexpectedUpdateException("Unexpected number of updated record!");
        }
    }
}
