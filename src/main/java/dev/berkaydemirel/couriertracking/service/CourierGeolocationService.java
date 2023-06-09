package dev.berkaydemirel.couriertracking.service;

import dev.berkaydemirel.couriertracking.entity.Courier;
import dev.berkaydemirel.couriertracking.entity.CourierGeolocation;
import dev.berkaydemirel.couriertracking.exception.NotFoundException;
import dev.berkaydemirel.couriertracking.repository.CourierGeolocationRepository;
import dev.berkaydemirel.couriertracking.repository.CourierRepository;
import dev.berkaydemirel.couriertracking.util.GeolocationUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class CourierGeolocationService {

    private final List<CourierGeolocationObserver> observers;
    private final CourierGeolocationRepository courierGeolocationRepository;
    private final CourierRepository courierRepository;

    public CourierGeolocation create(Long courierId, Double lat, Double lng) {
        Courier courier = courierRepository.findById(courierId)
                .orElseThrow(() -> new NotFoundException("Courier not found!"));
        CourierGeolocation courierGeoLocation = CourierGeolocation.builder()
                .courier(courier)
                .lat(lat)
                .lng(lng)
                .travelDistance(calculateTravelDistance(courierId, lat, lng))
                .build();
        CourierGeolocation courierGeolocation = courierGeolocationRepository.save(courierGeoLocation);
        notifyObserver(courierGeolocation);
        log.info("Courier geolocation created successfully. CourierId: {}, CourierGeolocationId: {}", courierId, courierGeolocation.getId());
        return courierGeoLocation;
    }

    public Optional<CourierGeolocation> findLastByCourier(Long courierId) {
        return courierGeolocationRepository.findTopByCourierIdOrderByIdDesc(courierId);
    }

    public double getTotalTravelDistance(Long courierId) {
        return courierGeolocationRepository.findByCourierId(courierId).stream()
                .mapToDouble(CourierGeolocation::getTravelDistance)
                .sum();
    }

    private void notifyObserver(CourierGeolocation courierGeolocation) {
        observers.forEach(observer -> observer.update(courierGeolocation));
    }

    private double calculateTravelDistance(Long courierId, double lat, double lng) {
        return findLastByCourier(courierId).stream()
                .mapToDouble(courierGeolocation -> GeolocationUtil.getInstance().calculateDistance(courierGeolocation.getLat(), courierGeolocation.getLng(), lat, lng))
                .findFirst()
                .orElse(0.0);
    }

}
