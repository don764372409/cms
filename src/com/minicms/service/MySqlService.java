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
	 * ��ȡ���б���
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
	 * ��ȡĳ����ֶ���
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
