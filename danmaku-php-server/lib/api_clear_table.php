<?php
	require_once(DMK_LIB_PATH . "/api_base.php");
	require_once(DMK_LIB_PATH . "/danmaku_db.php");

	class ApiClearTable extends ApiBase {
		
		public function __construct(){
			parent::__construct(self::API_TYPE_POST);
		}

		protected function process(){
			$db = DanmakuDb::getInstance();
			$result = $db->clearTable();

			if($result !== false){
				$this->outputSuccess(NULL);
			}else{
				$this->outputFailure("Try again.");
			}
		}
	}
?>
