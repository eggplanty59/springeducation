package ru.education.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Transient;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name ="product")
@NoArgsConstructor
@Getter
@Setter
public class Product {

    @Transient
   public static String TYPE_NAME = "Продукт";

    public Product(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    @Id
    @Column(name= "id", nullable = false)
    private Integer id;

    @Column(name = "name")
    private String name;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
