<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<style type="text/css">
	input{
		width: 280px;
	}
</style>
<script type="text/javascript" src="/js/jquery.js"></script>
<script type="text/javascript">
	function loginMySql(){
		$.post("/loginMySql?cmd=loginMySql",{
			"url":$("input[name=url]").val(),
			"port":$("input[name=port]").val(),
			"username":$("input[name=username]").val(),
			"password":$("input[name=password]").val()},
			function(data){
				alert(data.message);
				if(data.result){
					location.href="/tables?cmd=databases";
				}
			}
		);
	}
	$(window).keyup(function(even){
		if(even.keyCode==13){
			loginMySql();
		}
	});
</script>
</head>
<body>
	<center>
		<div style="width: 800px;height: 800px;margin-top: 50px;">
			<table style="width: 800px;height: 600px;border: 1px solid #e3e3e3;">
				<tr>
					<td>主机名或IP地址:</td>
					<td><input placeholder="localhost" value="localhost" name="url"></td>
				</tr>
				<tr>
					<td>端口:</td>
					<td><input placeholder="3306" value="3306" type="number" name="port"></td>
				</tr>
				<tr>
					<td>账号:</td>
					<td><input placeholder="root" value="root" name="username"></td>
				</tr>
				<tr>
					<td>密码:</td>
					<td><input placeholder="数据库密码" value="admin" type="password" name="password"></td>
				</tr>
				<tr>
					<td align="center" colspan="2">
						<a href="javascript:void(0)" onclick="loginMySql()" style="display: block;border: 1px solid #e2e2e2;color: white;height:35px;line-height:35px;text-decoration: none;border-radius:5px;background: #ff6600;">进入数据库</a>
					</td>
				</tr>
			</table>
		</div>
	</center>
</body>
</html>