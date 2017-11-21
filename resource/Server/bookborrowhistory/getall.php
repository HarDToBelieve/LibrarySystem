<?php
	require_once ("../config.php");

	$bindParam = new BindParam();
	$qArray = array();
	$query = 'SELECT * FROM bookborrowhistory'; 

	if ( $stmt = $db->prepare($query) ) {
		call_user_func_array( array($stmt, 'bind_param'), $bindParam->get());
		$stmt->execute();
		$stmt->bind_result($id, $bookID, $copyID, $card_number, $lentDate, $returnDate, $user_id);
		$result = array();
		while ( $stmt->fetch() ) {
			if ( $copyID && $card_number && $lentDate && $bookID && $returnDate && $user_id ) {
				$tmp = array('copyID' => $copyID,
								'card_number' => $card_number,
								'lentDate' => $lentDate,
								'bookID' => $bookID,
								'returnDate' => $returnDate,
								'user_id' => $user_id );
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