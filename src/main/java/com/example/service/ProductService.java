package com.example.service;

import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.cql.PreparedStatement;
import com.datastax.oss.driver.api.core.cql.ResultSet;
import com.datastax.oss.driver.api.core.cql.Row;
import com.example.entity.Product;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import jakarta.inject.Singleton;

@Singleton
public class ProductService {
    private final CqlSession cqlSession;
    private final PreparedStatement createStatement;
    private final String CREATE_QUERY = "INSERT INTO pankaj.products(id, name, price, company) VALUES (?, ?, ?, ?)";
    private final PreparedStatement findById;
    private final PreparedStatement findAllProducts;
    private final PreparedStatement deleteProduct;

    private final PreparedStatement updateProduct;

    public ProductService(CqlSession cqlSession) {
        this.cqlSession = cqlSession;
        this.createStatement = cqlSession.prepare(CREATE_QUERY);
        this.findById = cqlSession.prepare("SELECT * FROM pankaj.products WHERE id = ? AND name = ?");
        this.findAllProducts = cqlSession.prepare("SELECT * FROM pankaj.products");
        this.deleteProduct = cqlSession.prepare("DELETE FROM pankaj.products WHERE id = ? AND name = ?");
        this.updateProduct = cqlSession.prepare("UPDATE pankaj.products SET price = ?, company = ? WHERE id = ? AND name = ?");
    }

    public Product createProduct(Product product) {
        cqlSession.execute(
                createStatement.bind(product.getId(), product.getName(), product.getPrice(), product.getCompany())
        );
        return product;
    }

    public Optional<Product> findById(int id, String name) {
        ResultSet resultSet = cqlSession.execute(findById.bind(id, name));
        Row row = resultSet.one();
        if (row != null) {
            Product product = new Product();
            product.setId(row.getInt("id"));
            product.setName(row.getString("name"));
            product.setPrice(row.getString("price"));
            product.setCompany(row.getString("company"));
            return Optional.of(product);
        }
        return Optional.empty();
    }

    public List<Product> findProducts() {
        List<Product> products = new ArrayList<>();
        ResultSet resultSet = cqlSession.execute(findAllProducts.bind());
        for (Row row : resultSet) {
            Product product = new Product();
            product.setId(row.getInt("id"));
            product.setName(row.getString("name"));
            product.setPrice(row.getString("price"));
            product.setCompany(row.getString("company"));
            products.add(product);
        }
        return products;
    }

    public void deleteProduct(int id, String name) {
        cqlSession.execute(deleteProduct.bind(id, name));
    }

    public Product updateProduct(int id, Product updatedProduct) {
        Optional<Product> existingProduct = findById(id, updatedProduct.getName());

        if (existingProduct.isEmpty()) {
            throw new IllegalArgumentException("Product not found for id " + id + " and name " + updatedProduct.getName());
        }

        String existingProductName = existingProduct.get().getName();
        existingProduct.get().setName(updatedProduct.getName());
        existingProduct.get().setPrice(updatedProduct.getPrice());
        existingProduct.get().setCompany(updatedProduct.getCompany());

        cqlSession.execute(updateProduct.bind(updatedProduct.getPrice(), updatedProduct.getCompany(), id,
                existingProductName));

        return existingProduct.get();
    }

}
