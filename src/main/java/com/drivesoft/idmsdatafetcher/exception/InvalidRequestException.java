package com.drivesoft.idmsdatafetcher.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.http.HttpStatus;
@Data
@EqualsAndHashCode(callSuper = false)
public class InvalidRequestException extends RuntimeException {

    private static final long serialVersionUID = -4265643737002567524L;

    private HttpStatus httpStatus ;

    public InvalidRequestException(String message) {
        super(message);

    }
    public InvalidRequestException(String message, Throwable throwable) {
        super(message,throwable);

    }

    public InvalidRequestException(String message,HttpStatus httpStatus) {
        super(message);
        this.httpStatus = httpStatus;
    }

}
