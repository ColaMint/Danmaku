<?php
	require_once("DanmakuZbus.php");
	
	$zbus = new DanmakuZbus('127.0.0.1', '15555');

	$zbus->createSubPubQueue("danmaku", "", "danmaku");

	$zbus->sendDanmaku("danmaku", "", "danmaku", "123", "123", "123");
	
?>
