package com.research.watchsearchingwithredis.controller;

import com.research.watchsearchingwithredis.dto.CreateWatchDto;
import com.research.watchsearchingwithredis.dto.UpdateWatchDto;
import com.research.watchsearchingwithredis.exception.ResourceNotFoundException;
import com.research.watchsearchingwithredis.helper.ResponseDto;
import com.research.watchsearchingwithredis.model.Watch;
import com.research.watchsearchingwithredis.service.WatchService;
import lombok.NonNull;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("api/v1/watches")
public class WatchController {
    private final WatchService watchService;

    public WatchController(WatchService watchService) {
        this.watchService = watchService;
    }

    @GetMapping("")
    public ResponseEntity<ResponseDto<List<Watch>>> findAll(
            @RequestParam(name = "size", defaultValue = "10") @NonNull Integer size,
            @RequestParam(name = "page", defaultValue = "0") @NonNull Integer page,
            @RequestParam(name = "sort_by", defaultValue = "asc") @NonNull String sortBy,
            @RequestParam(name = "type_id", required = false) UUID typeId,
            @RequestParam(name = "brand_id", required = false) UUID brandId) {
        if (!sortBy.equalsIgnoreCase("asc") && !sortBy.equalsIgnoreCase("desc")) {
            throw new IllegalArgumentException("Invalid sort by value!");
        }
        List<Watch> watches = this.watchService.findAll(size, page, sortBy, typeId, brandId);
        if (watches.isEmpty()) {
            throw new ResourceNotFoundException("Cannot find any watch!");
        }
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ResponseDto.success(watches));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseDto<Watch>> findById(
            @PathVariable @NonNull UUID id) {
        Optional<Watch> watch = this.watchService.findById(id);
        if (watch.isEmpty()) {
            throw new ResourceNotFoundException("Cannot find any watch with the given ID!");
        }
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ResponseDto.success(watch.get()));
    }

    @PostMapping()
    public ResponseEntity<ResponseDto<Watch>> create(
            @RequestBody @NonNull CreateWatchDto createWatchDto) {
        Watch newWatch = this.watchService.create(createWatchDto);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ResponseDto.success(newWatch));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ResponseDto<Watch>> update(
            @PathVariable @NonNull UUID id,
            @RequestBody @NonNull UpdateWatchDto updateWatchDto) {
        Watch updatedWatch = this.watchService.update(id, updateWatchDto);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ResponseDto.success(updatedWatch));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDto<String>> remove(
            @PathVariable @NonNull UUID id) {
        this.watchService.remove(id);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ResponseDto.success());
    }
}
