<?php
	require_once(DMK_LIB_PATH . '/api_base.php');
	require_once(DMK_LIB_PATH . '/danmaku_db.php');

	class ApiQueryChannel extends ApiBase{
		
		public function __construct(){
			parent::__construct(self::API_TYPE_GET);
		}

		private $mandatory = array('channel_id');

		protected function before(){
			$this->checkField($this->mandatory, NULL);
		}

		public function process(){
			$db = DanmakuDb::getInstance();
			$result = $db->queryChannel($this->params['channel_id']);
			if($result === false){
				$this->outputCommonFailure();
			}else if($result){
				$this->outputSuccess(NULL);
			}else{
				$this->outputFailure("Channel ID does not exist.");
			}
		}

	}
?>
