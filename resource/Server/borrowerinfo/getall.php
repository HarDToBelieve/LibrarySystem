<?php
	require_once ("../config.php");

	$bindParam = new BindParam();
	$qArray = array();
	$query = 'SELECT * FROM borrowerinfo'; 

	if ( $stmt = $db->prepare($query) ) {
		call_user_func_array( array($stmt, 'bind_param'), $bindParam->get());
		$stmt->execute();
		$stmt->bind_result($id, $card_number, $compensation, $user_id);
		$result = array();

		while ( $stmt->fetch() ) {
			if ( $card_number && $compensation && $user_id ) {
				$tmp = array('card_number' => $card_number,
								'compensation' => $compensation,
								'user_id' => $user_id );
	
			}
			$result[] = $tmp;
		}
		echo json_encode(array('status_code' => 'Success',
										'result' => $result));
	}
	else {
		echo json_encode(array('status_code' => 'Failure',
									'result' => array()));
	}