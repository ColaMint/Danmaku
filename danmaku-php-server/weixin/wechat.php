<?php
	require_once(__dir__ . '/wechat-php-sdk/wechat.class.php');
	require_once(__dir__ . '/../common/init.php');
	require_once(DMK_LIB_PATH . '/danmaku_db.php');

	$options = array('token' => 'danmaku',
						'appid' => 'wxf9b1d87dfc10774f',
							'appsecret' => 'abc8e53b2b99896674779788e99911cd');

	$db = DanmakuDb::getInstance();

	$weObj = new Wechat($options);
	$weObj->valid();
	$type = $weObj->getRev()->getRevType();
	switch($type){
		case Weichat::MSGTYPE_TEXT:{
			$openid = $weObj->getRevFrom();
			$user_info = $weObj->getUserInfo($openid);
			if($user_info){
				$ad->add($openid, $user_info['nickname'], $weObj->getRevContentgetRevContent());
			}	
		}break;
	}
	
?>
