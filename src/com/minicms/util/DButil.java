package com.minicms.util;

import java.sql.Connection;
import java.sql.DriverManager;

public class DButil {
	public static String url;
	public static String port;
	public static String dataBaseName;
	public static String username;
	public static String password;
	public static Connection conn = null;
	public static Connection getConn() {
		try {
			return conn;
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e.getMessage());
		}
	}
	/**
	 * ÓÃÓÚµÇÂ¼»òÇÐ»»µÇÂ¼
	 */
	public static void login() {
		try {
			if (conn!=null&&!conn.isClosed()) {
				conn.close();
			}
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://"+url+":"+port+"/"+dataBaseName, username, password);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e.getMessage());
		}
	}
	
}
