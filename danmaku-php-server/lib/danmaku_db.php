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

		public function createChannel($channel_name){
			$channel_name = trim($trim_name);

			$sql = "INSERT INTO `dmk_channel` (`channel_name`) VALUES ('$channel_name')";
			return $this->_insert($sql);
		}

		public function queryChannel($channel_id){
			$sql = "SELECT * FROM `dmk_channel` WHERE `id` = '$channel_id' LIMIT 1";
			
			return $this->_queryOne($sql);
		}

		public function add($userid, $channel_id, $username, $content, $font_size = 40, $color_r = 0, $color_g = 0, $color_b = 0, $speed = 5){
			$userid = trim($userid);
			$username = trim($username);
			$content = trim($content);
			if($font_size < 0) $font_size = 40;
			if($color_r < 0 || $color_r > 255) $color_r = 0;
			if($color_g < 0 || $color_g > 255) $color_g = 0;
			if($color_b < 0 || $color_b > 255) $color_b = 0;
			if($speed < 0) $speed = 5;

			$sql = "INSERT INTO `dmk_danmaku` (`userid`, `channel_id`, `username`, `content`, `font_size`, `color_r`, `color_g`, `color_b`, `speed`) 
					VALUES ('$userid', '$channel_id', '$username', '$content', '$font_size', '$color_r', '$color_g', '$color_b', '$speed')";
			return $this->_insert($sql);
		}

		public function getLatestID($channel_id){
			$sql = "SELECT MAX(`id`) AS `id` FROM `dmk_danmaku` WHERE `channel_id` = '$channel_id'";
		    $result = $this->_queryOne($sql);
			return $result === false ? 0 : $result['id'];	
		}

		public function fetch($channel_name, $latest_id, $return_num = 50){
			$sql = "SELECT * FROM `dmk_danmaku` WHERE `channel_id` = '$channel_id' AND `id` > $latest_id ORDER BY `id` ASC LIMIT $return_num";
			return $this->_queryAll($sql);
		}

		public function clearTable(){
			$sql = "DELETE FROM `dmk_danmaku` WHERE 1";
			return $this->_delete($sql);
		}

	}
