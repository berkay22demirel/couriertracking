package dev.berkaydemirel.couriertracking.service;

import dev.berkaydemirel.couriertracking.entity.Courier;
import dev.berkaydemirel.couriertracking.entity.CourierGeolocation;
import dev.berkaydemirel.couriertracking.entity.CourierTracking;
import dev.berkaydemirel.couriertracking.entity.Store;
import dev.berkaydemirel.couriertracking.repository.CourierTrackingRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Collections;
import java.util.Date;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CourierTrackingServiceTest {

    @InjectMocks
    private CourierTrackingService courierTrackingService;

    @Mock
    private CourierTrackingRepository courierTrackingRepository;

    @Mock
    private StoreService storeService;

    @Before
    public void setup() {
        ReflectionTestUtils.setField(courierTrackingService, "ignoredSecondOfCourierTracking", 60);
        ReflectionTestUtils.setField(courierTrackingService, "courierTrackingDistance", 0.1);
    }

    @Test
    public void update_shouldSaveCourierTracking_whenCourierAroundStores() {
        //given
        CourierGeolocation courierGeolocation = getDummyCourierGeolocation();
        Store store = getDummyStore();
        when(storeService.findAll()).thenReturn(Collections.singletonList(store));
        when(courierTrackingRepository.findTopByStoreIdAndCourierIdOrderByIdDesc(store.getId(), courierGeolocation.getCourier().getId())).thenReturn(Optional.empty());

        //when
        courierTrackingService.update(courierGeolocation);

        //then
        InOrder inOrder = Mockito.inOrder(storeService, courierTrackingRepository, courierTrackingRepository);
        inOrder.verify(storeService).findAll();
        inOrder.verify(courierTrackingRepository).findTopByStoreIdAndCourierIdOrderByIdDesc(store.getId(), courierGeolocation.getCourier().getId());
        inOrder.verify(courierTrackingRepository).save(any(CourierTracking.class));
    }

    @Test
    public void update_shouldNotSaveCourierTracking_whenCourierAroundStoresAndHaveRecordInLastMinutes() {
        //given
        CourierGeolocation courierGeolocation = getDummyCourierGeolocation();
        Store store = getDummyStore();
        CourierTracking courierTracking = CourierTracking.builder()
                .storeId(store.getId())
                .courierId(courierGeolocation.getCourier().getId())
                .trackingDate(new Date(System.currentTimeMillis() - 1000 * 60))
                .build();
        when(storeService.findAll()).thenReturn(Collections.singletonList(store));
        when(courierTrackingRepository.findTopByStoreIdAndCourierIdOrderByIdDesc(store.getId(), courierGeolocation.getCourier().getId())).thenReturn(Optional.of(courierTracking));

        //when
        courierTrackingService.update(courierGeolocation);

        //then
        InOrder inOrder = Mockito.inOrder(storeService, courierTrackingRepository, courierTrackingRepository);
        inOrder.verify(storeService).findAll();
        inOrder.verify(courierTrackingRepository).findTopByStoreIdAndCourierIdOrderByIdDesc(store.getId(), courierGeolocation.getCourier().getId());
        inOrder.verify(courierTrackingRepository, times(0)).save(any(CourierTracking.class));
    }

    @Test
    public void update_shouldSaveCourierTracking_whenCourierAroundStoresAndNoRecordInLastMinutes() {
        //given
        CourierGeolocation courierGeolocation = getDummyCourierGeolocation();
        Store store = getDummyStore();
        CourierTracking courierTracking = CourierTracking.builder()
                .storeId(store.getId())
                .courierId(courierGeolocation.getCourier().getId())
                .trackingDate(new Date(System.currentTimeMillis() - 1000 * 61))
                .build();
        when(storeService.findAll()).thenReturn(Collections.singletonList(store));
        when(courierTrackingRepository.findTopByStoreIdAndCourierIdOrderByIdDesc(store.getId(), courierGeolocation.getCourier().getId())).thenReturn(Optional.of(courierTracking));

        //when
        courierTrackingService.update(courierGeolocation);

        //then
        InOrder inOrder = Mockito.inOrder(storeService, courierTrackingRepository, courierTrackingRepository);
        inOrder.verify(storeService).findAll();
        inOrder.verify(courierTrackingRepository).findTopByStoreIdAndCourierIdOrderByIdDesc(store.getId(), courierGeolocation.getCourier().getId());
        inOrder.verify(courierTrackingRepository).save(any(CourierTracking.class));
    }

    private CourierGeolocation getDummyCourierGeolocation() {
        return CourierGeolocation.builder()
                .id(10L)
                .courier(getDummyCourier())
                .lat(40.992877)
                .lng(29.024910)
                .travelDistance(0.0)
                .build();
    }

    private Courier getDummyCourier() {
        return Courier.builder()
                .id(1L)
                .name("courier_name")
                .surname("courier_surname")
                .build();
    }

    private Store getDummyStore() {
        return Store.builder()
                .id(1L)
                .name("store_name")
                .lat(40.992777)
                .lng(29.024910)
                .build();
    }
}
