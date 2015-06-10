<?php
	require_once(DMK_LIB_PATH . '/api_base.php');
	require_once(DMK_LIB_PATH . '/danmaku_db.php');

	class ApiAdd extends ApiBase{
		
		public function __construct(){
			parent::__construct(self::API_TYPE_POST);
		}

		private $mandatory = array(	'userid',
									'username',
		   							'content');

		private $notMandatory = array(	'font_size'	=> 40,
										'color_r' 	=> 0,
										'color_g' 	=> 0,
										'color_b' 	=> 0,
										'speed'   	=> 5);

		protected function before(){
			$this->checkField($this->mandatory, $this->notMandatory);
		}

		protected function process(){
			
			$db = DanmakuDb::getInstance();
			$result = $db->add(	$this->params['userid'], $this->params['username'], $this->params['content'], 
								$this->params['font_size'], $this->params['color_r'], $this->params['color_g'], $this->params['color_b'], $this->params['speed']);
			if($result){
				$this->outputSuccess(NULL);
			}else{
				$this->outputFailure("Try again or check the param's value you submitted.");
			}
		}
	}

?>
