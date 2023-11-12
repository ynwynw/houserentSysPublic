# hrent

#### 介绍
基于springboot的房屋租赁管理系统，系统拥有三中角色，房东，普通用户和管理员，普通用户只能通过微信小程序访问，房东和管理员可以登录管理系统管理自己的房源等信息。

#### 软件架构
##### 前端
* 管理系统

  Vue+ElementUI+Axios+VueRouter

* 微信小程序

  Iview+WeUI

##### 后端

SpringBoot+Mybatis

##### 数据库

Mysql(5.7)

#### 项目截图

##### 管理系统

房源列表：

![房源列表](./img/g_1.png)

用户管理：

![用户管理](./img/g_2.png)

消息管理：

![消息管理](./img/g_3.png)

##### 微信小程序

房源筛选：

![房源筛选](./img/w_1.png)

申请管理：

![申请管理](F:\个人项目\git\hrent\img\w_2.png)


#### 安装教程

使用IDEA打开项目。

创建数据库，导入sql文件。

使用VsCode或Webstorm等工具打开项目里面的hrent-web文件夹，在命令行输入npm install安装依赖。

使用微信开发者工具打开hrent-wechat文件夹。

修改后端application.yml文件中的数据库连接相关信息,url,username,password等信息。

如果要使用nginx作为图片服务器，那么就需要修改yml中的一些配置(也可以进行修改，直接使用地址映射访问图片)，

```xml
nginx:
  local: F:\tool\nginx\nginx-1.20.2	#nginx安装地址
  picLocal: F:/tool/nginx/nginx-1.20.2/images	#项目图片存放位置
  visitLocal: localhost:88/images	#图片访问前缀(目前没用)
```

同时在nginx.conf配置文件里面加上相关配置,同时创建images文件夹

```
location /images/ {
	alias F:/tool/nginx/nginx-1.20.2/images/;
	autoindex on;
}
```



要在微信小程序里面获取微信的信息，需要将项目中的wechat-webapp.properties配置文件中的相关配置信息改为自己的。

```properties
#小程序id
appId=wx1322719d78fc392b
#小程序密钥
appSecret=15fe956ca2ee3996235592f78b928b6d
```

id和秘钥可以去微信公众平台登录账号查看。



* 如果需要使用腾讯地图，记得将hrent-web下面的src文件夹里面的config.js中的腾讯地图秘钥修改为自己的。



#### 使用说明

1. 在IDEA中运行项目，在VSCode或WebStorm运行命令npm run dev。然后访问localhost:8080，就可以看到项目的登录页面（默认的账号密码可以直接登录）。

   ![登录页面](./img/1.png)

   

   

2. 登录之后就可以进入主页：

   ![主页](./img/2.png)

3. 微信小程序也可以直接通过微信开发者工具访问

![微信小程序](./img/3.png)
