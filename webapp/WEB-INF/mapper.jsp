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
#tt td{
	border: 1px solid #e2e2e2;
	height: 30px;
	border-left: none;
}
#tt td:first-child{
	border-left:1px solid #e2e2e2;
}
</style>
<script type="text/javascript" src="/js/jquery.js"></script>
<script type="text/javascript" src="/layer/layer.js"></script>
<script type="text/javascript">
	$(function(){
		var str= $("input[name=className]").val();
		str = str.substring(0,1).toUpperCase()+str.substring(1);
		//类名
		$("input[name=className]").val(str);
		//子包名
		$("#subPackage").val($("input[name=className]").val().toLowerCase());
	})
	function subPackage(value){
		$("#subPackage").val(value.toLowerCase());
	}
	function tableMapper(){
		//获取表名
		var tableName = "${dto.tableName}"+"".trim();
		//获取类名
		var className = $("input[name=className]").val()+"".trim();
		//获取子包名
		var subPackage = $("#subPackage").val()+"".trim();
		//获取属性名
		var names = $("input[name=filedName]");
		var filedNames = "";
		for (var i = 0; i < names.length; i++) {
			filedNames+=$(names[i]).val()+",";
		}
		//获取属性类型
		var types2 = $("input[name=type]");
		var types = "";
		for (var i = 0; i < types2.length; i++) {
			types+=$(types2[i]).val()+",";
		}
		$.post("/tables?cmd=mapperSave",{
			"tableName":tableName,
			"className":className,
			"subPackage":subPackage,
			"filedNames":filedNames,
			"types":types},
			function(data){
			alert(data.message);
			if(data.result){
				window.parent.layer.closeAll();
			}
		});
	}
</script>
</head>
<body>
	<center>
		<div style="width:785px;height: 65px;margin-top: 20px;border: 1px solid #e2e2e2;text-align: left;padding-left: 15px;">
			仅支持:<br>
			bigint--Long,varchar,char,text==String,integer,tinyint==Integer,int==int,float=Float<br>
			double==Double,date,datetime,time,timestamp==Date,decimal==BigDecimal等类型的转换
		</div>
		<div style="width: 800px;height: 550px;border: 1px solid #e2e2e2;overflow: auto;margin-top: 10px;">
			<table style="width: 770px;text-align: center;margin-top: 15px;" cellpadding="0" cellspacing="0">
				<tr id="tt">
					<td>表名</td>
					<td><input value="${dto.tableName}" readonly="readonly" style="border: 1px solid #e2e2e2;outline:none;"></td>
					<td>类名</td>
					<td><input name="className" value="${dto.tableName}" placeholder="请输入表名对应的类名" onkeyup="subPackage(this.value)"></td>
					<td>子包名称</td>
					<td><input value="" placeholder="请输入子包名称" id="subPackage"></td>
				</tr>
			</table>
			<table style="width: 770px;text-align: center;margin-top: 15px;" cellpadding="0" cellspacing="0">
				<thead>
				<tr>
					<th width="25%">字段名称</th>
					<th width="25%">字段类型</th>
					<th width="25%">属性名称</th>
					<th width="25%">属性类型</th>
				</tr>
				</thead>
				<tbody>
				<tr>
					<td colspan="5">
						<a href="javascript:void(0)" onclick="tableMapper()" style="padding:5px 10px;width:100px;border: 1px solid #e2e2e2;color: white;height:35px;line-height:35px;text-decoration: none;border-radius:5px;background: #ff6600;">保存配置</a>
					</td>
				</tr>
				<c:forEach items="${dto.columns}" var="column">
				<tr>
					<td>${column.name}</td>
					<td>${column.type}</td>
					<td><input name="filedName" value="${column.name}"></td>
					<td>
						<c:if test="${column.type=='bigint'}">
							<input readonly="readonly" name="type" value="java.lang.Long">
						</c:if>
						<c:if test="${column.type=='varchar' or column.type=='char' or column.type=='text'}">
							<input readonly="readonly" name="type" value="java.lang.String">
						</c:if>
						<c:if test="${column.type=='integer' or column.type=='tinyint'}">
							<input readonly="readonly" name="type" value="java.lang.Integer">
						</c:if>
						<c:if test="${column.type=='int'}">
							<input readonly="readonly" name="type" value="int">
						</c:if>
						<c:if test="${column.type=='date' or column.type=='datetime' or filcolumned.type=='time' or column.type=='timestamp'}">
							<input readonly="readonly" name="type" value="java.util.Date">
						</c:if>
						<c:if test="${column.type=='float'}">
							<input readonly="readonly" name="type" value="java.lang.Float">
						</c:if>
						<c:if test="${column.type=='double'}">
							<input readonly="readonly" name="type" value="java.lang.Double">
						</c:if>
						<c:if test="${column.type=='decimal'}">
							<input readonly="readonly" name="type" value="java.math.BigDecimal">
						</c:if>
					</td>
				</tr>
				</c:forEach>
				</tbody>
			</table>
		</div>
	</center>
</body>
</html>