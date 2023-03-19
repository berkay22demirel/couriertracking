package dev.berkaydemirel.couriertracking.controller;

import dev.berkaydemirel.couriertracking.controller.request.CreateStoreRequest;
import dev.berkaydemirel.couriertracking.controller.response.Response;
import dev.berkaydemirel.couriertracking.entity.Store;
import dev.berkaydemirel.couriertracking.service.StoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@RequiredArgsConstructor
@RequestMapping("api/v1/stores")
@RestController
public class StoreController {

    private final StoreService storeService;

    @PostMapping
    public ResponseEntity<Response<Store>> create(@RequestBody @Valid CreateStoreRequest request) {
        Store store = storeService.create(request.getName(), request.getLat(), request.getLng());
        return new ResponseEntity<>(new Response<>("Store created successfully.", store), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Response<Store>> delete(@PathVariable @NotNull Long id) {
        storeService.delete(id);
        return new ResponseEntity<>(new Response<>("Store deleted successfully."), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Response<Store>> getById(@PathVariable @NotNull Long id) {
        return storeService.findById(id)
                .map(store -> new ResponseEntity<>(new Response<>(store), HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(new Response<>("Store not found!"), HttpStatus.OK));
    }

    @GetMapping("/all")
    public ResponseEntity<Response<List<Store>>> getAll() {
        List<Store> stores = storeService.findAll();
        return new ResponseEntity<>(new Response<>(stores), HttpStatus.OK);
    }

}
