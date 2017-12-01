<?php
	require_once ("../config.php");

	$bindParam = new BindParam();
	$qArray = array();
	$query = 'DELETE FROM booklenthistory WHERE ';

	if ( isset($_GET['user_id']) ) {
		$qArray[] = 'user_id = ?';
		$bindParam->add('s', $_GET['user_id']);
	}

	if ( isset($_GET['card_number']) ) {
		$qArray[] = 'card_number = ?';
		$bindParam->add('s', $_GET['card_number']);
	}

	if ( isset($_GET['copyID']) ) {
		$qArray[] = 'copyID = ?';
		$bindParam->add('s', $_GET['copyID']);
	}

	if ( isset($_GET['date']) ) {
		$qArray[] = 'date = ?';
		$bindParam->add('s', $_GET['date']);
	}

	$query .= implode(' AND ', $qArray);
	if ( $stmt = $db->prepare($query) ) {
		call_user_func_array( array($stmt, 'bind_param'), $bindParam->get());
		if ( $stmt->execute() ) {
        		    echo json_encode(array('status_code' => 'Success',
        			    							'result' => array()));
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