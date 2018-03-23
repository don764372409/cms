package com.minicms.dto;

import java.util.ArrayList;
import java.util.List;

import com.minicms.domain.CMSClass;
import com.minicms.domain.CMSColumn;

public class ColumnClassDTO {
	private String tableName;
	private String className;
	private String subPackage;
	private List<CMSClass> classes = new ArrayList<>();
	private List<CMSColumn> columns = new ArrayList<>();
	
	public String getTableName() {
		return tableName;
	}


	public void setTableName(String tableName) {
		this.tableName = tableName;
	}


	public String getClassName() {
		return className;
	}


	public void setClassName(String className) {
		this.className = className;
	}


	public String getSubPackage() {
		return subPackage;
	}


	public void setSubPackage(String subPackage) {
		this.subPackage = subPackage;
	}


	public List<CMSClass> getClasses() {
		return classes;
	}


	public void setClasses(List<CMSClass> classes) {
		this.classes = classes;
	}


	public List<CMSColumn> getColumns() {
		return columns;
	}


	public void setColumns(List<CMSColumn> columns) {
		this.columns = columns;
	}


	@Override
	public String toString() {
		return "ColumnClassDTO [tableName=" + tableName + ", className=" + className + ", subPackage=" + subPackage
				+ ", classes=" + classes + ", columns=" + columns + "]";
	}
}
