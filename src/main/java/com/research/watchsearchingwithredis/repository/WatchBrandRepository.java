package com.research.watchsearchingwithredis.repository;

import com.research.watchsearchingwithredis.model.WatchBrand;
import jakarta.transaction.Transactional;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface WatchBrandRepository extends JpaRepository<WatchBrand, UUID> {
    @Query("SELECT wb FROM WatchBrand AS wb WHERE wb.deleteAt IS NULL")
    @NonNull List<WatchBrand> findAll();

    @Query("SELECT wb FROM WatchBrand AS wb WHERE wb.deleteAt IS NULL AND wb.id = :id")
    @NonNull Optional<WatchBrand> findById(@Param("id") @NonNull UUID id);

    @Transactional
    @NonNull WatchBrand save(@NonNull WatchBrand watchBrand);

    @Modifying
    @Transactional
    @Query("UPDATE WatchBrand AS wb SET wb.name = :name, wb.updateAt = :updateAt WHERE wb.id = :id")
    Integer updateWatchBrand(
            @NonNull UUID id,
            @NonNull String name,
            @NonNull LocalDateTime updateAt);

    @Modifying
    @Transactional
    @Query("UPDATE WatchBrand SET deleteAt = :deleteAt WHERE id = :id")
    Integer updateDeleteAt(
            @Param("id") @NonNull UUID id,
            @Param("deleteAt") @NonNull LocalDateTime deleteAt);
}
