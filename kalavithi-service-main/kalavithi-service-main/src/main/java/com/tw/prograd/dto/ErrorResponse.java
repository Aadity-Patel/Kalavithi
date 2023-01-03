package com.tw.prograd.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ErrorResponse {
    private int errorCode;
    private String message;
}
