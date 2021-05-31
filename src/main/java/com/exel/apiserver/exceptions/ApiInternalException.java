package com.exel.apiserver.exceptions;

import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@Builder
public class ApiInternalException extends RuntimeException {
	private final HttpStatus status;
	private final String message;

}
