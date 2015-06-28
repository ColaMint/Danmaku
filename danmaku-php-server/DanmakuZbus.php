<?php
	class DanmakuZbus {

		private static $instance;

		private $zbus_server_address =  "http://127.0.0.1:15555";

		public function __construct($server_host,$server_port){
			$this->zbus_server_address = "http://{$server_host}:{$server_port}";
		}

		private function sendMessage($headers, $body){
			$ch = curl_init();
			
			curl_setopt($ch, CURLOPT_URL, $this->zbus_server_address);
			curl_setopt($ch, CURLOPT_RETURNTRANSFER, TRUE);
			curl_setopt($ch, CURLOPT_HEADER, TRUE);
			curl_setopt($ch, CURLOPT_HTTPHEADER, $headers);
			curl_setopt($ch, CURLOPT_POST, TRUE);
			curl_setopt($ch, CURLOPT_POSTFIELDS, $body);      
			$ret  = curl_exec($ch);
			$code = curl_getinfo($ch,CURLINFO_HTTP_CODE);
			curl_close($ch);
				 
			return 200 == $code;
		}

		public function createSubPubQueue($mq, $admin_token, $mq_access_token = ''){
			$headers = array("cmd:admin", "sub_cmd:create_mq", "token:{$admin_token}");
			$body    = array('accessToken' => $mq_access_token, 'mqName' => $mq, 'mqMode' => 2);
			$body    = json_encode($body);
			return $this->sendMessage($headers, $body);
		}

		public function sendDanmaku($mq, $mq_access_token, $topic, $userid, $username, $content, $font_size = 40, $color_r = 0, $color_g = 0, $color_b = 0, $speed = 5){
			
			$headers = array("cmd:produce", "mq:{$mq}", "token:{$mq_access_token}", "topic:{$topic}");
			
			$danmaku = array();
			$danmaku['userid'] 	  = $userid;
			$danmaku['username']  = $username;
			$danmaku['content']   = $content;
			$danmaku['font_size'] = $font_size;
			$danmaku['color_r']   = $color_r;
			$danmaku['color_g']   = $color_g;
			$danmaku['color_b']   = $color_b;
			$danmaku['speed']     = $speed;
			
			$body    = json_encode($danmaku);
			return $this->sendMessage($headers, $body);
		}
	}

?>
