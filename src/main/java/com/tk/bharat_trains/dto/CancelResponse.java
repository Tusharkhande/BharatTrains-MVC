package com.tk.bharat_trains.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CancelResponse {
	private boolean cancelStatus;
	private String message;
}
