package dev.berkaydemirel.couriertracking.repository;

import dev.berkaydemirel.couriertracking.entity.CourierLocation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourierLocationRepository extends JpaRepository<CourierLocation, Long> {
}
