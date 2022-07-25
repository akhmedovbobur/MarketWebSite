package com.example.marketwebsite.controller;

import com.example.marketwebsite.dto.order.OrderCreateDto;
import com.example.marketwebsite.dto.order.OrderDetailDto;
import com.example.marketwebsite.dto.order.OrderFilterDto;
import com.example.marketwebsite.service.OrderService;
import com.example.marketwebsite.util.SpringSecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("/action/create")
    public ResponseEntity<?> create(@RequestBody OrderCreateDto dto){
        Integer userId = SpringSecurityUtil.getUserId();
        OrderDetailDto result = orderService.create(userId, dto);
        return ResponseEntity.ok(result);
    }

    @PutMapping("/action/{id}")
    public ResponseEntity<?> update(@Valid OrderCreateDto dto,
                                    @PathVariable("id") Integer orderId) {
        Integer userId = SpringSecurityUtil.getUserId();
        OrderDetailDto result = orderService.update(userId, orderId, dto);
        return ResponseEntity.ok().body(result);
    }

    @GetMapping("/action/{id}")
    public ResponseEntity<OrderDetailDto> getById(@PathVariable("id") Integer productId) {
        Integer userID = SpringSecurityUtil.getUserId();
        OrderDetailDto result = orderService.getById(productId);
        return ResponseEntity.ok().body(result);
    }

    @PostMapping("/action/filter")
    public ResponseEntity<Page<OrderDetailDto>> filter(@RequestBody OrderFilterDto filterDto){
        Integer userID = SpringSecurityUtil.getUserId();
        Page<OrderDetailDto> result = orderService.filter(filterDto);
        return ResponseEntity.ok().body(result);

    }
}
