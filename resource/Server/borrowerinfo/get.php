<?php
	require_once ("../config.php");

	$bindParam = new BindParam();
	$qArray = array();
	$query = 'SELECT * FROM borrowerinfo WHERE '; 

	if ( isset($_GET['card_number']) ) {
		$qArray[] = 'card_number = ?';
		$bindParam->add('s', $_GET['card_number']);
	}

	if ( isset($_GET['compensation']) ) {
		$qArray[] = 'compensation = ?';
		$bindParam->add('s', $_GET['compensation']);
	}

	if ( isset($_GET['user_id']) ) {
		$qArray[] = 'user_id = ?';
		$bindParam->add('s', $_GET['user_id']);
	}

	$query .= implode(' AND ', $qArray);
	if ( $stmt = $db->prepare($query) ) {
		call_user_func_array( array($stmt, 'bind_param'), $bindParam->get());
		$stmt->execute();
		$stmt->bind_result($job, $card_number, $compensation, $user_id);
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