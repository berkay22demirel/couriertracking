package dev.berkaydemirel.couriertracking.service;

import dev.berkaydemirel.couriertracking.entity.CourierGeolocation;
import dev.berkaydemirel.couriertracking.entity.CourierTracking;
import dev.berkaydemirel.couriertracking.entity.Store;
import dev.berkaydemirel.couriertracking.repository.CourierTrackingRepository;
import dev.berkaydemirel.couriertracking.util.DateUtil;
import dev.berkaydemirel.couriertracking.util.GeolocationUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Slf4j
@Service
public class CourierTrackingService implements CourierGeolocationObserver {

    private final CourierTrackingRepository courierTrackingRepository;
    private final StoreService storeService;

    @Value("${dev.berkaydemirel.couriertracking.ignored-minute-of-courier-tracking}")
    private Integer ignoredMinuteOfCourierTracking;

    @Value("${dev.berkaydemirel.couriertracking.courier-tracking-distance}")
    private Double courierTrackingDistance;

    public CourierTrackingService(CourierTrackingRepository courierTrackingRepository, StoreService storeService, CourierGeolocationService courierGeolocationService) {
        this.courierTrackingRepository = courierTrackingRepository;
        this.storeService = storeService;
        courierGeolocationService.attachObserver(this);
    }

    @Override
    public void update(CourierGeolocation courierGeolocation) {
        Date trackingDate = new Date();
        storeService.findAll()
                .stream()
                .filter(store -> GeolocationUtil.getInstance().calculateDistance(courierGeolocation.getLat(), courierGeolocation.getLng(), store.getLat(), store.getLng()) <= courierTrackingDistance)
                .forEach(store -> create(store, courierGeolocation, trackingDate));
    }

    private void create(Store store, CourierGeolocation courierGeolocation, Date trackingDate) {
        Optional<CourierTracking> courierTrackingOptional = courierTrackingRepository.findTopByStoreIdAndCourierIdOrderByIdDesc(store.getId(), courierGeolocation.getCourier().getId());
        if (courierTrackingOptional.isPresent()) {
            CourierTracking courierTracking = courierTrackingOptional.get();
            if (ignoredMinuteOfCourierTracking >= DateUtil.getInstance().getDiffMinutes(courierTracking.getTrackingDate(), trackingDate)) {
                return;
            }
        }
        CourierTracking courierTracking = CourierTracking.builder()
                .courierId(courierGeolocation.getCourier().getId())
                .storeId(store.getId())
                .trackingDate(trackingDate)
                .build();
        courierTrackingRepository.save(courierTracking);
    }
}
