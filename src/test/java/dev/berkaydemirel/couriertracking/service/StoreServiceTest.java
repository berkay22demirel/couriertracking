package dev.berkaydemirel.couriertracking.service;

import dev.berkaydemirel.couriertracking.entity.Store;
import dev.berkaydemirel.couriertracking.repository.StoreRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class StoreServiceTest {

    @InjectMocks
    private StoreService storeService;

    @Mock
    private StoreRepository storeRepository;

    @Test
    public void create_shouldCreateStore() {
        //given
        Store store = getDummyStore();
        when(storeRepository.save(any(Store.class))).thenReturn(store);

        //when
        storeService.create("store_name", 40.992877, 29.024910);

        //then
        InOrder inOrder = Mockito.inOrder(storeRepository);
        inOrder.verify(storeRepository).save(any(Store.class));
    }

    @Test
    public void delete_shouldDeleteStore() {
        //given
        Long storeId = 1L;

        //when
        storeService.delete(storeId);

        //then
        InOrder inOrder = Mockito.inOrder(storeRepository);
        inOrder.verify(storeRepository).deleteById(storeId);
    }

    @Test
    public void findById_shouldGetStore() {
        //given
        Store store = getDummyStore();
        when(storeRepository.findById(store.getId())).thenReturn(Optional.of(store));

        //when
        Optional<Store> result = storeService.findById(store.getId());

        //then
        assertThat(result.isPresent()).isTrue();
        assertThat(result.get().getId()).isEqualTo(store.getId());
        assertThat(result.get().getName()).isEqualTo(store.getName());
        assertThat(result.get().getLat()).isEqualTo(store.getLat());
        assertThat(result.get().getLng()).isEqualTo(store.getLng());
    }

    @Test
    public void findById_shouldGetEmptyOptional_WhenStoreIsNull() {
        //given
        Long storeId = 1L;
        when(storeRepository.findById(storeId)).thenReturn(Optional.empty());

        //when
        Optional<Store> result = storeService.findById(storeId);

        //then
        assertThat(result.isPresent()).isFalse();
    }

    @Test
    public void findAll_shouldGetStores() {
        //given
        List<Store> stores = new ArrayList<>();
        stores.add(getDummyStore());
        stores.add(getDummyStore());
        when(storeRepository.findAll()).thenReturn(stores);

        //when
        List<Store> resultList = storeService.findAll();

        //then
        assertThat(resultList.size()).isEqualTo(stores.size());
        assertThat(resultList.get(0)).isEqualTo(stores.get(0));
        assertThat(resultList.get(1)).isEqualTo(stores.get(1));
    }

    private Store getDummyStore() {
        return Store.builder()
                .id(1L)
                .name("store_name")
                .lat(40.992877)
                .lng(29.024910)
                .build();
    }
}
