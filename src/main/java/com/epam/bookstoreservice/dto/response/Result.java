package com.epam.bookstoreservice.dto.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Result<T> {

    @ApiModelProperty(notes = "status information returned")
    private String message;

    @ApiModelProperty(notes = "data returned")
    private T data;

    private Result(String message, T data) {
        this.message = message;
        this.data = data;
    }

    private Result(String message) {
        this.message = message;
        this.data = null;
    }
    /**
     * The content that is returned without data on success
     *
     * @param message
     * @return
     */
    public static <T> Result<T> success(String message) {
        return new Result<>(message);
    }

    /**
     * The content that is returned with data on success
     *
     * @param message,obj
     * @return
     */
    public static <T> Result<T> success(String message, T data) {
        return new Result<>(message, data);
    }

    /**
     * The content that is returned without data on failure
     *
     * @param message
     * @return
     */
    public static <T> Result<T> error(String message) {
        return new Result<>(message);
    }

    /**
     * The content that is returned with data on failure
     *
     * @param message,obj
     * @return
     */
    public static <T> Result<T> error(String message, T data) {
        return new Result<>(message, data);
    }


}
