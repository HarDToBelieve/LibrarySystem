<?php
	require_once ("../config.php");

	$bindParam = new BindParam();
	$qArray = array();

	if ( isset($_POST['copyID']) && isset($_POST['type']) && 
		isset($_POST['price']) && isset($_POST['bookID']) ) {

		$query = 'INSERT INTO bookcopy(copyID, type, price, bookID) VALUES(?,?,?,?)';
		if ( $stmt = $db->prepare($query) ) {
			$stmt->bind_param('ssss', $_POST['copyID'], $_POST['type'], $_POST['price'], $_POST['bookID']);
			if ( $stmt->execute() ) {
            		    echo json_encode(array('status_code' => 'Success',
            			    							'result' => array()));
            		}
            		else {
            		    echo json_encode(array('status_code' => 'Failure',
                        									'result' => array()));
            		}
		}
		else {
			echo json_encode(array('status_code' => 'Failure',
									'result' => array()));
		}
	}
	else {
		echo json_encode(array('status_code' => 'Failure',
									'result' => array()));
	}