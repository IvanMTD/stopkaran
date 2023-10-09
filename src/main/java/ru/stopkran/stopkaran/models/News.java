package ru.stopkran.stopkaran.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

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
    @Column(name = "name")
    private String name;
    @Column(name = "annotation")
    private String annotation;
    @Column(name = "content")
    private String content;
    @Column(name = "image")
    private String image;
    @Column(name = "placed_at")
    private Date placedAt;
    @PrePersist
    void placedAt() {
        this.placedAt = new Date();
    }
}
