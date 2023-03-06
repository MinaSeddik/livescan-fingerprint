package com.softxpert.livescanfingerprint.model;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.softxpert.livescanfingerprint.exception.AppError;
import com.softxpert.livescanfingerprint.exception.FieldErrorInfo;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RestErrorResponse {

    private String timestamp;

    private String status;

    private AppError error;

    private String message;

    private String path;

    private String traceId;

    private List<FieldErrorInfo> validation;

}
