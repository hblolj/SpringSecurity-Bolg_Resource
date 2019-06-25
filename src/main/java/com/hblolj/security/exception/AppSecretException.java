package com.hblolj.security.exception;

/**
 * @author: hblolj
 * @Date: 2019/6/25 17:08
 * @Description:
 * @Version:
 **/
public class AppSecretException extends RuntimeException{

    public AppSecretException() {
    }

    public AppSecretException(String message) {
        super(message);
    }

    public AppSecretException(String message, Throwable cause) {
        super(message, cause);
    }
}
