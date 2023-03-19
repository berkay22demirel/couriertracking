package dev.berkaydemirel.couriertracking.repository;

import dev.berkaydemirel.couriertracking.entity.Courier;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourierRepository extends JpaRepository<Courier, Long> {
}
