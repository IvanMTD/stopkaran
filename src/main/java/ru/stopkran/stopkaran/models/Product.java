package ru.stopkran.stopkaran.models;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;

@Data
@RequiredArgsConstructor
public class Product {
    private long id;
    private String name;
    private String description;
    private BigDecimal coast;
    private String image;
}
