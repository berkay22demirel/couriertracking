package dev.berkaydemirel.couriertracking.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class Courier {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String surname;

}
