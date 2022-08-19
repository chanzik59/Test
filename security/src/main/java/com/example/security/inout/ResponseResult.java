package com.example.security.inout;

import lombok.extern.jackson.Jacksonized;

/**
 * @author chenzhiqin
 * @date 2022/8/19 16:19
 * @info XX
 */
@Jacksonized
public class ResponseResult<T> {

    private Integer code;
    private String message;
    private T data;

}
