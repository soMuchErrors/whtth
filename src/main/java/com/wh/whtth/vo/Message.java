package com.wh.whtth.vo;

public class Message {
	private String msg;
	private String status;
	
	public Message(){
		
	}
	
	public Message(String status,String msg){
		this.setMsg(msg);
		this.setStatus(status);
	}
	
	public Message(String status){
		this.setStatus(status);
		this.setMsg("");
	}
	
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
}
