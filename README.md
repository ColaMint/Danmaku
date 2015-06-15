#Danmaku
##  介绍
-------
Danmaku是一个弹幕软件，可用于举办活动时，在Windows系统的电脑屏幕上显示弹幕，让参加活动的人员发送弹幕。
发送弹幕的形式可以根据API进行二次开发，也可以参照项目中的微信端代码。

##  部署指南
-------
Part-One:danmaku-php-server
* 导入数据库脚本:/danmaku-php-server/danmaku.sql
* 修改数据库连接参数的配置文件：/danmaku-php-server/config.php
```php
  private static $config = array(
    'db_host' => '127.0.0.1',
    'db_port' => '3306',
    'db_user' => 'xxx',
    'db_pwd'  => 'xxx',
    'db_name' => 'danmaku',
  );
```

Part-Two:danmaku-java-client
* 导入项目到eclipse
* 修改服务器参数的配置文件（也可在运行程序时填写）:/danmaku-java-client/danmaku.properties
```
danmaku.host=localhost                                                      
danmaku.port=80
danmaku.project_name=danmaku-php-server
```
