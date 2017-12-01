<?php
	require_once ("../config.php");

	$bindParam = new BindParam();
	$qArray = array();

	if ( isset($_POST['card_number']) && isset($_POST['user_id']) && 
		isset($_POST['copyID']) && isset($_POST['date']) && isset($_POST['is_returned']) ) {

		$query = 'INSERT INTO bookinfo(card_number, user_id, copyID, date, is_returned) VALUES(?,?,?,?,?)';
		if ( $stmt = $db->prepare($query) ) {
			$stmt->bind_param('sssss', $_POST['card_number'], $_POST['user_id'], $_POST['copyID'], $_POST['date'], $_POST['is_returned']);
			if ( $stmt->execute() ) {
            		    echo json_encode(array('status_code' => 'Success',
            			    							'result' => $result));
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