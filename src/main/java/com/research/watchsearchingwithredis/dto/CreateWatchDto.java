package com.research.watchsearchingwithredis.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateWatchDto {
    @NonNull
    private String name;

    @NonNull
    private String description;

    @NonNull
    @PositiveOrZero(message = "Price must not be negative!")
    private Double price;

    @NonNull
    @PositiveOrZero(message = "Quantity must not be negative!")
    private Integer quantity;

    @NonNull
    private UUID typeId;

    @NonNull
    private UUID brandId;
}
