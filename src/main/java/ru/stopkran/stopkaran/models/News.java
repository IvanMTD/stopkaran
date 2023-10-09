package ru.stopkran.stopkaran.models;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class News {
    private long id;
    private String name;
    private String annotation;
    private String content;
    private String image;
}
