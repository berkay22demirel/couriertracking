package dev.berkaydemirel.couriertracking.service;

import dev.berkaydemirel.couriertracking.entity.Courier;
import dev.berkaydemirel.couriertracking.entity.CourierGeolocation;
import dev.berkaydemirel.couriertracking.exception.NotFoundException;
import dev.berkaydemirel.couriertracking.repository.CourierGeolocationRepository;
import dev.berkaydemirel.couriertracking.repository.CourierRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CourierGeolocationServiceTest {

    private CourierGeolocationService courierGeolocationService;

    @Mock
    private CourierGeolocationRepository courierGeolocationRepository;

    @Mock
    private CourierRepository courierRepository;

    @Mock
    private CourierTrackingService courierTrackingService;

    private final List<CourierGeolocationObserver> observers = new ArrayList<>();

    @Before
    public void setup() {
        observers.add(courierTrackingService);
        courierGeolocationService = new CourierGeolocationService(observers, courierGeolocationRepository, courierRepository);
    }

    @Test
    public void create_shouldCreateCourierGeolocation() {
        //given
        Long courierId = 1L;
        CourierGeolocation courierGeolocation = getDummyCourierGeolocation();
        when(courierRepository.findById(courierId)).thenReturn(Optional.of(courierGeolocation.getCourier()));
        when(courierGeolocationRepository.save(any(CourierGeolocation.class))).thenReturn(courierGeolocation);
        doNothing().when(courierTrackingService).update(courierGeolocation);

        //when
        courierGeolocationService.create(courierId, 40.992877, 29.024910);

        //then
        InOrder inOrder = Mockito.inOrder(courierRepository, courierGeolocationRepository, courierTrackingService);
        inOrder.verify(courierRepository).findById(courierId);
        inOrder.verify(courierGeolocationRepository).save(any(CourierGeolocation.class));
        inOrder.verify(courierTrackingService).update(courierGeolocation);
    }

    @Test
    public void create_shouldThrowNotFoundException_whenCourierIsNull() {
        //given
        Long courierId = 1L;
        when(courierRepository.findById(courierId)).thenReturn(Optional.empty());

        //when
        Throwable throwable = catchThrowable(() -> courierGeolocationService.create(courierId, 40.992877, 29.024910));

        //then
        assertThat(throwable).isInstanceOf(NotFoundException.class);
        assertThat(throwable.getMessage()).isEqualTo("Courier not found!");
    }

    @Test
    public void findLastByCourier_shouldGetLastCourierGeolocation() {
        //given
        Long courierId = 1L;
        CourierGeolocation courierGeolocation = getDummyCourierGeolocation();
        when(courierGeolocationRepository.findTopByCourierIdOrderByIdDesc(courierId)).thenReturn(Optional.of(courierGeolocation));

        //when
        Optional<CourierGeolocation> result = courierGeolocationService.findLastByCourier(courierId);

        //then
        assertThat(result.isPresent()).isTrue();
        assertThat(result.get().getId()).isEqualTo(courierGeolocation.getId());
        assertThat(result.get().getCourier()).isEqualTo(courierGeolocation.getCourier());
        assertThat(result.get().getLat()).isEqualTo(courierGeolocation.getLat());
        assertThat(result.get().getLng()).isEqualTo(courierGeolocation.getLng());
        assertThat(result.get().getTravelDistance()).isEqualTo(courierGeolocation.getTravelDistance());
    }

    @Test
    public void findById_shouldGetEmptyOptional_WhenCourierGeolocationIsNull() {
        //given
        Long courierId = 1L;
        when(courierGeolocationRepository.findTopByCourierIdOrderByIdDesc(courierId)).thenReturn(Optional.empty());

        //when
        Optional<CourierGeolocation> result = courierGeolocationService.findLastByCourier(courierId);

        //then
        assertThat(result.isPresent()).isFalse();
    }

    @Test
    public void getTotalTravelDistance_shouldGetCourierTotalTravelDistance() {
        //given
        Long courierId = 1L;
        List<CourierGeolocation> courierGeolocations = new ArrayList<>();
        courierGeolocations.add(getDummyCourierGeolocation());
        courierGeolocations.get(0).setTravelDistance(1.0);
        courierGeolocations.add(getDummyCourierGeolocation());
        courierGeolocations.get(1).setTravelDistance(3.5);
        when(courierGeolocationRepository.findByCourierId(courierId)).thenReturn(courierGeolocations);

        //when
        double result = courierGeolocationService.getTotalTravelDistance(courierId);

        //then
        assertThat(result).isEqualTo(4.5);
    }

    @Test
    public void getZeroTotalTravelDistance_shouldNotFoundCourierTotalTravelDistance() {
        //given
        Long courierId = 1L;
        List<CourierGeolocation> courierGeolocations = new ArrayList<>();
        when(courierGeolocationRepository.findByCourierId(courierId)).thenReturn(courierGeolocations);

        //when
        double result = courierGeolocationService.getTotalTravelDistance(courierId);

        //then
        assertThat(result).isEqualTo(0.0);
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
}
