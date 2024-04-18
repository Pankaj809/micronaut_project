package com.example.serviceImpl;

import com.example.entity.ProductEntity;
import com.example.exception.ProductNotFoundException;
import com.example.grpc.*;
import com.example.mapper.ProductMapper;
import com.example.service.ProductService;
import io.grpc.Status;
import io.grpc.stub.StreamObserver;
import jakarta.inject.Singleton;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

@Singleton
public class ProductServiceImpl extends ProductServiceGrpc.ProductServiceImplBase {

    private final ProductService productService;

    private static final Logger logger = LoggerFactory.getLogger(ProductServiceImpl.class);

    public ProductServiceImpl(ProductService productService) {
        this.productService = productService;
    }

    @Override
    public void createProduct(ProductDto request, StreamObserver<ProductDto> responseObserver) {
        ProductEntity createdProduct = productService.createProduct(ProductMapper.toEntity(request));
        responseObserver.onNext(ProductMapper.toDto(createdProduct));
        responseObserver.onCompleted();


        //in order to return something, need to include onNext(). EXPLORE MORE
    }

    @Override
    public void deleteProduct(DeleteProductRequest request, StreamObserver<Empty> responseObserver) {
        productService.deleteProduct(request.getId(), request.getName());
        responseObserver.onNext(Empty.getDefaultInstance());
        responseObserver.onCompleted();
    }

    @Override
    public void findAllProducts(Empty request, StreamObserver<ListOfProductRequest> responseObserver) {
        try {
            var products = productService.findProducts();
            logger.info("Found {} products", products.size());
            var listOfProductRequest = products.stream().map(ProductMapper::toDto).toList();
            var rpcProductList = ListOfProductRequest.newBuilder()
                    .addAllProduct(listOfProductRequest)
                    .build();
            responseObserver.onNext(rpcProductList);
        } catch (ProductNotFoundException e) {
            responseObserver.onError(Status.INTERNAL.withDescription(e.getMessage()).asException());
        }
        responseObserver.onCompleted();
    }

    @Override
    public void findProduct(FindProductRequest request, StreamObserver<ProductDto> responseObserver) {
        Optional<ProductEntity> products = productService.findById(request.getId(), request.getName());

        if (products.isEmpty()) {
            responseObserver.onError(Status.NOT_FOUND.withDescription("Product not found").asException());
            return;
        }
        responseObserver.onNext(ProductMapper.toDto(products.get()));
        responseObserver.onCompleted();
    }

    @Override
    public void updateProduct(UpdateProductRequest request, StreamObserver<ProductDto> responseObserver) {
        var productModel = ProductEntity.builder()
                .id(request.getId())
                .name(request.getName())
                .price(request.getPrice())
                .company(request.getCompany())
                .build();
        var updatedProduct = productService.updateProduct(productModel);
        responseObserver.onNext(ProductMapper.toDto(updatedProduct));
        responseObserver.onCompleted();
    }
}
