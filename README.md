#Danmaku
##  介绍
-------
Danmaku是一款弹幕软件，可用于举办活动时，在Windows系统的电脑屏幕上显示弹幕，让参加活动的人员发送弹幕。

该版本利用zbus的发布订阅功能。[【少帮主/zbus】](http://git.oschina.net/rushmore/zbus)
##  部署指南
-------
###Part-One:danmaku-java-server(zbus服务端)
* 在pom.xml所在文件夹下使用maven进行打包，打包后的文件在target文件夹下 ： 
```
mvn clean package
```
* 修改服务器参数的配置文件: target/server.properties
```
server.port=15555       #zbus端口
server.token=danmaku    #在zbus进行管理员操作的口令（例如：创建队列）
```
* 运行target下的launch.bat或launch.sh即可运行服务端

###Part-Two:danmaku-java-client
* 在pom.xml所在文件夹下使用maven进行打包，打包后的文件在target文件夹下 ： 
```
mvn clean package
```
* 修改服务器参数的配置文件（也可在运行程序时填写）: target/danmaku.properties
```
server.host=127.0.0.1   #zbus服务器IP
server.port=15555       #zbus服务器端口
server.mq=danmaku       #队列名
server.token=danmaku    #访问队列的口令
server.topic=danmaku    #订阅的主题
```
* 运行target下的launch.bat或launch.sh即可运行客户端

###Part-Three:根据zbus协议开发发送弹幕的程序

*   创建队列

        URL                  :  http://[zbus服务器IP]:[zbus服务器端口]
        Method               :  POST
        Extended headers     ： cmd:admin
                                sub_cmd:create_mq
                                token:[进行管理员操作的口令]
        Body                 ： {"accessToken":"访问队列的口令","mqName":"队列名","mqMode":2}
        
*   添加弹幕 

        URL                  :  http://[zbus服务器IP]:[zbus服务器端口]
        Method               :  POST
        Extended headers     ： cmd:produce
                                mq:[队列名]
                                token:[访问队列的口令]
                                topic:发布的主题
        Body                 :  {"userid":"用户ID","username":"用户名","content":"弹幕内容",
                                "font_size":40,"color_r":0,"color_g":0,"color_b":0,"speed":5}

###Part-Four:部署弹幕发送端于微信公众号的示例程序 /danmaku-php-server/weixin
*   使用composer下载php依赖库wechat-php-sdk
   
    [详细过程点此查看](https://github.com/meso5533/Danmaku/tree/master/danmaku-php-server/weixin)
*   修改微信公众号参数：/danmaku-php-server/weixin/wechat.php
```php
    $options        = array(    'token'             => 'danmaku',
                                'appid'             => 'xxx',
                                'appsecret'         => 'xxx');
    $zbus_config    = array(    'host'              => 'zbus服务器IP',
                                'port'              => 'zbus服务器端口',
                                'admin_token'       => '进行管理员操作的口令',
                                'mq'                => '队列名',
                                'mq_access_token'   => '访问队列的口令',
                                'topic'             => '发布订阅的主题');
    
```
*   在微信公众号后台配置接口信息
        
        URL：http://host:port/danmaku-php-server/weixin/wechat.php
        Token:danmaku
    验证接口配置信息时把 /danmaku-php-server/weixin/wechat.php 中下面这行代码取消注释，验证完后加上注释
        
        $weObj->valid();
*   发送"cmd:create_mq" 给公众号以在zbus上创建一个消息队列
*   打开桌面端程序，填写服务器信息，并启动，然后在公众号上发送弹幕吧！
