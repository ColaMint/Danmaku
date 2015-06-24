#Danmaku
##  介绍
-------
Danmaku是一款弹幕软件，可用于举办活动时，在Windows系统的电脑屏幕上显示弹幕，让参加活动的人员发送弹幕。
发送弹幕的形式可以根据API进行二次开发，也可以参照项目中的微信端代码。

##  部署指南
-------
###Part-One:danmaku-php-server
* 导入数据库脚本:/danmaku-php-server/danmaku.sql
```
    其中弹幕表的结构：

    CREATE TABLE `dmk_danmaku` (
        `id` int(11) NOT NULL AUTO_INCREMENT,
        `channel_id` int(11) unsigned NOT NULL,
        `userid` varchar(64) NOT NULL,
        `username` varchar(32) NOT NULL,
        `content` text NOT NULL,
        `font_size` tinyint(4) unsigned NOT NULL DEFAULT '40',
        `color_r` tinyint(4) unsigned NOT NULL DEFAULT '0',
        `color_g` tinyint(4) unsigned NOT NULL DEFAULT '0',
        `color_b` tinyint(4) unsigned NOT NULL DEFAULT '0',
        `speed` tinyint(4) unsigned NOT NULL DEFAULT '5',
        `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
        PRIMARY KEY (`id`)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8
```
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

###Part-Two:danmaku-java-clien
* 在pom.xml所在文件夹下使用maven进行打包，打包后的文件在target文件夹下 ： 
```
mvn clean package
```
* 修改服务器参数的配置文件（也可在运行程序时填写）: target/danmaku.properties
```
danmaku.host=localhost                                                      
danmaku.port=80
danmaku.project_name=danmaku-php-server
```
* 运行target/launch.bat即可运行客户端

###Part-Three:根据PHP端提供的API开发弹幕发送程序
#####API 返回的数据格式 ( json )
        成功 ：{"success":1,"data":...}
        失败 : {"success":0,"code":"错误码","message":"错误信息"}
#####一条弹幕包含的字段

    
#####API 列表 ：
*   创建频道

        URL                  :  http://host:port/danmaku-php-server/api/create_channel.php
        Method               :  POST
        Mandotary Params     :  channel_name string
        Return Data          :  channel_id
*   添加弹幕 

        URL                  :  http://host:port/danmaku-php-server/api/add.php
        Method               :  POST
        Mandotary Params     ： userid      string
                                channel_id  int
                                username    string
                                content     string
        Not Mandotary Params ： font_size   int     (default : 40)
                                color_r     int     (default :  0)
                                color_g     int     (default :  0)
                                color_b     int     (default :  0)
        Return Data          :  NULL
*   获取最新弹幕ID 

        URL                  :  http://host:port/danmaku-php-server/api/get_latest_id.php
        Method               :  GET
        Mandotary Params     ： channel_id  int
        Return Data          :  最新弹幕ID  int
*   获取弹幕

        URL                  :  http://host:port/danmaku-php-server/api/fetch.php
        Method               :  GET
        Mandotary Params     ： channel_id  int
                                latest_id   int
        Not Mandotary Params ： max_num     int     (default : 50)
        Return Data          :  返回ID大于latest_id中id较小的弹幕数据,最多返回maxnum条数据
*   清空所有弹幕

        URL                  :  http://host:port/danmaku-php-server/api/clear_table.php
        Method               :  GET/POST
        Mandotary Params     ： channel_id  int
        Return Data          :  NULL
*   查询指定频道是否存在

        URL                  :  http://host:port/danmaku-php-server/api/quert_channel.php
        Method               :  GET
        Mandotary Params     ： channel_id  int
        Return Data          :  NULL

###Part-Four:部署弹幕发送端于微信公众号的示例程序 /danmaku-php-server/weixin
*   使用composer下载php依赖库wechat-php-sdk
   
    [详细过程点此查看](https://github.com/meso5533/Danmaku/tree/master/danmaku-php-server/weixin)
*   修改微信公众号参数：/danmaku-php-server/weixin/wechat.php
```php
    $options = array(    'token' => 'danmaku',
                        'appid' => 'xxx',
                        'appsecret' => 'xxx');
    $channel_id = [你创建的频道ID];
```
*   在微信公众号后台配置接口信息
        
        URL：http://host:port/danmaku-php-server/weixin/wechat.php
        Token:danmaku
    验证接口配置信息时把 /danmaku-php-server/weixin/wechat.php 中下面这行代码取消注释，验证完后加上注释
        
        $weObj->valid();
*   打开桌面端程序，填写服务器信息，并启动，然后在公众号上发送弹幕吧！
