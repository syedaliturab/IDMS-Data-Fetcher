package com.drivesoft.idmsdatafetcher.dto.response;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Map;

@Data
@Builder
public class ErrorResponse {
    String message;
    String error;
    Integer status;
    private LocalDateTime timestamp;
    private Map<String, String> details;
}
