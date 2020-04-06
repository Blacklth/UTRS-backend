# spring_template
spring boot 示范项目
<br>
日志框架：slf4j+log4j2<br>
持久层框架:mybatis（mybatis-plus) 连接mysql数据库<br> 后面考虑添加(MongoDB和Redis)<br>
API接口框架:Swagger2<br>

# 测试方式

## 1.测试Log4j2功能 url:localhost:8080/testLog4j2 
成功的话会在控制台输出相应的信息，但由于日志配置文件的限定，只能在控制台看到INFO以上的信息，可以在log4j2.xml的Appenders下的Consle调节<br>
## 2.测试统一异常处理的功能  
### 测试业务异常 url:localhost:8080/testException1 
成功信息:<br>
<code>
  {"code":"ErrorCode 000","state":0,"msg":"来自测试异常接口的信息","data":null}
</code>
### 测试系统异常 url:localhost:8080/testException2
成功信息:<br>
<code>
  {"code":"ArithmeticException","state":0,"msg":"/ by zero","data":null}
</code>

###  测试http异常 url:localhost:8080/testException3/true
成功信息:<br>
<code>
  {"code":"400 Bad_Request","state":0,"msg":" 参数类型错误","data":null}
</code>

###  测试404异常 url:localhost:8080/testException
成功信息:<br>
<code>
  {"code":"404 NOT_FOUND","state":0,"msg":" 未找到资源","data":null}
</code>

## 3.测试Mybatis增删改查
mysql创建template 数据库 创建user表 属性为 userId(int),userName(varchar(100)),password(varchar(1100)),phone(varchar(100)) <br>
userId 设置为从1000开始自增<br>
<code>
  CREATE DATABASE template;
  USE template;
  DROP TABLE IF EXISTS `user`;
  CREATE TABLE `user` (
  `user_id` int(10) NOT NULL AUTO_INCREMENT=1000,
  `user_name` varchar(100) DEFAULT NULL,
  `password` varchar(100) DEFAULT NULL,
  `phone` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`user_id`)
  ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
  </code>

运行TemplateApplicationTests 测试增删改查 其中删改是注解的形式，增查是用xmld的方式映射
