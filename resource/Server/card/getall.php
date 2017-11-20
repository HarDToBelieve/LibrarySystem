<?php
	require_once ("../config.php");

	$bindParam = new BindParam();
	$qArray = array();
	$query = 'SELECT * FROM card'; 


	$query .= implode(' AND ', $qArray);
	if ( $stmt = $db->prepare($query) ) {
		call_user_func_array( array($stmt, 'bind_param'), $bindParam->get());
		$stmt->execute();
		$stmt->bind_result($card_number, $user_id, $is_active, $is_student, $expired_date, $activate_code);
		$stmt->fetch();
		if ( $card_number && $user_id && $is_active && $is_student && $expired_date && $activate_code ) {
			$result = array('card_number' => $card_number,
							'user_id' => $user_id,
							'is_active' => $is_active,
							'is_student' => $is_student,
							'expired_date' => $expired_date,
							'activate_code' => $activate_code );
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