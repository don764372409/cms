package com.minicms.domain;
/**
 * SELECT COLUMN_NAME,COLUMN_KEY,DATA_TYPE,,COLUMN_COMMENT FROM `information_schema`.`columns`
WHERE `table_schema` = 'springboot'
AND `table_name` = 't_user';
 */
public class CMSColumn {
	private String name;//�ֶ�����
	private String type;//�ֶ�����
	private String key;//��������� PRI--����  MUL--���
	private String coment;//ע��
	
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
