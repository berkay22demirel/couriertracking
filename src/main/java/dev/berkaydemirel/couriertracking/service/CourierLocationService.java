package dev.berkaydemirel.couriertracking.service;

import dev.berkaydemirel.couriertracking.entity.Courier;
import dev.berkaydemirel.couriertracking.entity.CourierLocation;
import dev.berkaydemirel.couriertracking.exception.NotFoundException;
import dev.berkaydemirel.couriertracking.repository.CourierLocationRepository;
import dev.berkaydemirel.couriertracking.repository.CourierRepository;
import dev.berkaydemirel.couriertracking.util.LocationUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class CourierLocationService {

    private final CourierLocationRepository courierLocationRepository;
    private final CourierRepository courierRepository;

    public CourierLocation create(Long courierId, double lat, double lng) {
        Courier courier = courierRepository.findById(courierId)
                .orElseThrow(() -> new NotFoundException("Courier not found!"));
        CourierLocation courierLocation = CourierLocation.builder()
                .courier(courier)
                .lat(lat)
                .lng(lng)
                .travelDistance(calculateTravelDistance(courierId, lat, lng))
                .build();
        return courierLocationRepository.save(courierLocation);
    }

    public void delete(Long courierId, Long id) {
        courierLocationRepository.deleteByIdAndCourier(id, courierRepository.getReferenceById(courierId));
    }

    public Optional<CourierLocation> findLastByCourier(Long courierId) {
        return courierLocationRepository.findTopByCourierIdOrderByIdDesc(courierId);
    }

    public double getTotalTravelDistance(Long courierId) {
        return courierLocationRepository.findByCourierId(courierId).stream()
                .mapToDouble(CourierLocation::getTravelDistance)
                .sum();
    }

    private double calculateTravelDistance(Long courierId, double lat, double lng) {
        return findLastByCourier(courierId).stream()
                .mapToDouble(courierLocation -> LocationUtil.getInstance().calculateDistance(courierLocation.getLat(), courierLocation.getLng(), lat, lng))
                .findFirst()
                .orElse(0.0);
    }

}
