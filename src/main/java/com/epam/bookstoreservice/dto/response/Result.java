package com.epam.bookstoreservice.dto.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Result<T> {

    @ApiModelProperty(notes = "status code returned")
    private Integer code;

    @ApiModelProperty(notes = "status information returned")
    private String message;

    @ApiModelProperty(notes = "data returned")
    private T data;

    private Result(Integer code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    /**
     * The content that is returned without data on success
     *
     * @param message
     * @return
     */
    public static <T> Result<T> success(String message) {
        return new Result<>(200, message, null);
    }

    /**
     * The content that is returned with data on success
     *
     * @param message,obj
     * @return
     */
    public static <T> Result<T> success(String message, T data) {
        return new Result<>(200, message, data);
    }

    /**
     * The content that is returned without data on failure
     *
     * @param message
     * @return
     */
    public static <T> Result<T> error(String message) {
        return new Result<>(500, message, null);
    }

    /**
     * The content that is returned with data on failure
     *
     * @param message,obj
     * @return
     */
    public static <T> Result<T> error(String message, T data) {
        return new Result<>(500, message, data);
    }

    public static  <T> Result<T> error(Integer errorCode, String message, T data) {
        return new Result<>(errorCode, message, data);
    }

    public static  <T> Result<T> error(Integer errorCode, String message) {
        return new Result<>(errorCode, message, null);
    }

}
