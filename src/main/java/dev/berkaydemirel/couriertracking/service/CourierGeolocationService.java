package dev.berkaydemirel.couriertracking.service;

import dev.berkaydemirel.couriertracking.entity.Courier;
import dev.berkaydemirel.couriertracking.entity.CourierGeolocation;
import dev.berkaydemirel.couriertracking.exception.NotFoundException;
import dev.berkaydemirel.couriertracking.repository.CourierGeolocationRepository;
import dev.berkaydemirel.couriertracking.repository.CourierRepository;
import dev.berkaydemirel.couriertracking.util.GeolocationUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@RequiredArgsConstructor
@Service
public class CourierGeolocationService {

    private final Set<CourierGeolocationObserver> observers = new HashSet<>();
    private final CourierGeolocationRepository courierGeolocationRepository;
    private final CourierRepository courierRepository;

    public CourierGeolocation create(Long courierId, double lat, double lng) {
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
        return courierGeoLocation;
    }

    public void delete(Long courierId, Long id) {
        courierGeolocationRepository.deleteByIdAndCourier(id, courierRepository.getReferenceById(courierId));
    }

    public Optional<CourierGeolocation> findLastByCourier(Long courierId) {
        return courierGeolocationRepository.findTopByCourierIdOrderByIdDesc(courierId);
    }

    public double getTotalTravelDistance(Long courierId) {
        return courierGeolocationRepository.findByCourierId(courierId).stream()
                .mapToDouble(CourierGeolocation::getTravelDistance)
                .sum();
    }

    public void attachObserver(CourierGeolocationObserver courierGeolocationObserver) {
        observers.add(courierGeolocationObserver);
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
