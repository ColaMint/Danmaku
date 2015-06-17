<?php
	require_once(DMK_LIB_PATH . '/api_base.php');
	require_once(DMK_LIB_PATH . '/danmaku_db.php');

	class ApiCreateChannel extends ApiBase{
		
		public function __construct(){
			parent::__construct(self::API_TYPE_POST);
		}

		private $mandatory = array('channel_name');

		protected function before(){
			$this->checkField($this->mandatory, NULL);
		}

		public function process(){
			$db = DanmakuDb::getInstance();
			$result = $db->createChannel($this->params['channel_name']);
			if($result){
				$this->outputSuccess($result);
			}else if($result){
				$this->outputCommonFailure();
			}
		}

	}
?>
