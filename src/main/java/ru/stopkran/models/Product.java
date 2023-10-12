package ru.stopkran.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@Table(name = "product")
@Entity
@NoArgsConstructor(force = true)
public class Product implements Serializable {

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
    @NotNull(message = "Не может быть пустым")
    @Column(name = "coast")
    private BigDecimal coast;
    @Column(name = "image")
    private String image;
    @ManyToOne
    @JoinColumn(name = "category_id", referencedColumnName = "id")
    private Category category;
}
