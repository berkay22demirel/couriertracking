package dev.berkaydemirel.couriertracking.controller.request;

import com.sun.istack.NotNull;
import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@Data
public class CreateCourierLocationRequest {

    @NotNull
    @Min(value = 0)
    @Max(value = Long.MAX_VALUE)
    private Long lat;

    @NotNull
    @Min(value = 0)
    @Max(value = Long.MAX_VALUE)
    private Long lng;
}
