<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<style type="text/css">
th{
	border: 1px solid #e2e2e2;
	border-left: none;
}
th:first-child{
	border-left:1px solid #e2e2e2;
}
td{
	border: 1px solid #e2e2e2;
	border-top: none;
	border-left: none;
}
td:first-child{
	border-left:1px solid #e2e2e2;
}	
</style>
<script type="text/javascript" src="http://apps.bdimg.com/libs/jquery/2.0.0/jquery.min.js"></script>
<script type="text/javascript" src="/layer/layer.js"></script>
<script type="text/javascript">
	function tableMapper(name){
		location.href="/tables?cmd=tables&databaseName="+name;
	}
</script>
</head>
<body>
	<center>
		<div style="width: 800px;height: 650px;border-bottom: 1px solid #e2e2e2;overflow: auto;">
			<table style="width: 770px;text-align: center;margin-top: 15px;" cellpadding="0" cellspacing="0">
				<thead>
				<tr>
					<th width="80%">表名</th>
					<th width="20%">操作</th>
				</tr>
				</thead>
				<tbody>
					<c:forEach items="${dbs}" var="name">
						<tr>
							<td>${name}</td>
							<td>
								<a href="javascript:void(0)" onclick="tableMapper('${name}')" style="padding:5px 10px;width:100px;border: 1px solid #e2e2e2;color: white;height:35px;line-height:35px;text-decoration: none;border-radius:5px;background: #ff6600;">选择数据库</a>
							</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
	</center>
</body>
</html>