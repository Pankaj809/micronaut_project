package com.example.service;

import com.datastax.oss.driver.api.core.CqlSession;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;
import com.example.entity.ProductEntity;

import java.util.List;
import java.util.Optional;


import static org.junit.jupiter.api.Assertions.*;


@MicronautTest
class ProductsServiceTest {


    @Inject
     ProductService productService;

    @Inject
    CqlSession session;

    @Test
    void createProduct() {

        ProductEntity product = new ProductEntity(10, "Laptop", "2000", "Dell");
        ProductEntity savedProduct = productService.createProduct(product);
        assertNotNull(savedProduct);
        assertEquals(product.getId(), savedProduct.getId());
        assertEquals(product.getName(), savedProduct.getName());
        assertEquals(product.getPrice(), savedProduct.getPrice());
        assertEquals(product.getCompany(), savedProduct.getCompany());
    }

    @Test
    void findById() {
        int id= 102;
        String name = "Iphone 14";
//        String company = "Samsung";

        Optional<ProductEntity> product = productService.findById(id, name);
        assertTrue(product.isPresent());

    }

    @Test
    void findProducts() {

        List<ProductEntity> products = productService.findProducts();
        assertFalse(products.isEmpty());

    }

    @Test
    void deleteProduct() {
        ProductEntity product = new ProductEntity();
        product.setId(800);
        product.setName("Test Product");
        product.setPrice("10.99");
        product.setCompany("Test Company");

        productService.createProduct(product);

        productService.deleteProduct(product.getId(),product.getName());

        //finding the deletedproduct
        Optional<ProductEntity> deletedProduct = productService.findById(product.getId(), product.getName());
        assertFalse(deletedProduct.isPresent(), "Product should have deleted");

    }

    @Test
    void updateProduct() {
        ProductEntity product = new ProductEntity();
        product.setId(500);
        product.setName("Iphone 14 PRO");
        product.setPrice("500000.00");
        product.setCompany("The Apple");


        productService.createProduct(product);

        product.setPrice("220000");
        product.setCompany("Apple");

//        ProductDto updateProduct = productService.updateProduct(product.getId(),product);

        Optional<ProductEntity> retriveProduct = productService.findById(product.getId(), product.getName());
        assertTrue(retriveProduct.isPresent(),"Updaed Product");

        assertEquals(product.getPrice(), retriveProduct.get().getPrice(),"Price Updated");
        assertEquals(product.getCompany(), retriveProduct.get().getCompany(), "Compan Updated");


    }
}