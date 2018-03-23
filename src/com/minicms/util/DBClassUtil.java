package com.minicms.util;

import java.util.ArrayList;
import java.util.List;

import com.minicms.dto.ColumnClassDTO;

public class DBClassUtil {
	/**
	 */
	public static List<ColumnClassDTO> list = new ArrayList<>();
	
	public static String convterType(String type) {
		if ("bigint".equals(type)) {
			return "java.lang.Long";
		}
		if ("varchar".equals(type)||"char".equals(type)||"text".equals(type)) {
			return "java.lang.String";
		}
		if ("integer".equals(type)||"tinyint".equals(type)) {
			return "java.lang.Integer";
		}
		if ("int".equals(type)) {
			return "int";
		}
		if ("date".equals(type)||"datetime".equals(type)||"time".equals(type)||"timestamp".equals(type)) {
			return "java.util.Date";
		}
		if ("float".equals(type)) {
			return "java.lang.Float";
		}
		if ("double".equals(type)) {
			return "java.lang.Double";
		}
		if ("decimal".equals(type)) {
			return "java.math.BigDecimal";
		}
		return type;
	}
}
