package dev.berkaydemirel.couriertracking.controller.request;

import com.sun.istack.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
public class CreateCourierRequest {

    @NotNull
    @Length(min = 3, max = 64)
    private String name;

    @NotNull
    @Length(min = 3, max = 64)
    private String surname;
}
