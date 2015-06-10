<?php
	require_once(__dir__ . '/../common/init.php');
	require_once(DMK_LIB_PATH . '/api_get_latest_id.php');
	
	$api = new ApiGetLatestID();
    $api->run();	
?>
