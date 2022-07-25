package com.example.marketwebsite.dto.product;

import com.example.marketwebsite.dto.ImageDto;
import com.example.marketwebsite.enums.ProductStatus;
import com.example.marketwebsite.enums.ProductType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class ProductDetailDto {

    private String name;
    private String description;
    private Double price;
    private Integer rate;
    private LocalDateTime createdDate;
    private ProductType type;
    private ProductStatus status;
    private List<ImageDto> imageList;
}
