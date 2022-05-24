package com.example.demo.core.exception;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {
    private static final HttpStatus EXC_HTTP_STATUS=HttpStatus.INTERNAL_SERVER_ERROR;

    @ExceptionHandler(Exception.class)
    protected ResponseEntity<Object> exceptionHandlerxx(Exception ex) {
        return new ResponseEntity<>(ApiError.builder().message(ex.getMessage()).status(EXC_HTTP_STATUS).build(), EXC_HTTP_STATUS);
    }
}

@Getter
@Setter
@Builder
class ApiError {
    private HttpStatus status;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    private LocalDateTime timestamp;
    private String message;
    private String debugMessage;

}