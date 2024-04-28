package com.research.watchsearchingwithredis.controller;

import com.research.watchsearchingwithredis.dto.CreateWatchBrandDto;
import com.research.watchsearchingwithredis.dto.UpdateWatchBrandDto;
import com.research.watchsearchingwithredis.exception.ResourceNotFoundException;
import com.research.watchsearchingwithredis.helper.ResponseDto;
import com.research.watchsearchingwithredis.model.WatchBrand;
import com.research.watchsearchingwithredis.service.WatchBrandService;
import lombok.NonNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("api/v1/watch-brands")
public class WatchBrandController {
    private final WatchBrandService watchBrandService;

    public WatchBrandController(WatchBrandService watchBrandService) {
        this.watchBrandService = watchBrandService;
    }

    @GetMapping()
    public ResponseEntity<ResponseDto<List<WatchBrand>>> findAll() {
        List<WatchBrand> watchBrands = this.watchBrandService.findAll();
        if (watchBrands.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ResponseDto.success(watchBrands));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseDto<WatchBrand>> findById(
            @PathVariable(name = "id") @NonNull UUID id) {
        Optional<WatchBrand> watchBrand = this.watchBrandService.findById(id);
        if (watchBrand.isEmpty()) {
            throw new ResourceNotFoundException("Cannot find any watch brand with the given ID");
        }

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ResponseDto.success(watchBrand.get()));
    }

    @PostMapping("")
    public ResponseEntity<ResponseDto<WatchBrand>> create(
            @RequestBody @NonNull CreateWatchBrandDto createWatchBrandDto) {
        WatchBrand newWatchBrand = this.watchBrandService.create(createWatchBrandDto);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ResponseDto.success(newWatchBrand));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ResponseDto<WatchBrand>> update(
            @PathVariable UUID id,
            @RequestBody @NonNull UpdateWatchBrandDto updateWatchBrandDto) {
        WatchBrand updatedWatchBrand = this.watchBrandService.update(id, updateWatchBrandDto);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ResponseDto.success(updatedWatchBrand));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDto<String>> remove(
            @PathVariable UUID id) {
        this.watchBrandService.remove(id);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ResponseDto.success());
    }
}
