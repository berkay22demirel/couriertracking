package dev.berkaydemirel.couriertracking.repository;

import dev.berkaydemirel.couriertracking.entity.Courier;
import dev.berkaydemirel.couriertracking.entity.CourierLocation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CourierLocationRepository extends JpaRepository<CourierLocation, Long> {

    Optional<CourierLocation> findTopByCourierIdOrderByIdDesc(Long courierId);

    List<CourierLocation> findByCourierId(Long courierId);

    void deleteByIdAndCourier(Long id, Courier courier);
}
