<?php
	require_once ("../config.php");

	$bindParam = new BindParam();
	$qArray = array();
	$query = 'SELECT * FROM card'; 

	if ( $stmt = $db->prepare($query) ) {
		$stmt->execute();
		$stmt->bind_result($id, $card_number, $user_id, $is_active, $is_student, $expired_date, $activate_code);
		$result = array();

		while ( $stmt->fetch() ) {
			if ( $card_number && $user_id && $is_active && $is_student && $expired_date && $activate_code ) {
				$tmp = array('card_number' => $card_number,
								'user_id' => $user_id,
								'is_active' => $is_active,
								'is_student' => $is_student,
								'expired_date' => $expired_date,
								'activate_code' => $activate_code );
				$result[] = $tmp;
			}
		}
		echo json_encode(array('status_code' => 'Success',
										'result' => $result));
	}
	else {
		echo json_encode(array('status_code' => 'Failure',
									'result' => array()));
	}