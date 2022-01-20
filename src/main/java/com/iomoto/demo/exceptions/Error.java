package com.iomoto.demo.exceptions;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Error {
    private String message;
    private int status;
    private Long timestamp;
}
