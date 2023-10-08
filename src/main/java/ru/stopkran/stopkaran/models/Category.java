package ru.stopkran.stopkaran.models;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Data
@RequiredArgsConstructor
public class Category {
    private long id;
    private String name;
    private String description;
    private String image;
    private List<Product> products;
}
