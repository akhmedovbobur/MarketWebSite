package com.example.marketwebsite.dto.order;

import com.example.marketwebsite.dto.AddressDto;
import com.example.marketwebsite.dto.profile.ProfileDetailDto;
import com.example.marketwebsite.enums.OrderStatus;
import com.example.marketwebsite.enums.PaymentType;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;


@Getter
@Setter
public class OrderDetailDto {
    private Integer id;
    private ProfileDetailDto profile;

    private String requirement;
    private String contact;
    private AddressDto address;

    private Double deliveryCost;
    private PaymentType paymentType;
    private OrderStatus status;

    private LocalDateTime deliveryDate;
    private LocalDateTime createdDate;

    private List<OrderItemDetailDto> orderItemList;
}
