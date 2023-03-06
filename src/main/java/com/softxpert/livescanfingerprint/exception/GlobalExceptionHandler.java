package com.softxpert.livescanfingerprint.exception;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.softxpert.livescanfingerprint.model.RestErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.SQLException;
import java.time.Instant;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @Autowired
    public ObjectMapper mapper;

//    @ExceptionHandler(MyException.class)
    public void handleMyException(HttpServletRequest request,
                                  HttpServletResponse response,
                                  Exception exception) {

        String trackId = UUID.randomUUID().toString();
        log.error("{} - {}", trackId, exception.getMessage(), exception);

        RestErrorResponse restError = RestErrorResponse.builder()
                .timestamp(Instant.now().toString())
                .traceId(trackId)
                .status(HttpStatus.BAD_REQUEST.toString())
//                .status(HttpStatus.CONFLICT.toString())
                .error(new AppError(FingerprintErrorCode.APPLICATION_ERROR_CODE.ordinal(), exception.getMessage()))
                .path(request.getServletPath())
                .build();

        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);

        try {
            OutputStream responseStream = response.getOutputStream();

//            mapper.writeValue(responseStream, restError);
            responseStream.flush();
        } catch (Exception ex) {
            log.error("Error: ", ex);
        }

    }

    @ExceptionHandler(UnsupportedOperationException.class)
    public ResponseEntity<Object> handleUnsupportedOperationException(HttpServletRequest request,
                                                     Exception exception) {

        String trackId = UUID.randomUUID().toString();
        log.error("{} - {}", trackId, exception.getMessage(), exception);

        RestErrorResponse restErrorResponse = RestErrorResponse.builder()
                .timestamp(Instant.now().toString())
                .traceId(trackId)
                .status(HttpStatus.BAD_REQUEST.toString())
//                .status(HttpStatus.CONFLICT.toString())
                .error(new AppError(FingerprintErrorCode.APPLICATION_ERROR_CODE.ordinal(), exception.getMessage()))
                .path(request.getServletPath())
                .build();

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(restErrorResponse);

    }


    @ExceptionHandler(SQLException.class)
    public String handleSQLException(HttpServletRequest request, Exception ex) {
        log.info("SQLException occurred:: URL=" + request.getRequestURL());
        return "database_error";
    }

    @ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "IOException occurred")
    @ExceptionHandler(IOException.class)
    public void handleIOException() {
        log.error("IOException handler executed ************************");
        //returning 404 error code
    }




    @ExceptionHandler(value = {IllegalArgumentException.class, IllegalStateException.class})
    protected ResponseEntity<Object> handleConflict(RuntimeException ex, WebRequest request) {

        String bodyOfResponse = "This should be application specific";
        log.error("handleConflict handler executed ************************");
//        return handleExceptionInternal(ex, bodyOfResponse, new HttpHeaders(), HttpStatus.CONFLICT, request);
        return null;
    }


    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<RestErrorResponse> handleHttpMessageNotReadableException(HttpMessageNotReadableException ex, HttpServletRequest request) {

        String trackId = UUID.randomUUID().toString();
        log.error("{}", trackId, ex);


        RestErrorResponse restError = RestErrorResponse.builder()
                .timestamp(Instant.now().toString())
                .traceId(trackId)
                .error(new AppError(FingerprintErrorCode.APPLICATION_ERROR_CODE.ordinal(), "Missing request body."))
                .status(HttpStatus.BAD_REQUEST.toString())
                .path(request.getServletPath())
                .build();


        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(restError);
    }
    @ExceptionHandler(value = {BindException.class, MethodArgumentNotValidException.class})
    protected ResponseEntity<RestErrorResponse> handleDataBindingException(BindException ex, HttpServletRequest request) {

        String trackId = UUID.randomUUID().toString();
        log.error("{}", trackId, ex);

        // fields errors
        List<FieldErrorInfo> validationErrors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(fieldError -> new FieldErrorInfo(fieldError.getField(), fieldError.getDefaultMessage()))
                .collect(Collectors.toList());

        // global errors
        List<FieldErrorInfo> globalErrors = ex.getBindingResult()
                .getGlobalErrors()
                .stream()
                .map(objectError -> new FieldErrorInfo(objectError.getObjectName(), objectError.getDefaultMessage()))
                .collect(Collectors.toList());

        // joining 2 kind of errors
        List<FieldErrorInfo> allErrors = Stream.concat(validationErrors.stream(), globalErrors.stream())
                .collect(Collectors.toList());

        RestErrorResponse restError = RestErrorResponse.builder()
                .timestamp(Instant.now().toString())
                .traceId(trackId)
                .error(new AppError(FingerprintErrorCode.VALIDATION_ERROR_CODE.ordinal(), "Fields Validation"))
                .status(HttpStatus.BAD_REQUEST.toString())
//                .validation(validationErrors)
                .validation(allErrors)
                .path(request.getServletPath())
                .build();

        System.out.println("--------------------------------------------------- " + request.getServletPath());
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(restError);
    }


    private ResponseEntity<Object> handleExceptionInternal(RuntimeException ex,
                                                           String bodyOfResponse,
                                                           HttpHeaders httpHeaders,
                                                           HttpStatus conflict,
                                                           WebRequest request) {
        // ...
        return ResponseEntity.badRequest()
                .body(bodyOfResponse);
    }


}
