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
	$(function(){
		//页面加载完成后去获取所有表的字段
		$.post("/tables?cmd=allFileds");
		//全选框
		$("thead input[type=checkbox]").change(function(){
			if($("thead input[type=checkbox]")[0].checked){
				$("tbody input[type=checkbox]").each(function(index,item){
					item.checked = true;
				});
			}else{
				$("tbody input[type=checkbox]").each(function(index,item){
					item.checked = false;
				});
			}
		});
		if($("thead input[type=checkbox]")[0].checked){
			$("tbody input[type=checkbox]").each(function(index,item){
				item.checked = true;
			});
		}
		//每一个项中的checkbox
		$("tbody input[type=checkbox]").change(function(){
			var flag = true;
			$("tbody input[type=checkbox]").each(function(index,item){
				if (!item.checked) {
					flag = false;
				}
			});
			$("thead input[type=checkbox]")[0].checked = flag;
		});
	})
	function tableMapper(el,name){
		layer.open({
		      type: 2,
		      title: '列-字段映射配置',
		      maxmin: true,
		      shadeClose: true, //点击遮罩关闭层
		      area: ['850px', '700px'],
			  content: '/tables?cmd=mapper&name='+name
	    });
	}
	function createCMS(){
		var basePathName = $("input[name=basePathName]").val()+"".trim();
		if(!basePathName){
			alert("请填写公共包路径");
			return;
		}
		var mapperPathName = $("input[name=mapperPathName]").val()+"".trim();
		$.post("/tables?cmd=create",{"basePathName":basePathName,"mapperPathName":mapperPathName},function(data){
			alert(data.message);
			if(data.result){
				location.href="/tables?cmd=download";
			}
		});
	}
</script>
</head>
<body>
	<center>
		<div style="width:785px;height: 125px;margin-top: 20px;border: 1px solid #e2e2e2;text-align: left;padding-left: 15px;">
			1.选择需要生成代码的表<br>
			2.点击<span style="padding:5px 10px;width:100px;border: 1px solid #e2e2e2;color: white;height:35px;line-height:35px;text-decoration: none;border-radius:5px;background: #ff6600;">字段映射</span>配置相应的列--属性与表名--类名的映射配置<br>
			3.输入公共包路径,如:com.cms  生成的代码会放入这个公共包路径下<br>
			4.如果MyBatis的映射文件不跟DAO在同路径,请单独配置MyBatis的映射文件路径<br>
			5.点击一件生成<br>
		</div>
			${databaseName}
		<div style="width: 800px;height: 720px;margin-top: 10px;border: 1px solid #e2e2e2;">
			<div style="width: 800px;height: 650px;border-bottom: 1px solid #e2e2e2;overflow: auto;">
				<table style="width: 770px;text-align: center;margin-top: 15px;" cellpadding="0" cellspacing="0">
					<thead>
					<tr>
						<th width="5%"><input type="checkbox" checked="checked"></th>
						<th width="75%">表名</th>
						<th width="20%">操作</th>
					</tr>
					</thead>
					<tbody>
						<c:forEach items="${tables}" var="name">
							<tr>
								<td><input type="checkbox"></td>
								<td>${name}</td>
								<td>
									<a href="javascript:void(0)" onclick="tableMapper(this,'${name}')" style="padding:5px 10px;width:100px;border: 1px solid #e2e2e2;color: white;height:35px;line-height:35px;text-decoration: none;border-radius:5px;background: #ff6600;">映射配置</a>
								</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
			<div style="margin-top: 5px;">
				<input placeholder="请输入公共包路径" name="basePathName">
				<input placeholder="如果映射文件不在模型包下,请输入映射文件路径" name="mapperPathName" style="width: 300px;"><br>
				<a href="javascript:void(0)" onclick="createCMS()" style="padding:5px 10px;width:100px;border: 1px solid #e2e2e2;color: white;height:35px;line-height:35px;text-decoration: none;border-radius:5px;background: #ff6600;">开始生成</a>
			</div>
		</div>
	</center>
</body>
</html>