package dev.berkaydemirel.couriertracking.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class CourierLocation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long courierId;

    @Column(nullable = false)
    private double lat;

    @Column(nullable = false)
    private double lng;

    @Column(nullable = false)
    private double travelDistance;
}
