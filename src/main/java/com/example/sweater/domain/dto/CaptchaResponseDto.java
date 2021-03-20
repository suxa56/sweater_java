package com.example.sweater.domain.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Value;

import java.util.Set;

@JsonIgnoreProperties(ignoreUnknown = true)
@Value
public class CaptchaResponseDto {
    private boolean success;
    @JsonAlias("error-codes")
    private Set<String> errorCodes;
}
