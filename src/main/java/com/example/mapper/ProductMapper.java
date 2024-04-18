package com.example.mapper;

import com.example.entity.ProductEntity;
import com.example.grpc.ProductDto;

public class ProductMapper {

    public static ProductEntity toEntity(ProductDto grpcProduct) {

        return ProductEntity.builder()
                .id(grpcProduct.getId())
                .name(grpcProduct.getName())
                .price(grpcProduct.getPrice())
                .company(grpcProduct.getCompany())
                .build();
    }

    public static ProductDto toDto(ProductEntity entity) {
        return ProductDto.newBuilder()
                .setId(entity.getId())
                .setName(entity.getName())
                .setPrice(entity.getPrice() == null ? "" : entity.getPrice())
                .setCompany(entity.getCompany() == null ? "" : entity.getCompany())
                .build();
    }
}
