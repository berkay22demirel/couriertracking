package dev.berkaydemirel.couriertracking.service;

import dev.berkaydemirel.couriertracking.entity.Courier;
import dev.berkaydemirel.couriertracking.repository.CourierRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class CourierService {

    private final CourierRepository courierRepository;

    public Courier create(String name, String surname) {
        Courier courier = Courier.builder()
                .name(name)
                .surname(surname)
                .build();
        courier = courierRepository.save(courier);
        log.info("Courier created successfully. CourierId: {}", courier.getId());
        return courier;
    }

    public void delete(Long id) {
        courierRepository.deleteById(id);
        log.info("Courier deleted successfully. CourierId: {}", id);
    }

    public Optional<Courier> findById(Long id) {
        return courierRepository.findById(id);
    }

    public List<Courier> findAll() {
        return courierRepository.findAll();
    }
}