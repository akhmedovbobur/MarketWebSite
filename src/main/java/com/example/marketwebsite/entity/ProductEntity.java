package com.example.marketwebsite.entity;

import com.example.marketwebsite.enums.ProductStatus;
import com.example.marketwebsite.enums.ProductType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = ("product"))
public class ProductEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column
    private String name;

    @Column(name = ("description_uz"))
    private String descriptionUz;
    @Column(name = ("description_ru"))
    private String descriptionRu;

    @Column
    private Double price;
    @Column
    private Integer rate;
    @Column(name = ("created_date"))
    private LocalDateTime createdDate;
    @Column(name = ("visible"), columnDefinition = "boolean")
    private Boolean visible;
    @Column
    @Enumerated(EnumType.STRING)
    private ProductType type;
    @Column
    @Enumerated(EnumType.STRING)
    private ProductStatus status;

}
