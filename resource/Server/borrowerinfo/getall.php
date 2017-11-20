<?php
	require_once ("../config.php");

	$bindParam = new BindParam();
	$qArray = array();
	$query = 'SELECT * FROM borrowerinfo'; 

	if ( $stmt = $db->prepare($query) ) {
		call_user_func_array( array($stmt, 'bind_param'), $bindParam->get());
		$stmt->execute();
		$stmt->bind_result($job, $card_number, $compensation, $user_id);
		$stmt->fetch();
		if ( $card_number && $compensation && $user_id ) {
			$result = array('card_number' => $card_number,
							'compensation' => $compensation,
							'user_id' => $user_id );
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