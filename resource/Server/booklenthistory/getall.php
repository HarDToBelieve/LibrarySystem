<?php
	require_once ("../config.php");

	$bindParam = new BindParam();
	$qArray = array();
	$query = 'SELECT * FROM booklenthistory'; 

	if ( $stmt = $db->prepare($query) ) {
		$stmt->execute();
		$stmt->bind_result($id, $card_number, $copyID, $date, $user_id, $is_returned);
		$result = array();

		while ( $stmt->fetch() ) {
			if ( $user_id && $card_number && $copyID && $date && is_returned ) {
				$tmp = array('user_id' => $user_id,
								'card_number' => $card_number,
								'copyID' => $copyID,
								'date' => $date,
								 'is_returned' => $is_returned );
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