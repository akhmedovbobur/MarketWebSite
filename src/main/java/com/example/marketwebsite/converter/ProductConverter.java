package com.example.marketwebsite.converter;

import com.example.marketwebsite.dto.product.ProductDetailDto;
import com.example.marketwebsite.entity.ProductEntity;

public class ProductConverter {

    public static ProductEntity toEntity(ProductDetailDto dto){
        ProductEntity entity = new ProductEntity();
        entity.setName(dto.getName());
        entity.setPrice(dto.getPrice());
        entity.setType(dto.getType());
        return entity;
    }

    public static ProductDetailDto toDetail(ProductEntity entity){
        ProductDetailDto dto =new ProductDetailDto();
        dto.setName(entity.getName());
        dto.setPrice(entity.getPrice());
        dto.setType(entity.getType());
        return dto;
    }
}
