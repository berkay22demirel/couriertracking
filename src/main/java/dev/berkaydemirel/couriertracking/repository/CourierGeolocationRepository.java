package dev.berkaydemirel.couriertracking.repository;

import dev.berkaydemirel.couriertracking.entity.CourierGeolocation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CourierGeolocationRepository extends JpaRepository<CourierGeolocation, Long> {

    Optional<CourierGeolocation> findTopByCourierIdOrderByIdDesc(Long courierId);

    List<CourierGeolocation> findByCourierId(Long courierId);
    
}
