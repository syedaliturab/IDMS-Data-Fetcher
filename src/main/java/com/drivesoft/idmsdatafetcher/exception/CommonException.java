package com.drivesoft.idmsdatafetcher.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.http.HttpStatus;

@Data
@EqualsAndHashCode(callSuper = false)
public class CommonException extends RuntimeException {

    private static final long serialVersionUID = -4265643737002567524L;
    private HttpStatus httpStatus;
    private String message;

    public CommonException(String message) {
        super(message);
        this.message = message;

    }
    public CommonException(String message, Throwable throwable) {
        super(message, throwable);
        this.message = message;

    }

    public CommonException(String message, HttpStatus httpStatus) {
        super(message);
        this.message = message;
        this.httpStatus = httpStatus;
    }

}
