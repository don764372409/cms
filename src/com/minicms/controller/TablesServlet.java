package com.minicms.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.minicms.domain.CMSClass;
import com.minicms.domain.CMSColumn;
import com.minicms.dto.ColumnClassDTO;
import com.minicms.dto.ResultDTO;
import com.minicms.service.MySqlService;
import com.minicms.util.DBClassUtil;
import com.minicms.util.DButil;
import com.minicms.util.ZipUtil;

@WebServlet("/tables")
public class TablesServlet extends BaseServlet{
	private static final long serialVersionUID = 1L;
	private MySqlService mySqlService;
	@Override
	public void init() throws ServletException {
		mySqlService = new MySqlService();
	}
	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String cmd = req.getParameter("cmd");
		if ("loginMySql".equals(cmd)) {
			loginMySql(req,resp);
		}else if("mapper".equals(cmd)) {
			mapper(req,resp);
		}else if("allFileds".equals(cmd)) {
			allFileds(req,resp);
		}else if("mapperSave".equals(cmd)) {
			mapperSave(req,resp);
		}else if("create".equals(cmd)){
			create(req,resp);
		}else if("download".equals(cmd)){
			req.getRequestDispatcher("/WEB-INF/down.jsp").forward(req, resp);
		}else if("downFile".equals(cmd)){
			String path = req.getRealPath("/cms_zip");
			File f = new File(path+"/9527CMS.zip");
	        if(f.exists()){  
	            FileInputStream  fis = new FileInputStream(f);  
	            String filename=URLEncoder.encode(f.getName(),"utf-8"); //解决中文文件名下载后乱码的问题  
	            byte[] b = new byte[fis.available()];  
	            fis.read(b);  
	            resp.setCharacterEncoding("utf-8");  
	            resp.setHeader("Content-Disposition","attachment; filename="+filename+"");  
	            //获取响应报文输出流对象  
	            ServletOutputStream  out =resp.getOutputStream();
	            //输出  
	            out.write(b);  
	            out.flush();  
	            out.close(); 
	            fis.close();
	        }     
		}else {
			List<String> tables = mySqlService.getTables();
			req.setAttribute("tables", tables);
			req.getRequestDispatcher("/WEB-INF/tables.jsp").forward(req, resp);
		}
	}
	private void create(HttpServletRequest req, HttpServletResponse resp) {
		ResultDTO result = null;
		try {
			String basePathName = req.getParameter("basePathName");
			String mapperPathName = req.getParameter("mapperPathName");
			//开始生成公共包路径
			String basePath = "C://9527CMS/"+basePathName.replace(".", "/");
			File file = new File(basePath);
			if (!file.exists()) {
				file.mkdirs();
			}
			//获取表结构数据
			List<ColumnClassDTO> list = DBClassUtil.list;
			for (ColumnClassDTO dto : list) {
				//生成子包
				String subPackage = dto.getSubPackage();
				file = new File(basePath+"/domain/"+subPackage);
				if (!file.exists()) {
					file.mkdirs();
				}
				file = new File(basePath+"/dao/"+subPackage);
				if (!file.exists()) {
					file.mkdirs();
				}
				file = new File(basePath+"/service/"+subPackage);
				if (!file.exists()) {
					file.mkdirs();
				}
				//------------------模型---------------------------------
				//获取类名
				String className = dto.getClassName();
				file = new File(basePath+"/domain/"+subPackage+"/"+className+".java");
				if (!file.exists()) {
					file.createNewFile();
				}
				//获取属性
				List<CMSClass> classes = dto.getClasses();
				FileWriter out = new FileWriter(file, true);
				//公共包
				out.write("package "+basePathName+".domain."+subPackage+";\n");
				//导入包
				for (CMSClass clz : classes) {
					if (!clz.getType().contains("java.lang.")) {
						out.write("import "+clz.getType()+";\n"); 
					}
				}
				//类名
				out.write("public class "+className+"{\n"); 
				//属性
				for (CMSClass clz : classes) {
					String type = clz.getType();
					int index = type.lastIndexOf(".");
					type = type.substring(index+1, type.length());
					out.write("\tprivate "+type+" "+clz.getName()+";\n");
				}
				//方法
				for (CMSClass clz : classes) {
					String type = clz.getType();
					int index = type.lastIndexOf(".");
					type = type.substring(index+1, type.length());
					out.write("\tpublic "+type+" get"+clz.getName().substring(0, 1).toUpperCase() + clz.getName().substring(1)+"(){\n\t\treturn this."+clz.getName()+";\n\t}\n");
					out.write("\tpublic void set"+clz.getName().substring(0, 1).toUpperCase() + clz.getName().substring(1)+"("+type+" "+clz.getName()+"){\n\t\tthis."+clz.getName()+"="+clz.getName()+";\n\t}\n");
					
				}
				//生成模型
				out.write("}");
				out.close();
				//----------------------------------------------------------
				//-------------------生成DAO-------------------------------------------
				file = new File(basePath+"/dao/"+subPackage+"/"+className+"DAO.java");
				//公共包
				if (!file.exists()) {
					file.createNewFile();
				}
				FileWriter outDAO = new FileWriter(file, true);
				//公共包
				outDAO.write("package "+basePathName+".dao."+subPackage+";\n");
				//导入包
				for (CMSClass clz : classes) {
					if (!clz.getType().contains("java.lang.")) {
						outDAO.write("import "+clz.getType()+";\n"); 
					}
				}
				outDAO.write("import "+basePathName+".domain."+subPackage+"."+dto.getClassName()+";\n");
				//SpringBoot的两个注解
				outDAO.write("import org.apache.ibatis.annotations.Mapper;\n");
				outDAO.write("import org.springframework.stereotype.Repository;\n");
				
				outDAO.write("import java.util.List;\n"); 
				
				//SpringBoot的两个注解
				outDAO.write("@Mapper\n"); 
				outDAO.write("@Repository\n"); 
				//类名
				outDAO.write("public interface "+className+"DAO{\n"); 
				//方法
				outDAO.write("\tint insert("+dto.getClassName()+" obj);\n"); 
				outDAO.write("\tint update("+dto.getClassName()+" obj);\n"); 
				outDAO.write("\tint delete(Long id);\n"); 
				outDAO.write("\t"+dto.getClassName()+" selectOneById(Long id);\n"); 
				outDAO.write("\tList<"+dto.getClassName()+"> selectAll();\n"); 
				outDAO.write("}");
				outDAO.close();
				//生成映射文件
				if (mapperPathName==null||"".equals(mapperPathName)) {
					//创建XML
					file = new File(basePath+"/dao/"+subPackage+"/"+className+"DAO.xml");
					if (!file.exists()) {
						file.createNewFile();
					}
					//写XML头
					FileWriter outXML = new FileWriter(file, true);
					outXML.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
					outXML.write("<!DOCTYPE mapper PUBLIC \"-//mybatis.org//DTD Mapper 3.0//EN\"\n");
					outXML.write("\"http://mybatis.org/dtd/mybatis-3-mapper.dtd\">\n");
					outXML.write("<mapper namespace=\""+basePathName+".dao."+subPackage+"."+dto.getClassName()+"DAO\">\n");
					outXML.write("\t<resultMap type=\""+dto.getClassName()+"\" id=\""+dto.getClassName()+"ResultMap\">\n");
					for (int i = 0; i < dto.getColumns().size(); i++) {
						if ("PRI".equals(dto.getColumns().get(i).getKey())) {
							outXML.write("\t\t<id column=\""+dto.getColumns().get(i).getName()+"\" property=\""+dto.getClasses().get(i).getName()+"\"/>\n");
						}else {
							outXML.write("\t\t<result column=\""+dto.getColumns().get(i).getName()+"\" property=\""+dto.getClasses().get(i).getName()+"\"/>\n");
						}
					}
					outXML.write("\t</resultMap>\n");
					//5条SQL
					//insert
					outXML.write("\t<insert id=\"insert\" parameterType=\""+dto.getClassName()+"\">\n");
					outXML.write("\t\tinsert into "+dto.getTableName()+"(");
					for (int i = 0; i < dto.getColumns().size(); i++) {
						if (!"PRI".equals(dto.getColumns().get(i).getKey())) {
							if (i==dto.getColumns().size()-1) {
								outXML.write(dto.getColumns().get(i).getName());
							}else {
								outXML.write(dto.getColumns().get(i).getName()+",");
							}
						}
					}
					outXML.write(") values(");
					for (int i = 0; i < dto.getColumns().size(); i++) {
						if (!"PRI".equals(dto.getColumns().get(i).getKey())) {
							if (i==dto.getColumns().size()-1) {
								outXML.write("#{"+dto.getClasses().get(i).getName()+"}");
							}else {
								outXML.write("#{"+dto.getClasses().get(i).getName()+"},");
							}
						}
					}
					outXML.write(")\n");
					outXML.write("\t</insert>\n");
					//update
					outXML.write("\t<update id=\"update\" parameterType=\""+dto.getClassName()+"\">\n");
					outXML.write("\t\tupdate "+dto.getTableName()+" set ");
					for (int i = 0; i < dto.getColumns().size(); i++) {
						if (!"PRI".equals(dto.getColumns().get(i).getKey())) {
							if (i==dto.getColumns().size()-1) {
								outXML.write(dto.getColumns().get(i).getName()+"=#{"+dto.getClasses().get(i).getName()+"}");
							}else {
								outXML.write(dto.getColumns().get(i).getName()+"=#{"+dto.getClasses().get(i).getName()+"},");
							}
						}
					}
					for (int i = 0; i < dto.getColumns().size(); i++) {
						if ("PRI".equals(dto.getColumns().get(i).getKey())) {
							outXML.write(" where "+dto.getColumns().get(i).getName()+"=#{"+dto.getClasses().get(i).getName()+"}");
						}
					}
					outXML.write("\n\t</update>\n");
					//delete
					outXML.write("\t<delete id=\"delete\" parameterType=\"Long\">\n");
					outXML.write("\t\tdelete from "+dto.getTableName()+" where ");
					for (int i = 0; i < dto.getColumns().size(); i++) {
						if ("PRI".equals(dto.getColumns().get(i).getKey())) {
							outXML.write(dto.getColumns().get(i).getName()+"=#{"+dto.getClasses().get(i).getName()+"}");
						}
					}
					outXML.write("\n\t</delete>\n");
					//select
					outXML.write("\t<select id=\"selectOneById\" parameterType=\"Long\" resultMap=\""+dto.getClassName()+"ResultMap\">\n");
					outXML.write("\t\tselect * from "+dto.getTableName()+" where ");
					for (int i = 0; i < dto.getColumns().size(); i++) {
						if ("PRI".equals(dto.getColumns().get(i).getKey())) {
							outXML.write(dto.getColumns().get(i).getName()+"=#{"+dto.getClasses().get(i).getName()+"}");
						}
					}
					outXML.write("\n\t</select>\n");
					//selectAll
					outXML.write("\t<select id=\"selectAll\" resultMap=\""+dto.getClassName()+"ResultMap\">\n");
					outXML.write("\t\tselect * from "+dto.getTableName());
					outXML.write("\n\t</select>\n");
					outXML.write("</mapper>");
					outXML.close();
				}
				//-----------service------------------------------------------------------------------
				file = new File(basePath+"/service/"+subPackage+"/"+className+"Service.java");
				//公共包
				if (!file.exists()) {
					file.createNewFile();
				}
				FileWriter outService = new FileWriter(file, true);
				//公共包
				outService.write("package "+basePathName+".service."+subPackage+";\n");
				//导入包
				for (CMSClass clz : classes) {
					if (!clz.getType().contains("java.lang.")) {
						outService.write("import "+clz.getType()+";\n"); 
					}
				}
				outService.write("import "+basePathName+".domain."+subPackage+"."+dto.getClassName()+";\n"); 
				outService.write("import "+basePathName+".dao."+subPackage+"."+dto.getClassName()+"DAO;\n"); 
				outService.write("import org.springframework.stereotype.Service;\n"); 
				outService.write("import org.springframework.beans.factory.annotation.Autowired;\n"); 
				outService.write("import java.util.List;\n"); 
				
				//类名
				outService.write("@Service\npublic class "+className+"Service{\n");
				outService.write("\t@Autowired\n\tprivate "+dto.getClassName()+"DAO "+(new StringBuilder()).append(Character.toLowerCase(dto.getClassName().charAt(0))).append(dto.getClassName().substring(1)).toString()+"DAO;");
				//方法
				outService.write("\n\tpublic int insert("+dto.getClassName()+" obj){\n"); 
				outService.write("\t\treturn "+(new StringBuilder()).append(Character.toLowerCase(dto.getClassName().charAt(0))).append(dto.getClassName().substring(1)).toString()+"DAO.insert(obj);"+"\n"); 
				outService.write("\t}\n"); 
				
				outService.write("\n\tpublic int update("+dto.getClassName()+" obj){\n"); 
				outService.write("\t\treturn "+(new StringBuilder()).append(Character.toLowerCase(dto.getClassName().charAt(0))).append(dto.getClassName().substring(1)).toString()+"DAO.update(obj);"+"\n"); 
				outService.write("\t}\n"); 
				
				outService.write("\n\tpublic int delete(Long id){\n"); 
				outService.write("\t\treturn "+(new StringBuilder()).append(Character.toLowerCase(dto.getClassName().charAt(0))).append(dto.getClassName().substring(1)).toString()+"DAO.delete(id);"+"\n"); 
				outService.write("\t}\n"); 
				
				outService.write("\n\tpublic "+dto.getClassName()+" selectOneById(Long id){\n"); 
				outService.write("\t\treturn "+(new StringBuilder()).append(Character.toLowerCase(dto.getClassName().charAt(0))).append(dto.getClassName().substring(1)).toString()+"DAO.selectOneById(id);"+"\n"); 
				outService.write("\t}\n"); 
				
				outService.write("\n\tpublic List<"+dto.getClassName()+"> selectAll(){\n"); 
				outService.write("\t\treturn "+(new StringBuilder()).append(Character.toLowerCase(dto.getClassName().charAt(0))).append(dto.getClassName().substring(1)).toString()+"DAO.selectAll();"+"\n"); 
				outService.write("\t}\n"); 
				outService.write("}");
				outService.close();
			}
			if (mapperPathName!=null&&!"".equals(mapperPathName)) {
				basePath = "C://9527CMS/"+mapperPathName.replace(".", "/");
				file = new File(basePath);
				if (!file.exists()) {
					file.mkdirs();
				}
				for (ColumnClassDTO dto : list) {
					//创建XML
					file = new File(basePath+"/"+dto.getSubPackage()+"/");
					if (!file.exists()) {
						file.mkdirs();
					}
					file = new File(basePath+"/"+dto.getSubPackage()+"/"+dto.getClassName()+"DAO.xml");
					//写XML头
					FileWriter outXML = new FileWriter(file, true);
					outXML.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
					outXML.write("<!DOCTYPE mapper PUBLIC \"-//mybatis.org//DTD Mapper 3.0//EN\"\n");
					outXML.write("\"http://mybatis.org/dtd/mybatis-3-mapper.dtd\">\n");
					outXML.write("<mapper namespace=\""+basePathName+".dao."+dto.getSubPackage()+"."+dto.getClassName()+"DAO\">\n");
					outXML.write("\t<resultMap type=\""+dto.getClassName()+"\" id=\""+dto.getClassName()+"ResultMap\">\n");
					for (int i = 0; i < dto.getColumns().size(); i++) {
						if ("PRI".equals(dto.getColumns().get(i).getKey())) {
							outXML.write("\t\t<id column=\""+dto.getColumns().get(i).getName()+"\" property=\""+dto.getClasses().get(i).getName()+"\"/>\n");
						}else {
							outXML.write("\t\t<result column=\""+dto.getColumns().get(i).getName()+"\" property=\""+dto.getClasses().get(i).getName()+"\"/>\n");
						}
					}
					outXML.write("\t</resultMap>\n");
					//5条SQL
					//insert
					outXML.write("\t<insert id=\"insert\" parameterType=\""+dto.getClassName()+"\">\n");
					outXML.write("\t\tinsert into "+dto.getTableName()+"(");
					for (int i = 0; i < dto.getColumns().size(); i++) {
						if (!"PRI".equals(dto.getColumns().get(i).getKey())) {
							if (i==dto.getColumns().size()-1) {
								outXML.write(dto.getColumns().get(i).getName());
							}else {
								outXML.write(dto.getColumns().get(i).getName()+",");
							}
						}
					}
					outXML.write(") values(");
					for (int i = 0; i < dto.getColumns().size(); i++) {
						if (!"PRI".equals(dto.getColumns().get(i).getKey())) {
							if (i==dto.getColumns().size()-1) {
								outXML.write("#{"+dto.getClasses().get(i).getName()+"}");
							}else {
								outXML.write("#{"+dto.getClasses().get(i).getName()+"},");
							}
						}
					}
					outXML.write(")\n");
					outXML.write("\t</insert>\n");
					//update
					outXML.write("\t<update id=\"update\" parameterType=\""+dto.getClassName()+"\">\n");
					outXML.write("\t\tupdate "+dto.getTableName()+" set ");
					for (int i = 0; i < dto.getColumns().size(); i++) {
						if (!"PRI".equals(dto.getColumns().get(i).getKey())) {
							if (i==dto.getColumns().size()-1) {
								outXML.write(dto.getColumns().get(i).getName()+"=#{"+dto.getClasses().get(i).getName()+"}");
							}else {
								outXML.write(dto.getColumns().get(i).getName()+"=#{"+dto.getClasses().get(i).getName()+"},");
							}
						}
					}
					for (int i = 0; i < dto.getColumns().size(); i++) {
						if ("PRI".equals(dto.getColumns().get(i).getKey())) {
							outXML.write(" where "+dto.getColumns().get(i).getName()+"=#{"+dto.getClasses().get(i).getName()+"}");
						}
					}
					outXML.write("\n\t</update>\n");
					//delete
					outXML.write("\t<delete id=\"delete\" parameterType=\"Long\">\n");
					outXML.write("\t\tdelete from "+dto.getTableName()+" where ");
					for (int i = 0; i < dto.getColumns().size(); i++) {
						if ("PRI".equals(dto.getColumns().get(i).getKey())) {
							outXML.write(dto.getColumns().get(i).getName()+"=#{"+dto.getClasses().get(i).getName()+"}");
						}
					}
					outXML.write("\n\t</delete>\n");
					//select
					outXML.write("\t<select id=\"selectOneById\" parameterType=\"Long\" resultMap=\""+dto.getClassName()+"ResultMap\">\n");
					outXML.write("\t\tselect * from "+dto.getTableName()+" where ");
					for (int i = 0; i < dto.getColumns().size(); i++) {
						if ("PRI".equals(dto.getColumns().get(i).getKey())) {
							outXML.write(dto.getColumns().get(i).getName()+"=#{"+dto.getClasses().get(i).getName()+"}");
						}
					}
					outXML.write("\n\t</select>\n");
					//selectAll
					outXML.write("\t<select id=\"selectAll\" resultMap=\""+dto.getClassName()+"ResultMap\">\n");
					outXML.write("\t\tselect * from "+dto.getTableName());
					outXML.write("\n\t</select>\n");
					outXML.write("</mapper>");
					outXML.close();
					
				}
				}
			result = ResultDTO.newInStrance("生成成功,共:"+DBClassUtil.list.size()+"张表数据",true);
		} catch (Exception e) {
			result = ResultDTO.newInStrance("生成失败,请稍后重试.",false);
		}
		try {
			String path = req.getRealPath("/cms_zip");
			ZipUtil.toZip("C://9527CMS", new FileOutputStream(new File(path+"/9527CMS.zip")), true);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			result = ResultDTO.newInStrance(e.getMessage(),false);
		} catch (RuntimeException e) {
			e.printStackTrace();
			result = ResultDTO.newInStrance(e.getMessage(),false);
		}
		putJSON(resp, result);
	}
	private void mapperSave(HttpServletRequest req, HttpServletResponse resp) {

		String tableName = req.getParameter("tableName");
		String className = req.getParameter("className");
		String subPackage = req.getParameter("subPackage");
		String filedNames = req.getParameter("filedNames");
		String types = req.getParameter("types");
		ResultDTO dto = null;
		try {
			for (ColumnClassDTO ccDto : DBClassUtil.list) {
				if (ccDto.getTableName().equals(tableName)) {
					ccDto.setSubPackage(subPackage);
					ccDto.setClassName(className);
					String[] filedNames2 = filedNames.split(",");
					String[] types2 = types.split(",");
					if (filedNames2.length!=types2.length) {
						throw new RuntimeException("属性与属性类型不匹配,请刷新后重试.");
					}else {
						List<CMSClass> list = new ArrayList<>();
						for (int i = 0;i<types2.length;i++) {
							CMSClass clz = new CMSClass();
							clz.setName(filedNames2[i]);
							clz.setType(types2[i]);
							list.add(clz);
						}
						ccDto.setClasses(list);
					}
				}
			}
			dto = ResultDTO.newInStrance("保存成功!",true);
		} catch (Exception e) {
			e.printStackTrace();
			dto = ResultDTO.newInStrance(e.getMessage(),false);
		}
		putJSON(resp, dto);
	
	}
	private void allFileds(HttpServletRequest req, HttpServletResponse resp) {
		//获取所有表的所有字段
		List<String> tables = mySqlService.getTables();
		List<ColumnClassDTO> list = DBClassUtil.list;
		for (String table : tables) {
			for (ColumnClassDTO dto : list) {
				//如果已经存在,就不要在操作了
				if (dto.getTableName().equals(table)) {
					return;
				}
			}
			//获取单表所有字段
			ColumnClassDTO dto = mySqlService.getTableInfo(table);
			//设置默认子包
			dto.setSubPackage(table);
			//设置默认类名
			dto.setClassName(table.substring(0, 1).toUpperCase() + table.substring(1));
			List<CMSColumn> columns = dto.getColumns();
			List<CMSClass> clzes = new ArrayList<>();
			for (CMSColumn col : columns) {
				CMSClass clz = new CMSClass();
				clz.setName(col.getName());
				clz.setType(DBClassUtil.convterType(col.getType()));
				clzes.add(clz);
			}
			dto.setClasses(clzes);
			DBClassUtil.list.add(dto);
		}
	}
	private void mapper(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String name = req.getParameter("name");
		ColumnClassDTO dto = mySqlService.getTableInfo(name);
		req.setAttribute("dto", dto);
		req.getRequestDispatcher("/WEB-INF/mapper.jsp").forward(req, resp);
	}
	private void loginMySql(HttpServletRequest req, HttpServletResponse resp) {
		String url = req.getParameter("url");
		String port = req.getParameter("port");
		String databaseName = req.getParameter("databaseName");
		String username = req.getParameter("username");
		String password = req.getParameter("password");
		DButil.url=url;
		DButil.port=port;
		DButil.dataBaseName=databaseName;
		DButil.username=username;
		DButil.password=password;
		ResultDTO dto = null;
		try {
			DButil.getConn();
			dto = ResultDTO.newInStrance("登录成功!跳转数据表列表页面!",true);
		} catch (Exception e) {
			e.printStackTrace();
			dto = ResultDTO.newInStrance(e.getMessage(),false);
		}
		putJSON(resp, dto);
	}
}
