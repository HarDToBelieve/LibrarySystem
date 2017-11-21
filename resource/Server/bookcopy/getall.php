<?php
	require_once ("../config.php");

	$bindParam = new BindParam();
	$qArray = array();
	$query = 'SELECT * FROM bookcopy'; 

	if ( $stmt = $db->prepare($query) ) {
		call_user_func_array( array($stmt, 'bind_param'), $bindParam->get());
		$stmt->execute();
		$stmt->bind_result($id, $copyID, $bookID, $type, $price);
		$result = array();

		while ( $stmt->fetch() ) {
			if ( $copyID && $type && $price && $bookID) {
				$tmp = array('copyID' => $copyID,
								'type' => $type,
								'price' => $price,
								'bookID' => $bookID );
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