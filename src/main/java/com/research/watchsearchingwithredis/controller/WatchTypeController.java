package com.research.watchsearchingwithredis.controller;

import com.research.watchsearchingwithredis.dto.CreateWatchTypeDto;
import com.research.watchsearchingwithredis.dto.UpdateWatchTypeDto;
import com.research.watchsearchingwithredis.exception.ResourceNotFoundException;
import com.research.watchsearchingwithredis.helper.ResponseDto;
import com.research.watchsearchingwithredis.model.WatchType;
import com.research.watchsearchingwithredis.service.WatchTypeService;
import lombok.NonNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("api/v1/watch-types")
public class WatchTypeController {
    private final WatchTypeService watchTypeService;

    public WatchTypeController(WatchTypeService watchTypeService) {
        this.watchTypeService = watchTypeService;
    }

    @GetMapping()
    public ResponseEntity<ResponseDto<List<WatchType>>> findAll() {
        List<WatchType> watchTypes = this.watchTypeService.findAll();
        if (watchTypes.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ResponseDto.success(watchTypes));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseDto<WatchType>> findById(
            @PathVariable(name = "id") @NonNull UUID id) {
        Optional<WatchType> watchType = this.watchTypeService.findById(id);
        if (watchType.isEmpty()) {
            throw new ResourceNotFoundException("Cannot find any watch type with the given ID");
        }

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ResponseDto.success(watchType.get()));
    }

    @PostMapping("")
    public ResponseEntity<ResponseDto<WatchType>> create(
            @RequestBody @NonNull CreateWatchTypeDto createWatchTypeDto) {
        WatchType newWatchType = this.watchTypeService.create(createWatchTypeDto);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ResponseDto.success(newWatchType));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ResponseDto<WatchType>> update(
            @PathVariable UUID id,
            @RequestBody @NonNull UpdateWatchTypeDto updateWatchTypeDto) {
        WatchType updatedWatchType = this.watchTypeService.update(id, updateWatchTypeDto);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ResponseDto.success(updatedWatchType));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDto<String>> remove(
            @PathVariable UUID id) {
        this.watchTypeService.remove(id);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ResponseDto.success());
    }
}
