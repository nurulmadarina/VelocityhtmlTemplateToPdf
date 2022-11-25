package com.example.htmlToPdf;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class BaseResponse <T> implements Serializable {
	private static final long serialVersionUID = 1L;

	public static final int DEFAULT_SUCCESS_RESPONSE = 200;
	
	public static final int DEFAULT_ERROR_RESPONSE = -1;
	
	public static final String DEFAULT_SUCCESS_INFO = "OK";

	@JsonProperty("status")
	private int status = DEFAULT_SUCCESS_RESPONSE;
	
	@JsonProperty("info")
	private String info = DEFAULT_SUCCESS_INFO;
	
	@JsonProperty("content")
	private T content;	
	
	public BaseResponse() {
		
	}
	
	public BaseResponse(int status) {
		this.status = status;
	}
	
	public BaseResponse(T content) {
		this.content = content;
	}
	
	public BaseResponse(int status, T content) {
		this.status = status;
		this.content = content;
	}
	
	public BaseResponse(String info, T content) {
		this.info = info;
		this.content =  content;
	}
	
	public BaseResponse(int status, String info, T content) {
		this.status = status;
		this.info = info;
		this.content = content;
	}

}
