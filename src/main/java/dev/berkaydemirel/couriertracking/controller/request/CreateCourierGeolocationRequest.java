package dev.berkaydemirel.couriertracking.controller.request;

import com.sun.istack.NotNull;
import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@Data
public class CreateCourierGeolocationRequest {

    @NotNull
    @Min(value = 0)
    @Max(value = Long.MAX_VALUE)
    private Double lat;

    @NotNull
    @Min(value = 0)
    @Max(value = Long.MAX_VALUE)
    private Double lng;
}
