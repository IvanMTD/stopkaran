package ru.stopkran.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@Entity
@Table(name = "category")
@NoArgsConstructor(force = true)
public class Category implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @NotBlank(message = "Поле не может быть пустым")
    @Column(name = "name")
    private String name;
    @NotBlank(message = "Поле не может быть пустым")
    @Column(name = "description")
    private String description;
    @Column(name = "image")
    private String image;
    @ManyToMany(targetEntity = Product.class, fetch = FetchType.EAGER)
    private List<Product> products;
}
