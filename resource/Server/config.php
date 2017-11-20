<?php
	error_reporting(E_ALL);
    ini_set("display_errors", 1);

    class BindParam {
		private $values = array(), $types = ''; 
		
		public function add( $type, &$value ) {
			$this->values[] = $value;
			$this->types .= $type;
		}

		public function get() {
			return array_merge(array($this->types), $this->values);
		}
	}
	
    $db = mysqli_connect("localhost", "itss", "r0gbCXc!l351", "LibrarySystem");
    if ( mysqli_connect_errno() ) {
        die ("Failed to connect to MySQL: " . mysqli_connect_error());
    }