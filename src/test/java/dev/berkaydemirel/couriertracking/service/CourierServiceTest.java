package dev.berkaydemirel.couriertracking.service;

import dev.berkaydemirel.couriertracking.entity.Courier;
import dev.berkaydemirel.couriertracking.repository.CourierRepository;
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
public class CourierServiceTest {

    @InjectMocks
    private CourierService courierService;

    @Mock
    private CourierRepository courierRepository;

    @Test
    public void create_shouldCreateCourier() {
        //given
        Courier courier = getDummyCourier();
        when(courierRepository.save(any(Courier.class))).thenReturn(courier);

        //when
        courierService.create("courier_name", "courier_surname");

        //then
        InOrder inOrder = Mockito.inOrder(courierRepository);
        inOrder.verify(courierRepository).save(any(Courier.class));
    }

    @Test
    public void delete_shouldDeleteCourier() {
        //given
        Long courierId = 1L;

        //when
        courierService.delete(courierId);

        //then
        InOrder inOrder = Mockito.inOrder(courierRepository);
        inOrder.verify(courierRepository).deleteById(courierId);
    }

    @Test
    public void findById_shouldGetCourier() {
        //given
        Courier courier = getDummyCourier();
        when(courierRepository.findById(courier.getId())).thenReturn(Optional.of(courier));

        //when
        Optional<Courier> result = courierService.findById(courier.getId());

        //then
        assertThat(result.isPresent()).isTrue();
        assertThat(result.get().getId()).isEqualTo(courier.getId());
        assertThat(result.get().getName()).isEqualTo(courier.getName());
        assertThat(result.get().getSurname()).isEqualTo(courier.getSurname());
    }

    @Test
    public void findById_shouldGetEmptyOptional_WhenCourierIsNull() {
        //given
        Long courierId = 1L;
        when(courierRepository.findById(courierId)).thenReturn(Optional.empty());

        //when
        Optional<Courier> result = courierService.findById(courierId);

        //then
        assertThat(result.isPresent()).isFalse();
    }

    @Test
    public void findAll_shouldGetCouriers() {
        //given
        List<Courier> couriers = new ArrayList<>();
        couriers.add(getDummyCourier());
        couriers.add(getDummyCourier());
        when(courierRepository.findAll()).thenReturn(couriers);

        //when
        List<Courier> resultList = courierService.findAll();

        //then
        assertThat(resultList.size()).isEqualTo(couriers.size());
        assertThat(resultList.get(0)).isEqualTo(couriers.get(0));
        assertThat(resultList.get(1)).isEqualTo(couriers.get(1));
    }

    private Courier getDummyCourier() {
        return Courier.builder()
                .id(1L)
                .name("courier_name")
                .surname("courier_surname")
                .build();
    }
}
