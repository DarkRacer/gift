package com.gift.model.projections;

import lombok.Value;

@Value
public class ApiResponse {
	private Boolean success;
	private String message;
}
