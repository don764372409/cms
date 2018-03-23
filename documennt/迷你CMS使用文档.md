# miniCMS使用文档 #

## 部署 ##
直接将项目的webapp目录填入tomcat\conf\server.xml中

## 使用 ##


1. 浏览器输入localhost/loginMySql.jsp进入数据库登录界面
2. 填写数据库相关信息,在登录时需要填入数据库名称,而不能选择
3. 进入配置页面
4. 默认全选表,暂时不支持指定选择某几张表
5. 点击"映射配置"链接进入列名与JAVA属性名的映射配置
6. 默认选择全部列生成,暂不支持指定列生成.(生成模型属性)
7. 输入基本包名，如:com.cms.xxx。将根据这个基本包生成entity，dao,service等子包。每张表还会根据包名生成子包。
8. 如果DAO的映射文件不在DAO接口包下,请另外指定。最终会在用户输入的映射包下加入子包生成映射文件。如:resource/mapper/user/UserDAO.xml
9. 点击生成。

## 功能 ##
1. 根据表生成模型,DAO接口,Service类
2. 默认生成5个方法:insert,update,delete,selectOneById,selectAll
3. 此CMS只支持MyBatis+Spring
4. 仅支持单表生成

## 后期更新 ##
1. 选择指定数据库
2. 选择指定表
3. 多对一映射
4. Controller(SpringMVC或Springboot两张方式)的生成
5. JSP相关页面的生成
6. 高级查询加分页
7. 支持
8. 多对多生成
9. shiro安全框架

