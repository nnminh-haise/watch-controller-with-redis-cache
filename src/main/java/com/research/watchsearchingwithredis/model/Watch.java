package com.research.watchsearchingwithredis.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "watches", uniqueConstraints = {@UniqueConstraint(columnNames = {"type_id", "brand_id"})})
@AttributeOverrides({
        @AttributeOverride(name = "createAt", column = @Column(name = "create_at")),
        @AttributeOverride(name = "updateAt", column = @Column(name = "update_at")),
        @AttributeOverride(name = "deleteAt", column = @Column(name = "delete_at"))
})
public class Watch extends BaseEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(unique = true, nullable = false)
    private String name;

    @Column(nullable = true)
    private String description;

    @Column(nullable = false)
    private Double price;

    @Column(nullable = false)
    private Integer quantity;

    @JoinColumn(name = "type_id", referencedColumnName = "id", nullable = false)
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    private WatchType watchType;

    @JoinColumn(name = "brand_id", referencedColumnName = "id", nullable = false)
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    private WatchBrand watchBrand;

    public Watch(
            String name,
            String description,
            Double price,
            Integer quantity,
            WatchType watchType,
            WatchBrand watchBrand,
            LocalDateTime createAt,
            LocalDateTime updateAt) {
        super(createAt, updateAt);
        this.id = null;
        this.name = name;
        this.description = description;
        this.price = price;
        this.quantity = quantity;
        this.watchType = watchType;
        this.watchBrand = watchBrand;
    }
}
