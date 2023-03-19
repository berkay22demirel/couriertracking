package dev.berkaydemirel.couriertracking.controller.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.Date;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class Response<T> {

    private String message;
    private Map<String, String> validationErrors;
    private T data;
    private long systemTime = new Date().getTime();

    public Response(String message) {
        this.message = message;
    }

    public Response(String message, Map<String, String> validationErrors) {
        this.message = message;
        this.validationErrors = validationErrors;
    }

    public Response(String message, T data) {
        this.message = message;
        this.data = data;
    }

    public Response(T data) {
        this.data = data;
    }

    public Response() {
    }
}
