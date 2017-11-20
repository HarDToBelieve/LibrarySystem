<?php
	require_once ("../config.php");

	$bindParam = new BindParam();
	$qArray = array();

	if ( isset($_POST['card_number']) && isset($_POST['user_id']) && 
		isset($_POST['compensation']) ) {

		$query = 'INSERT INTO bookinfo(card_number, user_id, compensation) VALUES(?,?,?)';
		if ( $stmt = $db->prepare($query) ) {
			$stmt->bind_param('sss', $_POST['card_number'], $_POST['user_id'], $_POST['compensation']);
			$stmt->execute();
			if (mysqli_connect_errno()) {
				echo json_encode(array('status_code' => 'Failure',
									'result' => array()));
			}
			else {
				echo json_encode(array('status_code' => 'Success',
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