<?php
	require_once ("../config.php");

	$bindParam = new BindParam();
	$qArray = array();
	$query = 'DELETE FROM bookborrowhistory WHERE ';

	if ( isset($_GET['copyID']) ) {
		$qArray[] = 'copyID = ?';
		$bindParam->add('s', $_GET['copyID']);
	}

	if ( isset($_GET['card_number']) ) {
		$qArray[] = 'card_number = ?';
		$bindParam->add('s', $_GET['card_number']);
	}

	if ( isset($_GET['lentDate']) ) {
		$qArray[] = 'lentDate = ?';
		$bindParam->add('s', $_GET['lentDate']);
	}

	if ( isset($_GET['bookID']) ) {
		$qArray[] = 'bookID = ?';
		$bindParam->add('s', $_GET['bookID']);
	}

	if ( isset($_GET['returnDate']) ) {
		$qArray[] = 'returnDate = ?';
		$bindParam->add('s', $_GET['returnDate']);
	}

	if ( isset($_GET['user_id']) ) {
		$qArray[] = 'user_id = ?';
		$bindParam->add('s', $_GET['user_id']);
	}

	$query .= implode(' AND ', $qArray);
	if ( $stmt = $db->prepare($query) ) {
		call_user_func_array( array($stmt, 'bind_param'), $bindParam->get());
		if ( $stmt->execute() ) {
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