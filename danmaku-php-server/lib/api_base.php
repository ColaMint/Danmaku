<?php
	abstract class ApiBase {
		
		const API_TYPE_GET  = 0;
		const API_TYPE_POST = 1;
		const API_TYPE_GET_OR_POST = 2;

		protected $params;

		public function __construct($api_type = self::API_TYPE_GET_OR_POST){
			
			$method = $_SERVER['REQUEST_METHOD'];

			if($method == 'POST' && $api_type == self::API_TYPE_GET){
				$this->handleException('This is a GET api.');
			}else if($method == 'GET' && $api_type == self::API_TYPE_POST){
				$this->handleException('This is a POST api.');
			}

			$this->params = $_REQUEST;
		}

		public function run(){
			
			$this->before();

			$this->process();

			$this->after();

			if (function_exists('fastcgi_finish_request')) {
				fastcgi_finish_request();
			}

			$this->asyn();
		}

		protected function checkField($mandatory, $notMandatory){

			if(is_array($mandatory)){	
				foreach($mandatory as $field){
					if(!isset($this->params[$field])){
						$this->handleException("Missing field '$field'.");
					}else{
						$this->params[$field] = trim($this->params[$field]);
					}
				}
			}

			if(is_array($notMandatory)){	
				foreach($notMandatory as $field => $defaultVal){
					$this->params[$field] = isset($this->params[$field]) ? trim($this->params[$filed]) : $defaultVal;
				}
			}
		}

		protected function before(){

		}

		protected function process(){

		}

		protected function after(){

		}

		protected function asyn(){

		}

		protected function outputSuccess($data){
			$output = array('success' => 1, 'data' => $data);
			echo json_encode($output);
		}

		protected function outputFailure($message, $code = -1){
			$output = array('success' => 0, 'code' => $code, 'message' => $message);
			echo json_encode($output);
		}

		protected function handleException($message, $code = -1){
			
			$output	= array('success' => 0, 'code' => $code, 'message' => $message);
			echo json_encode($output);
			
			fastcgi_finish_request();
			
			exit();
		}
	}
?>
