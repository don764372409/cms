package com.minicms.dto;

public class ResultDTO {
	private String message;
	private boolean result;
	private static ResultDTO resultDTO = new ResultDTO();
	private  ResultDTO(){
		
	}
	
	public static ResultDTO newInStrance(String message,boolean result){
		resultDTO.message = message;
		resultDTO.result = result;
		return resultDTO;
	}
	
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public boolean isResult() {
		return result;
	}
	public void setResult(boolean result) {
		this.result = result;
	}
	@Override
	public String toString() {
		return "ResultDTO [message=" + message + ", result=" + result + "]";
	}
}
