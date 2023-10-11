package ru.stopkran.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@Entity
@Table(name = "news")
@NoArgsConstructor(force = true)
public class News implements Serializable{

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @NotBlank(message = "Поле не может быть пустым")
    @Column(name = "name")
    private String name;
    @NotBlank(message = "Поле не может быть пустым")
    @Column(name = "annotation")
    private String annotation;
    @NotBlank(message = "Поле не может быть пустым")
    @Column(name = "content")
    private String content;
    @Column(name = "image")
    //@Lob
    private String image;
    @Column(name = "placed_at")
    private Date placedAt;
    @PrePersist
    void placedAt() {
        this.placedAt = new Date();
    }
}
