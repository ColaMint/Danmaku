<?php
	require_once(__dir__ . '/vendor/dodgepudding/wechat-php-sdk/wechat.class.php');
	require_once(__dir__ . '/../DanmakuZbus.php');
	
	$options = array(	'token' => 'danmaku',
						'appid' => 'wxf9b1d87dfc10774f',
						'appsecret' => 'abc8e53b2b99896674779788e99911cd');
	$channel_id = 1;

	$zbus = DanmakuZbus::getInstance();

	$weObj = new Wechat($options);
	
	$weObj->valid();//after validation for the first time, comment this line

	$type = $weObj->getRev()->getRevType();
	switch($type){
		case Wechat::MSGTYPE_TEXT:{
			$openid = $weObj->getRevFrom();
			$user_info = $weObj->getUserInfo($openid);
			if($user_info){
				if($db->sendDanmaku($openid, $user_info['nickname'], $weObj->getRevContent())){
					$weObj->text("弹幕发送成功!")->reply();
				}else{
					$weObj->text("弹幕发送失败!")->reply();
				}
			}	
		}break;
	}
	
?>
