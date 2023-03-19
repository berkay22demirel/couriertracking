package dev.berkaydemirel.couriertracking.repository;

import dev.berkaydemirel.couriertracking.entity.CourierTracking;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CourierTrackingRepository extends JpaRepository<CourierTracking, Long> {

    Optional<CourierTracking> findTopByStoreIdAndCourierIdOrderByIdDesc(Long storeId, Long courierId);
}
