<?php
	require_once ("../config.php");

	$bindParam = new BindParam();
	$qArray = array();
	$query = 'SELECT * FROM bookcopy WHERE '; 

	if ( isset($_GET['copyID']) ) {
		$qArray[] = 'copyID = ?';
		$bindParam->add('s', $_GET['copyID']);
	}

	if ( isset($_GET['type']) ) {
		$qArray[] = 'type = ?';
		$bindParam->add('s', $_GET['type']);
	}

	if ( isset($_GET['price']) ) {
		$qArray[] = 'price = ?';
		$bindParam->add('d', $_GET['price']);
	}

	if ( isset($_GET['bookID']) ) {
		$qArray[] = 'bookID = ?';
		$bindParam->add('s', $_GET['bookID']);
	}

	$query .= implode(' AND ', $qArray);
	if ( $stmt = $db->prepare($query) ) {
		call_user_func_array( array($stmt, 'bind_param'), $bindParam->get());
		$stmt->execute();
		$stmt->bind_result($copyID, $type, $price, $bookID);
		$stmt->fetch();
		if ( $copyID && $type && $price && $bookID) {
			$result = array('copyID' => $copyID,
							'type' => $type,
							'price' => $price,
							'bookID' => $bookID );
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