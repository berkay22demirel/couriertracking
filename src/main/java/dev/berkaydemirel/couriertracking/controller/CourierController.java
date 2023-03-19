package dev.berkaydemirel.couriertracking.controller;

import dev.berkaydemirel.couriertracking.controller.request.CreateCourierRequest;
import dev.berkaydemirel.couriertracking.controller.response.Response;
import dev.berkaydemirel.couriertracking.entity.Courier;
import dev.berkaydemirel.couriertracking.service.CourierService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@RequiredArgsConstructor
@RequestMapping("api/v1/couriers")
@RestController
public class CourierController {

    private final CourierService courierService;

    @PostMapping
    public ResponseEntity<Response<Courier>> create(@RequestBody @Valid CreateCourierRequest request) {
        Courier courier = courierService.create(request.getName(), request.getSurname());
        return new ResponseEntity<>(new Response<>("Courier created successfully.", courier), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Response<Courier>> delete(@PathVariable @NotNull Long id) {
        courierService.delete(id);
        return new ResponseEntity<>(new Response<>("Courier deleted successfully."), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Response<Courier>> getById(@PathVariable @NotNull Long id) {
        return courierService.findById(id)
                .map(courier -> new ResponseEntity<>(new Response<>(courier), HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(new Response<>("Courier not found!"), HttpStatus.OK));
    }

    @GetMapping("/all")
    public ResponseEntity<Response<List<Courier>>> getAll() {
        List<Courier> couriers = courierService.findAll();
        return new ResponseEntity<>(new Response<>(couriers), HttpStatus.OK);
    }
}
