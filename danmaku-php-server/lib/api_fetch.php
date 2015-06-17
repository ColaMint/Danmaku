<?php
	require_once(DMK_LIB_PATH . '/api_base.php');
	require_once(DMK_LIB_PATH . '/danmaku_db.php');
	
	class ApiFetch extends ApiBase{
		
		public function __construct(){
			parent::__construct(self::API_TYPE_GET);
		}

		private $mandatory = array('latest_id' ,'channel_id');

		private $notMandatory = array('max_num' => 50);

		protected function before(){
			$this->checkField($this->mandatory, $this->notMandatory);
		}

		protected function process(){
			$db = DanmakuDb::getInstance();
			$result = $db->fetch($this->params['channel_id'], $this->params['latest_id'], $this->params['max_num']);
			if($result !== false){
				$this->outputSuccess($result);
			}else{
				$this->outputCommonFailure();
			}	
		}
	}
?>
