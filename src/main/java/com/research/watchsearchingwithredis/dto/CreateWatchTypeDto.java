package com.research.watchsearchingwithredis.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateWatchTypeDto {
    @NonNull
    private String name;
}
