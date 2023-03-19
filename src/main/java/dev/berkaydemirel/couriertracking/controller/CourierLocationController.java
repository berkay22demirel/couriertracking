package dev.berkaydemirel.couriertracking.controller;

import dev.berkaydemirel.couriertracking.controller.request.CreateCourierLocationRequest;
import dev.berkaydemirel.couriertracking.controller.response.Response;
import dev.berkaydemirel.couriertracking.entity.CourierLocation;
import dev.berkaydemirel.couriertracking.service.CourierLocationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@RequiredArgsConstructor
@RequestMapping("api/v1/couriers/{courierId}/locations")
@RestController
public class CourierLocationController {

    private final CourierLocationService courierLocationService;

    @PostMapping
    public ResponseEntity<Response<CourierLocation>> create(@PathVariable @NotNull Long courierId, @RequestBody @Valid CreateCourierLocationRequest request) {
        CourierLocation courierLocation = courierLocationService.create(courierId, request.getLat(), request.getLng());
        return new ResponseEntity<>(new Response<>("Courier location created successfully.", courierLocation), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Response<CourierLocation>> delete(@PathVariable @NotNull Long courierId, @PathVariable @NotNull Long id) {
        courierLocationService.delete(courierId, id);
        return new ResponseEntity<>(new Response<>("Courier location deleted successfully."), HttpStatus.OK);
    }

    @GetMapping("/last")
    public ResponseEntity<Response<CourierLocation>> getLastLocation(@PathVariable @NotNull Long courierId) {
        return courierLocationService.findLastByCourier(courierId)
                .map(courierLocation -> new ResponseEntity<>(new Response<>(courierLocation), HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(new Response<>("Courier location not found!"), HttpStatus.OK));
    }

    @GetMapping("/total-distance")
    public ResponseEntity<Response<Double>> getTotalDistance(@PathVariable @NotNull Long courierId) {
        double totalTravelDistance = courierLocationService.getTotalTravelDistance(courierId);
        return new ResponseEntity<>(new Response<>(totalTravelDistance), HttpStatus.OK);
    }

}
