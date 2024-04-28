package com.research.watchsearchingwithredis.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateWatchDto {
    @NotNull(message = "Name cannot be null!")
    private String name;

    private String description;

    @PositiveOrZero(message = "Price must not be negative")
    private Double price;

    @PositiveOrZero(message = "Quantity must not be negative")
    private Integer quantity;
}
