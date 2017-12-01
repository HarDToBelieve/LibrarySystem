<?php
	require_once ("../config.php");

	$bindParam = new BindParam();
	$qArray = array();

	if ( isset($_POST['card_number']) && isset($_POST['user_id']) && 
		isset($_POST['is_active']) && isset($_POST['is_student']) && isset($_POST['expired_date']) && isset($_POST['activate_code']) ) {

		$query = 'INSERT INTO bookinfo(card_number, user_id, is_active, is_student, expired_date, activate_code) VALUES(?,?,?,?,?,?)';
		if ( $stmt = $db->prepare($query) ) {
			$stmt->bind_param('ssddss', $_POST['card_number'], $_POST['user_id'], $_POST['is_active'], $_POST['is_student'], $_POST['expired_date'], $_POST['activate_code']);
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