<?php
	require_once(__dir__ . '/../common/init.php');
    require_once(DMK_LIB_PATH . '/api_query_channel.php');                                                                                                                                               
    $api = new ApiQueryChannel();
    $api->run();    
?> 
