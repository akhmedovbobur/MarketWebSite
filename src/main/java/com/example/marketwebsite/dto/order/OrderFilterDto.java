package com.example.marketwebsite.dto.order;

import com.example.marketwebsite.dto.FilterDto;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderFilterDto extends FilterDto {

    private Integer profileId;
    private Integer productId;
    private String orderDate;
}
