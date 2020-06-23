package com.vivek.jpa.entity.ds1;



import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table
public class Book {

    @Id
//    @GeneratedValue(strategy = GenerationType.AUTO)
    @Setter
    @Getter
    private Long id;

    @Setter
    @Getter
    private String name;

    public Book() {
    }

    public Book(Long id, String name) {
        this.id = id;
        this.name = name;
    }

}
