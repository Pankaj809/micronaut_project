package com.example.entity;


//import com.datastax.oss.driver.api.core.uuid.Uuids;
import com.datastax.oss.driver.api.mapper.annotations.Entity;
import io.micronaut.core.annotation.Introspected;
import io.micronaut.serde.annotation.SerdeImport;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

//import java.util.UUID;

@Data
@SerdeImport
@NoArgsConstructor
@AllArgsConstructor
@Introspected
public class Product {
    private int id;
    private String name;
    private String price;
    private String company;
}
