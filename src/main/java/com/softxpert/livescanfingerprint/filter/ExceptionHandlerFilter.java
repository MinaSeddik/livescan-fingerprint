package com.softxpert.livescanfingerprint.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.softxpert.livescanfingerprint.exception.AppError;
import com.softxpert.livescanfingerprint.exception.FingerprintErrorCode;
import com.softxpert.livescanfingerprint.model.RestErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.Instant;

@Component
@Slf4j
public class ExceptionHandlerFilter extends OncePerRequestFilter {

    @Autowired
    public ObjectMapper mapper;

    @Override
    public void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        RestErrorResponse errorResponse;
        try {
            filterChain.doFilter(request, response);
        } catch (ResponseStatusException ex) {

            log.error("{}: {} ", ex.getClass().getName(), ex.getMessage(), ex);
            response.setStatus(ex.getStatus().value());
            errorResponse = RestErrorResponse.builder()
                    .timestamp(Instant.now().toString())
                    .status(ex.getStatus().toString())
                    .error(new AppError(FingerprintErrorCode.APPLICATION_ERROR_CODE.ordinal(), ex.getMessage()))
                    .path(request.getServletPath())
                    .build();

            response.getWriter()
                    .write(mapper.writeValueAsString(errorResponse));

        } catch (RuntimeException ex) {
            log.error("{}: {} ", ex.getClass().getName(), ex.getMessage(), ex);
            throw ex;
        }

    }

}