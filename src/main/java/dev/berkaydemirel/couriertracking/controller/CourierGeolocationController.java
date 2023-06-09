package dev.berkaydemirel.couriertracking.controller;

import dev.berkaydemirel.couriertracking.controller.request.CreateCourierGeolocationRequest;
import dev.berkaydemirel.couriertracking.controller.response.Response;
import dev.berkaydemirel.couriertracking.entity.CourierGeolocation;
import dev.berkaydemirel.couriertracking.service.CourierGeolocationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@RequiredArgsConstructor
@RequestMapping("api/v1/couriers/{courierId}/geolocations")
@RestController
public class CourierGeolocationController {

    private final CourierGeolocationService courierGeolocationService;

    @PostMapping
    public ResponseEntity<Response<CourierGeolocation>> create(@PathVariable @NotNull Long courierId, @RequestBody @Valid CreateCourierGeolocationRequest request) {
        CourierGeolocation courierGeoLocation = courierGeolocationService.create(courierId, request.getLat(), request.getLng());
        return new ResponseEntity<>(new Response<>("Courier geolocation created successfully.", courierGeoLocation), HttpStatus.OK);
    }

    @GetMapping("/last")
    public ResponseEntity<Response<CourierGeolocation>> getLastGeolocation(@PathVariable @NotNull Long courierId) {
        return courierGeolocationService.findLastByCourier(courierId)
                .map(courierGeolocation -> new ResponseEntity<>(new Response<>(courierGeolocation), HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(new Response<>("Courier geolocation not found!"), HttpStatus.OK));
    }

    @GetMapping("/total-distance")
    public ResponseEntity<Response<Double>> getTotalDistance(@PathVariable @NotNull Long courierId) {
        double totalTravelDistance = courierGeolocationService.getTotalTravelDistance(courierId);
        return new ResponseEntity<>(new Response<>(totalTravelDistance), HttpStatus.OK);
    }

}
