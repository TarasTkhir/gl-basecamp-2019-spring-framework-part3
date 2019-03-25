package com.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

@Getter

@AllArgsConstructor

public class NotValidInformationException extends RuntimeException {

    private String message = "NOT_VALID_INFORMATION";

}
