package com.example.service;

import com.example.entity.ProductEntity;
import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.cql.PreparedStatement;
import com.datastax.oss.driver.api.core.cql.ResultSet;
import com.datastax.oss.driver.api.core.cql.Row;
import io.micronaut.core.annotation.NonNull;

import jakarta.inject.Singleton;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Singleton
public class ProductService {
    private final CqlSession cqlSession;
    private final PreparedStatement createStatement;
    private final String CREATE_QUERY = "INSERT INTO pankaj.products(id, name, price, company) VALUES (?, ?, ?, ?)";

    private final PreparedStatement findStatement;
    private final String FIND_BY_ID = "SELECT * FROM pankaj.products WHERE id = ? AND name = ?";

    private final PreparedStatement findAllStatement;
    private final String FIND_ALL_PRODUCTS = "SELECT * FROM pankaj.products";

    private final PreparedStatement deleteStatement;
    private final String DELETE_QUERY = "DELETE FROM pankaj.products WHERE id = ? AND name = ?";

    private final PreparedStatement updateStatement;
    private final String UPDATE_QUERY = "UPDATE pankaj.products SET price = ?, company = ? WHERE id = ? AND name = ?";

    public ProductService(CqlSession cqlSession) {
        this.cqlSession = cqlSession;
        this.createStatement = cqlSession.prepare(CREATE_QUERY);
        this.findStatement = cqlSession.prepare(FIND_BY_ID);
        this.findAllStatement = cqlSession.prepare(FIND_ALL_PRODUCTS);
        this.deleteStatement = cqlSession.prepare(DELETE_QUERY);
        this.updateStatement = cqlSession.prepare(UPDATE_QUERY);
    }

    public ProductEntity createProduct(ProductEntity product) {
        cqlSession.execute(
                createStatement.bind(product.getId(), product.getName(), product.getPrice(), product.getCompany())
        );
        return product;
    }

    public Optional<ProductEntity> findById(int id, String name) {
        ResultSet resultSet = cqlSession.execute(findStatement.bind(id, name));
        Row row = resultSet.one();
        if (row != null) {
            ProductEntity product = new ProductEntity();
            product.setId(row.getInt("id"));
            product.setName(row.getString("name"));
            product.setPrice(row.getString("price"));
            product.setCompany(row.getString("company"));
            return Optional.of(product);
        }
        return Optional.empty();
    }

    public List<ProductEntity> findProducts() {
        List<ProductEntity> products = new ArrayList<>();
        ResultSet resultSet = cqlSession.execute(findAllStatement.bind());
        for (Row row : resultSet) {
            ProductEntity product = new ProductEntity();
            product.setId(row.getInt("id"));
            product.setName(row.getString("name"));
            product.setPrice(row.getString("price"));
            product.setCompany(row.getString("company"));
            products.add(product);
        }
        return products;
    }

    public void deleteProduct(int id, String name) {
        cqlSession.execute(deleteStatement.bind(id, name));
    }

    public ProductEntity updateProduct(@NonNull ProductEntity updatedProduct) {
        Optional<ProductEntity> existingProduct = findById(updatedProduct.getId(), updatedProduct.getName());

        if (existingProduct.isPresent()) {
            ProductEntity existingProductDto = existingProduct.get();
            cqlSession.execute(updateStatement.bind(updatedProduct.getPrice(), updatedProduct.getCompany(), existingProductDto.getId(), existingProductDto.getName()));
            existingProductDto.setPrice(updatedProduct.getPrice());
            existingProductDto.setCompany(updatedProduct.getCompany());
            return existingProductDto;
        } else {
            throw new IllegalArgumentException("Product not found for id " + updatedProduct.getId() + " and name " +
                    updatedProduct.getName());
        }
    }
}
