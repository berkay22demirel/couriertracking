package dev.berkaydemirel.couriertracking.service;

import dev.berkaydemirel.couriertracking.entity.Store;
import dev.berkaydemirel.couriertracking.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class StoreService {

    private final StoreRepository storeRepository;

    @CacheEvict("stores")
    public Store save(String name, double lat, double lng) {
        Store store = Store.builder()
                .name(name)
                .lat(lat)
                .lng(lng)
                .build();
        return storeRepository.save(store);
    }

    @CacheEvict("stores")
    public void delete(Long id) {
        storeRepository.deleteById(id);
    }

    @Cacheable(value = "stores", key = "#id")
    public Optional<Store> find(Long id) {
        return storeRepository.findById(id);
    }

    @Cacheable("stores")
    public List<Store> findAll() {
        return storeRepository.findAll();
    }
}
