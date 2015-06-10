<?php
	require_once(DMK_LIB_PATH . '/danmaku_db_base.php');
	class DanmakuDb extends DanmakuDbBase{
		
		private static $db;

		protected function __construct(){
			parent::__construct();
		}

		public static function getInstance(){
			if(DanmakuDb::$db == NULL){
				DanmakuDb::$db = new DanmakuDb();
			}
			return DanmakuDb::$db;
		}	

		public function add($userid, $username, $content, $font_size = 40, $color_r = 0, $color_g = 0, $color_b = 0, $speed = 5){
			$userid = trim($userid);
			$username = trim($username);
			$content = trim($content);
			if($font_size < 0) $font_size = 40;
			if($color_r < 0 || $color_r > 255) $color_r = 0;
			if($color_g < 0 || $color_g > 255) $color_g = 0;
			if($color_b < 0 || $color_b > 255) $color_b = 0;
			if($speed < 0) $speed = 5;

			$sql = "INSERT INTO `dmk_danmaku` (`userid`, `username`, `content`, `font_size`, `color_r`, `color_g`, `color_b`, `speed`) 
					VALUES ('$userid', '$username', '$content', '$font_size', '$color_r', '$color_g', '$color_b', '$speed')";
			return $this->_query($sql, self::DB_INSERT);
		}

		public function getLatestID(){
			$sql = 'SELECT MAX(`id`) AS `id` FROM `dmk_danmaku`';
		    $result = $this->_query($sql, self::DB_QUERY_ONE);
			return $result === false ? 0 : $result['id'];	
		}

		public function fetch($latest_id, $return_num = 50){
			$sql = "SELECT * FROM `dmk_danmaku` WHERE `id` > $latest_id ORDER BY `id` ASC";
			return $this->_query($sql, self::DB_QUERY_MANY);
		}


	}

