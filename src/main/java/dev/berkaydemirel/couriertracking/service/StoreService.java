package dev.berkaydemirel.couriertracking.service;

import dev.berkaydemirel.couriertracking.entity.Store;
import dev.berkaydemirel.couriertracking.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class StoreService {

    private final StoreRepository storeRepository;

    @CacheEvict(value = "stores", allEntries = true)
    public Store create(String name, Double lat, Double lng) {
        Store store = Store.builder()
                .name(name)
                .lat(lat)
                .lng(lng)
                .build();
        store = storeRepository.save(store);
        log.info("Store created successfully. StoreId: {}", store.getId());
        return store;
    }

    @CacheEvict(value = "stores", allEntries = true)
    public void delete(Long id) {
        storeRepository.deleteById(id);
        log.info("Store deleted successfully. StoreId: {}", id);
    }

    @Cacheable(value = "stores", key = "#id")
    public Optional<Store> findById(Long id) {
        return storeRepository.findById(id);
    }

    @Cacheable("stores")
    public List<Store> findAll() {
        return storeRepository.findAll();
    }
}
