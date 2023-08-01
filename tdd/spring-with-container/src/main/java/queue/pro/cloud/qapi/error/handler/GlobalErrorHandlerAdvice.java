package queue.pro.cloud.qapi.error.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.support.WebExchangeBindException;
import queue.pro.cloud.qapi.error.ErrorDto;
import queue.pro.cloud.qapi.error.NotFoundException;

import java.util.stream.Collectors;

@ControllerAdvice
@Slf4j
public class GlobalErrorHandlerAdvice {
    @ExceptionHandler(WebExchangeBindException.class)
    public ResponseEntity<ErrorDto> handleRequestBodyError(WebExchangeBindException ex){
        log.error("Exception Caught in handleRequestError: {}",ex.getMessage(), ex);
        final String error = ex.getBindingResult().getAllErrors().stream()
                .map(objectError -> objectError.getDefaultMessage() == null ? "": objectError.getDefaultMessage())
                .sorted()
                .collect(Collectors.joining(","));
        ErrorDto errorDto = new ErrorDto(HttpStatus.BAD_REQUEST.value(), error.split(","));
        log.error("Errors: {}",error);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorDto);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<NotFoundException.Reason> handleNotFoundError(NotFoundException ex){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getReason());
    }
}
