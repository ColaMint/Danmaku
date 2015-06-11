<?php
	require_once(DMK_LIB_PATH . '/api_base.php');
	require_once(DMK_LIB_PATH . '/danmaku_db.php');

	class ApiTestServer extends ApiBase{
		
		public function __construct(){
			parent::__construct(self::API_TYPE_GET_OR_POST);
		}

		public function process(){
			$this->outputSuccess(NULL);
		}

	}


?>
