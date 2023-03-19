package dev.berkaydemirel.couriertracking.service;

import dev.berkaydemirel.couriertracking.entity.Courier;
import dev.berkaydemirel.couriertracking.repository.CourierRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class CourierService {

    private final CourierRepository courierRepository;

    public Courier create(String name, String surname) {
        Courier courier = Courier.builder()
                .name(name)
                .surname(surname)
                .build();
        return courierRepository.save(courier);
    }

    public void delete(Long id) {
        courierRepository.deleteById(id);
    }

    public Optional<Courier> findById(Long id) {
        return courierRepository.findById(id);
    }

    public List<Courier> findAll() {
        return courierRepository.findAll();
    }
}