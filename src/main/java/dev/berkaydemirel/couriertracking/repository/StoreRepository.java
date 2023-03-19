package dev.berkaydemirel.couriertracking.repository;

import dev.berkaydemirel.couriertracking.entity.Store;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StoreRepository extends JpaRepository<Store, Long> {
}
