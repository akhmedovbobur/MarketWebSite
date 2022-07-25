package com.example.marketwebsite.service;

import com.example.marketwebsite.dto.ImageDto;
import com.example.marketwebsite.entity.ImageEntity;
import com.example.marketwebsite.entity.ProductImageEntity;
import com.example.marketwebsite.repository.ProductImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductImageService {

    @Autowired
    private ProductImageRepository productImageRepository;

    @Autowired
    private ImageService imageService;

    public void create(Integer imageId, Integer productId) {
        ProductImageEntity entity = new ProductImageEntity();
        entity.setProductId(productId);
        entity.setImageId(imageId);
        productImageRepository.save(entity);
    }

    public void create(List<Integer> imageList, Integer productId) {
        imageList.forEach(id -> create(id, productId));
    }

    public List<ImageDto> getProductImageDtoList(Integer id) {
        List<ProductImageEntity> productImageList = productImageRepository.findAllByProductId(id);
        return productImageList.stream().map(e -> imageService.getImageDto(e.getImageId())).collect(Collectors.toList());
    }

    public List<ImageEntity> getProductImageList(Integer productId) {
        return productImageRepository.findAllByProductId(productId).stream().map(ProductImageEntity::getImage).collect(Collectors.toList());
    }
}
