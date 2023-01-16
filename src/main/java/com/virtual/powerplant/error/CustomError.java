package com.virtual.powerplant.error;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class CustomError {

    private String message;

    private String path;
}
