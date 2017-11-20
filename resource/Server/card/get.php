<?php
	require_once ("../config.php");

	$bindParam = new BindParam();
	$qArray = array();
	$query = 'SELECT * FROM card WHERE '; 

	if ( isset($_GET['card_number']) ) {
		$qArray[] = 'card_number = ?';
		$bindParam->add('s', $_GET['card_number']);
	}

	if ( isset($_GET['user_id']) ) {
		$qArray[] = 'user_id = ?';
		$bindParam->add('s', $_GET['user_id']);
	}

	if ( isset($_GET['is_active']) ) {
		$qArray[] = 'is_active = ?';
		$bindParam->add('d', $_GET['is_active']);
	}

	if ( isset($_GET['is_student']) ) {
		$qArray[] = 'is_student = ?';
		$bindParam->add('d', $_GET['is_student']);
	}

	if ( isset($_GET['expired_date']) ) {
		$qArray[] = 'expired_date = ?';
		$bindParam->add('s', $_GET['expired_date']);
	}

	if ( isset($_GET['activate_code']) ) {
		$qArray[] = 'activate_code = ?';
		$bindParam->add('s', $_GET['activate_code']);
	}

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