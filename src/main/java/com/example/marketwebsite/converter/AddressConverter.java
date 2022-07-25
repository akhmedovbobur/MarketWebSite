package com.example.marketwebsite.converter;

import com.example.marketwebsite.dto.AddressDto;
import com.example.marketwebsite.entity.AddressEntity;

public class AddressConverter {
    public static AddressEntity toEntity(AddressDto dto) {
        AddressEntity addressEntity = new AddressEntity();
        addressEntity.setDistrict(dto.getDistrict());
        addressEntity.setStreet(dto.getStreet());
        addressEntity.setHome(dto.getHome());
        return addressEntity;
    }
    public static AddressDto toDto(AddressEntity address) {
        AddressDto addressDto = new AddressDto();
        addressDto.setDistrict(address.getDistrict());
        addressDto.setStreet(address.getStreet());
        addressDto.setHome(address.getHome());
        return addressDto;
    }
}
