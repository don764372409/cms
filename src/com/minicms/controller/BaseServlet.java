package com.minicms.controller;

import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;

public class BaseServlet extends HttpServlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected void putJSON(HttpServletResponse resp,Object obj){
		resp.setContentType("application/json; charset=utf-8");  
		resp.setCharacterEncoding("UTF-8");  
        String userJson = JSON.toJSONString(obj);  
        OutputStream out;
		try {
			out = resp.getOutputStream();
			out.write(userJson.getBytes("UTF-8"));  
			out.flush();  
		} catch (IOException e) {
			e.printStackTrace();
		}  
	}
}
