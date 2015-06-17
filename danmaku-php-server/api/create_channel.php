<?php
	require_once(__dir__ . '/../common/init.php');
    require_once(DMK_LIB_PATH . '/api_create_channel.php');
	
	$api = new ApiCreateChannel();
    $api->run();    
?> 
