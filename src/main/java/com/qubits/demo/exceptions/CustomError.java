package com.qubits.demo.exceptions;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomError {
    private String message;
    private int status;
    private Long timestamp;
}
