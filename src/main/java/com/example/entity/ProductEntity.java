package com.example.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductEntity {
    private int id;
    private String name;
    private String price;
    private String company;

    public static ProductEntity toModel(com.example.grpc.ProductDto grpcProduct) {
        return ProductEntity.builder()
                .id(grpcProduct.getId())
                .name(grpcProduct.getName())
                .price(grpcProduct.getPrice())
                .company(grpcProduct.getCompany())
                .build();
    }

    public static com.example.grpc.ProductDto toGrpc(ProductEntity products) {
        return com.example.grpc.ProductDto.newBuilder()
                .setId(products.getId())
                .setName(products.getName())
                .setPrice(products.getPrice() == null ?"":products.getPrice())
                .setCompany(products.getCompany() == null ?"":products.getCompany())
                .build();
    }

//    public static ProductDto toModelUpdate(FindProductRequest request) {
//        return ProductDto.builder()
//                .id(request.getId())
//                .name(request.getName())
//                .build();
//    }
}
