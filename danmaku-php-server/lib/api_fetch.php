<?php
	require_once(DMK_LIB_PATH . '/api_base.php');
	require_once(DMK_LIB_PATH . '/danmaku_db.php');
	
	class ApiFetch extends ApiBase{
		
		public function __construct(){
			parent::__construct(self::API_TYPE_GET);
		}

		private $mandatory = array('latest_id');

		private $notMandatory = array('max_num' => 50);

		protected function before(){
			$this->checkField($this->mandatory, $this->notMandatory);
		}

		protected function process(){
			$db = DanmakuDb::getInstance();
			$result = $db->fetch($this->params['latest_id'], $this->params['max_num']);
			
			if($result){
				$this->outputSuccess($result);
			}else{
				$this->outputFailure("Try again or check the param's value you submitted.");
			}	
		}
	}
?>
