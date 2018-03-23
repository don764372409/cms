package com.minicms.domain;
/**
 * SELECT COLUMN_NAME,COLUMN_KEY,DATA_TYPE FROM `information_schema`.`columns`
WHERE `table_schema` = 'springboot'
AND `table_name` = 't_user';
 */
public class CMSColumn {
	private String name;//字段名称
	private String type;//字段类型
	private String key;//主键或外键 PRI--主键  MUL--外键
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
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	@Override
	public String toString() {
		return "CMSFiled [name=" + name + ", type=" + type + ", key=" + key + "]";
	}
}
