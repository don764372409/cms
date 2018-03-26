package com.minicms.domain;

public class CMSClass {
	private String name;//属性名称
	private String type;//属性类型
	private String coment;//属性注释
	
	public String getComent() {
		return coment;
	}
	public void setComent(String coment) {
		this.coment = coment;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	@Override
	public String toString() {
		return "CMSClass [name=" + name + ", type=" + type + ", coment=" + coment + "]";
	}
}
