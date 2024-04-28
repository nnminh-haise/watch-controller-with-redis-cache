package com.research.watchsearchingwithredis.service;

import com.research.watchsearchingwithredis.dto.CreateWatchDto;
import com.research.watchsearchingwithredis.dto.UpdateWatchDto;
import com.research.watchsearchingwithredis.exception.ResourceNotFoundException;
import com.research.watchsearchingwithredis.exception.UnexpectedUpdateException;
import com.research.watchsearchingwithredis.model.Watch;
import com.research.watchsearchingwithredis.model.WatchBrand;
import com.research.watchsearchingwithredis.model.WatchType;
import com.research.watchsearchingwithredis.repository.WatchBrandRepository;
import com.research.watchsearchingwithredis.repository.WatchRepository;
import com.research.watchsearchingwithredis.repository.WatchTypeRepository;
import lombok.NonNull;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class WatchService {
    private final WatchRepository watchRepository;
    private final WatchTypeRepository watchTypeRepository;
    private final WatchBrandRepository watchBrandRepository;

    public WatchService(
            WatchRepository watchRepository,
            WatchTypeRepository watchTypeRepository,
            WatchBrandRepository watchBrandRepository) {
        this.watchRepository = watchRepository;
        this.watchTypeRepository = watchTypeRepository;
        this.watchBrandRepository = watchBrandRepository;
    }

    public List<Watch> findAll(
            @NonNull Integer size,
            @NonNull Integer page,
            @NonNull String sortBy,
            UUID typeId,
            UUID brandId) {
        Sort sortRequest = sortBy.equalsIgnoreCase("asc")
                ? Sort.by("price").ascending()
                : Sort.by("price").descending();
        Pageable requestingPage = PageRequest.of(page, size, sortRequest);
        return this.watchRepository.findAll(typeId, brandId, requestingPage).stream().toList();
    }

    public Optional<Watch> findById(UUID id) {
        return this.watchRepository.findById(id);
    }

    public Watch create(@NonNull CreateWatchDto createWatchDto) {
        Optional<WatchType> targetingWatchType = this.watchTypeRepository.findById(createWatchDto.getTypeId());
        if (targetingWatchType.isEmpty()) {
            throw new ResourceNotFoundException("Cannot find any watch type with the given type ID!");
        }

        Optional<WatchBrand> targetingWatchBrand = this.watchBrandRepository.findById(createWatchDto.getBrandId());
        if (targetingWatchBrand.isEmpty()) {
            throw new ResourceNotFoundException("Cannot find any watch brand with the given brand ID!");
        }

        Watch newWatch = new Watch(
                createWatchDto.getName(),
                createWatchDto.getDescription(),
                createWatchDto.getPrice(),
                createWatchDto.getQuantity(),
                targetingWatchType.get(),
                targetingWatchBrand.get(),
                LocalDateTime.now(),
                LocalDateTime.now());
        try {
            this.watchRepository.save(newWatch);
        }
        catch (DataIntegrityViolationException dataIntegrityViolationException) {
            throw new DataIntegrityViolationException(dataIntegrityViolationException.getMessage());
        }
        return newWatch;
    }

    public Watch update(
            @NonNull UUID id,
            @NonNull UpdateWatchDto updateWatchDto) {
        Optional<Watch> targetingWatch = this.watchRepository.findById(id);
        if (targetingWatch.isEmpty()) {
            throw new ResourceNotFoundException("Cannot find any watch with the given ID!");
        }

        Watch updatedWatch = targetingWatch.get();
        updatedWatch.setName(updatedWatch.getName());
        updatedWatch.setDescription(updatedWatch.getDescription());
        updatedWatch.setPrice(updatedWatch.getPrice());
        updatedWatch.setQuantity(updatedWatch.getQuantity());
        updatedWatch.setUpdateAt(LocalDateTime.now());
        Integer updatedRecord = this.watchRepository.updateById(
                id,
                updatedWatch.getName(),
                updatedWatch.getDescription(),
                updatedWatch.getPrice(),
                updatedWatch.getQuantity(),
                updatedWatch.getUpdateAt());
        if (updatedRecord > 1) {
            throw new UnexpectedUpdateException("Unexpected number of updated record!");
        }
        return updatedWatch;
    }

    public void remove(@NonNull UUID id) {
        Optional<Watch> targetingWatch = this.watchRepository.findById(id);
        if (targetingWatch.isEmpty()) {
            throw new ResourceNotFoundException("Cannot find any watch with the given ID!");
        }

        Integer updatedRecord = this.watchRepository.updateDeleteAt(id, LocalDateTime.now());
        if (updatedRecord > 1) {
            throw new UnexpectedUpdateException("Unexpected number of updated record!");
        }
    }
}
