package com.research.watchsearchingwithredis.repository;

import com.research.watchsearchingwithredis.model.WatchType;
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
public interface WatchTypeRepository extends JpaRepository<WatchType, UUID> {
    @Query("SELECT wt FROM WatchType AS wt WHERE wt.deleteAt IS NULL")
    @NonNull List<WatchType> findAll();

    @Query("SELECT wt FROM WatchType AS wt WHERE wt.deleteAt IS NULL AND wt.id = :id")
    @NonNull Optional<WatchType> findById(@Param("id") @NonNull UUID id);

    @Transactional
    @NonNull WatchType save(@NonNull WatchType watchType);

    @Modifying
    @Transactional
    @Query("UPDATE WatchType AS wt SET wt.name = :name, wt.updateAt = :updateAt WHERE wt.id = :id")
    Integer updateWatchType(
            @NonNull UUID id,
            @NonNull String name,
            @NonNull LocalDateTime updateAt);

    @Modifying
    @Transactional
    @Query("UPDATE WatchType SET deleteAt = :deleteAt WHERE id = :id")
    Integer updateDeleteAt(
            @Param("id") @NonNull UUID id,
            @Param("deleteAt") @NonNull LocalDateTime deleteAt);
}
