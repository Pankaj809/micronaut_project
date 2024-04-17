//package com.example.controller;
//
//
//
//import com.example.entity.Product;
//import com.example.service.ProductService;
//import io.micronaut.http.annotation.*;
//
//import java.util.List;
//import java.util.Optional;
//
//@Controller("/product")
//public class ProductController {
//    private final ProductService productService;
//
//    public ProductController(ProductService productService) {
//        this.productService = productService;
//    }
//
//    @Post("/create")
//    public Product insertProduct(@Body Product product){
//        return productService.createProduct(product);
//    }
//
//   @Get("/findBy/{id}/{name}")
//    public Optional<Product> findById(@PathVariable int id, @PathVariable String name){
//        return productService.findById(id,name);
//    }
//
//    @Get("/findAll")
//    public List<?> findAll(){
//        return productService.findProducts();
//    }
//
//    @Delete("/delete/{id}/{name}")
//    public void deleteProducts(@PathVariable int id, @PathVariable String name){
//        productService.deleteProduct(id, name);
//    }
//
//    @Put("/update/{id}")
//
//    public Product updateProduct(@PathVariable int id, @Body Product updatedProduct){
//        return productService.updateProduct(id, updatedProduct);
//    }
//
//}
