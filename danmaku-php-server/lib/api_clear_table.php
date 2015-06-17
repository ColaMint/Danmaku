<?php
	require_once(DMK_LIB_PATH . "/api_base.php");
	require_once(DMK_LIB_PATH . "/danmaku_db.php");

	class ApiClearTable extends ApiBase {
			
		public function __construct(){
			parent::__construct(self::API_TYPE_GET);
		}	

		private $mandatory = array('channel_id');

		protected function before(){
			$this->checkField($this->mandatory, NULL);
		}

		protected function process(){
			$db = DanmakuDb::getInstance();
			$result = $db->clearTable($this->params['channel_id']);

			if($result !== false){
				$this->outputSuccess(NULL);
			}else{
				$this->outputCommonFailure();
			}
		}
	}
?>
