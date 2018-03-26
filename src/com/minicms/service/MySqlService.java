package com.minicms.service;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.minicms.domain.CMSColumn;
import com.minicms.dto.ColumnClassDTO;
import com.minicms.util.DButil;

public class MySqlService {
	/**
	 * 获取当前使用的数据库名
	 */
	public String getDatabaseName() {
		try { 
			String sql = "select database();";
			PreparedStatement state = DButil.getConn().prepareStatement(sql);
			ResultSet set = state.executeQuery();
			if(set.next()) {
				return set.getString("database()");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * 获取所有数据库名
	 */
	public void changeDatabase(String databaseName) {
		try { 
			String sql = "use "+databaseName+";";
			PreparedStatement state = DButil.getConn().prepareStatement(sql);
			state.execute();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 获取所有数据库名
	 */
	public List<String> getDatabases() {
		List<String> list = new ArrayList<>();
		try { 
			String sql = "show databases";
			PreparedStatement state = DButil.getConn().prepareStatement(sql);
			ResultSet query = state.executeQuery();
			while(query.next()){
				list.add(query.getString("Database"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	/**
	 * 获取所有表名
	 */
	public List<String> getTables() {
		List<String> list = new ArrayList<>();
		try { 
			String sql = "SELECT TABLE_NAME FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_SCHEMA=?";
			PreparedStatement state = DButil.getConn().prepareStatement(sql);
			state.setObject(1, DButil.dataBaseName);
			ResultSet query = state.executeQuery();
			while(query.next()){
				list.add(query.getString("TABLE_NAME"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	/**
	 * 获取某表的字段名
	 * @return
	 */
	public ColumnClassDTO getTableInfo(String tableName){
		List<CMSColumn> list = new ArrayList<>();
		ColumnClassDTO dto = new ColumnClassDTO();
		try { 
			String sql = "SELECT COLUMN_NAME,COLUMN_KEY,DATA_TYPE FROM `information_schema`.`columns`" + 
					"WHERE `table_schema` = ?" + "AND `table_name` = ?;";
			PreparedStatement state = DButil.getConn().prepareStatement(sql);
			state.setObject(1, DButil.dataBaseName);
			state.setObject(2, tableName);
			ResultSet query = state.executeQuery();
			while(query.next()){
				CMSColumn filed = new CMSColumn();
				filed.setKey(query.getString("COLUMN_KEY"));
				filed.setName(query.getString("COLUMN_NAME"));
				filed.setType(query.getString("DATA_TYPE"));
				list.add(filed);
			}
			dto.setTableName(tableName);
			dto.setColumns(list);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dto;
	}
}
