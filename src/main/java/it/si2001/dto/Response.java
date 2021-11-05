package it.si2001.dto;

import lombok.Data;

@Data
public class Response<T> {

	private T result;

	private boolean resultTest;

	private String error;

}
