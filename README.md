# travel_黑马旅游网
# 功能
用户注册登录、邮件激活、保持自动登录、线路分类浏览、线路查询、线路收藏
# 技术选型
## Web层
- Servlet：前端控制器
- html：视图
- ajax: 异步交互
- Filter：过滤器
- BeanUtils：数据封装
- Jackson：json序列化工具
## Service层
- Javamail：java发送邮件工具
- Java-jwt: 生成token，记录登陆状态
- Redis：内存数据库
- Jedis：java的redis客户端
## Dao层
- Mysql：数据库
- Druid：数据库连接池
- JdbcTemplate：jdbc的工具
## 服务器
- Tomcat8
