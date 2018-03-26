package com.minicms.controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.minicms.dto.ResultDTO;
import com.minicms.util.DBClassUtil;
import com.minicms.util.DButil;

@WebServlet("/loginMySql")
public class LoginMySqlServlet extends BaseServlet{
	private static final long serialVersionUID = 1L;

	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String cmd = req.getParameter("cmd");
		if ("loginMySql".equals(cmd)) {
			String url = req.getParameter("url");
			String port = req.getParameter("port");
			String username = req.getParameter("username");
			String password = req.getParameter("password");
			DButil.url=url;
			DButil.port=port;
			DButil.username=username;
			DButil.password=password;
			ResultDTO dto = null;
			try {
				DButil.login();
				//重新登录,清空表数据
				DBClassUtil.list = new ArrayList<>();
				dto = ResultDTO.newInStrance("登录成功!跳转数据表列表页面!",true);
			} catch (Exception e) {
				e.printStackTrace();
				dto = ResultDTO.newInStrance(e.getMessage(),false);
			}
			putJSON(resp, dto);
		}else {
			resp.sendRedirect("/loginMySql");
		}
	}
}
