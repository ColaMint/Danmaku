<?php
	require_once(DMK_CONF_PATH . '/config.php');
	class DanmakuDbBase extends mysqli {
		
		const DB_QUERY_ONE = 1;
		const DB_QUERY_MANY= 2; 			
		const DB_INSERT    = 3; 		
		const DB_DELETE    = 4; 			
		const DB_UPDATE    = 5;

		protected function __construct(){
			$host 	= DANMAKU_CONFIG::getConfig('db_host');
			$port 	= intval(DANMAKU_CONFIG::getConfig('db_port'));
			$user 	= DANMAKU_CONFIG::getConfig('db_user');
			$pwd  	= DANMAKU_CONFIG::getConfig('db_pwd' );
			$dbname = DANMAKU_CONFIG::getConfig('db_name');
			parent::__construct($host, $user, $pwd, $dbname, $port);
			$this->query('SET NAMES utf8');
						
		}

		protected function _queryOne($sql){
			return $this->_query($sql, self::DB_QUERY_ONE);
		}

		protected function _queryAll($sql){
			return $this->_query($sql, self::DB_QUERY_ALL);
		}

		protected function _insert($sql){
			return $this->_query($sql, self::DB_INSERT);
		}

		protected function _delete($sql){
			return $this->_query($sql, self::DB_DELETE);
		}

		protected function _update($sql, $op_type){
			return $this->_query($sql, self::DB_UPDATE);
		}

		protected function _query($sql, $op_type){
			$result = $this->query($sql);
			if($result){
				switch($op_type){
				case self::DB_QUERY_ONE:{
					return $result->num_rows > 0 ? $result->fetch_assoc() : NULL;
				}break;
				case self::DB_QUERY_MANY:{
					$res = array();
					while($tmp = $result->fetch_array(MYSQLI_ASSOC)) $res[] = $tmp;
					return $res; 
				}break;
				case self::DB_INSERT:{
					return $this->insert_id;
				}break;
				case self::DB_UPDATE:
				case self::DB_DELETE:{
					return $this->affected_rows;
				}break;
				}
			}else{
				$error_mesg = $sql . ":" . $this->error;
				error_log($error_mesg);
				return false;
			}	
		}

	}

?>
