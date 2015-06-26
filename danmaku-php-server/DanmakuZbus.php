<?php
	class DanmakuZbus {

		private static $instance;

		private $zbus_host 	= "127.0.0.1";
		private $zbus_port 	= "15555";
		private $zbus_mq   	= "danmaku";
		private $zbus_topic	= "danmaku";
		private $zbus_cmd   = "produce";

		private function __construct(){
			
		}

		public function getInstance(){
			if(self::$instance == NULL){
				self::$instance = new DanmakuZbus();
			}
			return self::$instance;
		}

		public function sendDanmaku($userid, $username, $content, $font_size = 40, $color_r = 5, $color_g = 0, $color_b = 0, $speed = 5){
			
			$ch = curl_init();

			curl_setopt($ch, CURLOPT_URL,"http://" . $this->zbus_host . ":" . $this->zbus_port);
			curl_setopt($ch, CURLOPT_RETURNTRANSFER, TRUE);
			curl_setopt($ch, CURLOPT_HEADER, TRUE);
			curl_setopt($ch, CURLOPT_HTTPHEADER, array('cmd:' . $this->zbus_cmd, 'mq:' . $this->zbus_mq, 'topic:' . $this->zbus_topic));
			curl_setopt($ch, CURLOPT_POST, TRUE);
			curl_setopt($ch, CURLOPT_POSTFIELDS, $this->buildJSON($userid, $username, $content, $font_size, $color_r, $color_g, $color_b, $speed));

			$result = curl_exec($ch);
			$code = curl_getinfo($ch,CURLINFO_HTTP_CODE);
			curl_close($ch);
			
			return 200 == $code;
		}

		private function buildJSON($userid, $username, $content, $font_size, $color_r, $color_g, $color_b, $speed){
			$danmaku = array();
			$danmaku['userid'] 	  = $userid;
			$danmaku['username']  = $username;
			$danmaku['content']   = $content;
			$danmaku['font_size'] = $font_size;
			$danmaku['color_r']   = $color_r;
			$danmaku['color_g']   = $color_g;
			$danmaku['color_b']   = $color_b;
			$danmaku['speed']     = $speed;
			return json_encode($danmaku);
		}
	}

?>
