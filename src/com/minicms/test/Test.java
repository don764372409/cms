package com.minicms.test;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.minicms.util.DButil;

public class Test {
	public static void main(String[] args) {
		DButil.url = "localhost";
		DButil.port = "3306";
		DButil.username = "root";
		DButil.password = "admin";
		DButil.login();
		
		try {
			PreparedStatement state = DButil.getConn().prepareStatement("show databases");
			ResultSet set = state.executeQuery();
			while(set.next()) {
				System.err.println(set.getString("Database"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}