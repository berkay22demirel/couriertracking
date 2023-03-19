package dev.berkaydemirel.couriertracking.controller.request;

import com.sun.istack.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@Data
public class CreateStoreRequest {

    @NotNull
    @Length(min = 3, max = 64)
    private String name;

    @NotNull
    @Min(value = 0)
    @Max(value = Long.MAX_VALUE)
    private Double lat;

    @NotNull
    @Min(value = 0)
    @Max(value = Long.MAX_VALUE)
    private Double lng;

}
