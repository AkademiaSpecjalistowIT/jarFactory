package pl.akademiaspecjalistowit.jarfactory.controller;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ErrorResponse {
    private String reason;
    private ErrorCode code;
}
