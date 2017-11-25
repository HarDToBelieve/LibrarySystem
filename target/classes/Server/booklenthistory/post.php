<?php
	require_once ("../config.php");

	$bindParam = new BindParam();
	$qArray = array();

	if ( isset($_POST['card_number']) && isset($_POST['user_id']) && 
		isset($_POST['copyID']) && isset($_POST['date']) ) {

		$query = 'INSERT INTO bookinfo(card_number, user_id, copyID, date) VALUES(?,?,?,?)';
		if ( $stmt = $db->prepare($query) ) {
			$stmt->bind_param('ssddss', $_POST['card_number'], $_POST['user_id'], $_POST['copyID'], $_POST['date']);
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