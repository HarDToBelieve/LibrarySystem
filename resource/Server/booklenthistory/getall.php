<?php
	require_once ("../config.php");

	$bindParam = new BindParam();
	$qArray = array();
	$query = 'SELECT * FROM booklenthistory'; 

	if ( $stmt = $db->prepare($query) ) {
		$stmt->execute();
		$stmt->bind_result($id, $card_number, $copyID, $date, $user_id);
		$result = array();

		while ( $stmt->fetch() ) {
			if ( $user_id && $card_number && $copyID && $date ) {
				$tmp = array('user_id' => $user_id,
								'card_number' => $card_number,
								'copyID' => $copyID,
								'email' => $email );
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