package com.research.watchsearchingwithredis.repository;

import com.research.watchsearchingwithredis.model.Watch;
import jakarta.transaction.Transactional;
import lombok.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface WatchRepository extends PagingAndSortingRepository<Watch, UUID> {
    @Query("""
        SELECT w FROM Watch AS w
        WHERE
            w.deleteAt IS NULL AND
            ((:typeId IS NULL) OR (w.watchType.id = :typeId)) AND
            ((:brandId IS NULL) OR (w.watchBrand.id = :brandId))
    """)
    @NonNull
    Page<Watch> findAll(
            @Param("typeId") UUID typeId,
            @Param("brandId") UUID brandId,
            @NonNull Pageable pageable);

    @Query("SELECT w FROM Watch AS w WHERE w.deleteAt IS NULL AND w.id = :id")
    @NonNull
    Optional<Watch> findById(@NonNull UUID id);

    void save(@NonNull Watch watch);

    @Modifying
    @Transactional
    @Query("""
        UPDATE Watch AS w SET
            w.name = :name,
            w.description = :description,
            w.price = :price,
            w.quantity = :quantity,
            w.updateAt = :updateAt
        WHERE w.id = :id
    """)
    Integer updateById(
            @NonNull UUID id,
            @NonNull String name,
            @NonNull String description,
            @NonNull Double price,
            @NonNull Integer quantity,
            @NonNull LocalDateTime updateAt);

    @Modifying
    @Transactional
    @Query("UPDATE Watch AS w SET w.deleteAt = :deleteAt WHERE w.id = :id")
    Integer updateDeleteAt(
            @NonNull UUID id,
            @NonNull LocalDateTime deleteAt);
}
