<?php
	require_once(__dir__ . '/vendor/dodgepudding/wechat-php-sdk/wechat.class.php');
	require_once(__dir__ . '/../DanmakuZbus.php');
	
	$Wechat_options = array(	'token' 			=> 'danmaku',
								'appid' 			=> 'wxf9b1d87dfc10774f',
								'appsecret' 		=> 'abc8e53b2b99896674779788e99911cd');

	$zbus_config    = array(	'host'  			=> '127.0.0.1',
								'port'  			=> '15555',
								'admin_token' 		=> 'danmaku',
								'mq'    			=> 'danmaku',
								'mq_access_token' 	=> 'danmaku',
								'topic' 			=> 'danmaku');

	$zbus  = new DanmakuZbus($zbus_config['host'], $zbus_config['port']);
	$topic = 'danmaku';

	$weObj = new Wechat($options);
	
	$weObj->valid();// 微信接口验证成功后注释掉此行

	$type = $weObj->getRev()->getRevType();
	switch($type){
		case Wechat::MSGTYPE_TEXT:{
			$openid    = $weObj->getRevFrom();
			$user_info = $weObj->getUserInfo($openid);
			$content   = $weObj->getRevContent(); 

			switch($content){
				case 'cmd:create_mq':{
					if($zbus->createSubPubQueue($zbus_config['mq'], $zbus_config['admin_token'], $zbus_config['mq_access_token'])){
						$weObj->text('创建队列成功！')->reply();
					}else{
						$weObj->text('创建队列失败！')->reply();
					}		
				}break;
				default:{
					if($user_info){
						if($db->sendDanmaku($zbus_config['mq'], $zbus_config['topic'], $openid, $user_info['nickname'], $content)){
							$weObj->text("弹幕发送成功!")->reply();
						}else{
							$weObj->text("弹幕发送失败!")->reply();
						}
					}		
				}
			}
		}break;
	}
	
?>
