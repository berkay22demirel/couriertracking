package dev.berkaydemirel.couriertracking.service;

import dev.berkaydemirel.couriertracking.entity.CourierGeolocation;
import org.springframework.scheduling.annotation.Async;

public interface CourierGeolocationObserver {

    @Async
    void update(CourierGeolocation courierGeolocation);
}
