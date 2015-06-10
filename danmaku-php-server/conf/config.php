<?php
	class DANMAKU_CONFIG{

		private static $config = array(
			'db_host' => '127.0.0.1',
			'db_port' => '3306',
			'db_user' => 'root',
			'db_pwd'  => '03545328',
			'db_name' => 'danmaku',
		);

		public static function getConfig($key, $default = NULL){
			if(isset(DANMAKU_CONFIG::$config[$key])){
				return DANMAKU_CONFIG::$config[$key];
			}else{
				return $default;
			}
		}

	}

?>
