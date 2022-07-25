package com.example.marketwebsite.dto.order;

import com.example.marketwebsite.dto.product.ProductDetailDto;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class OrderItemDetailDto {

    private Integer id;
    private Integer orderId;
    private ProductDetailDto product;
    private Integer amount;
    private LocalDateTime createdDate;
    private Double price;
}
