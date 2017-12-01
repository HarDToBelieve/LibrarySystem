<?php
	require_once ("../config.php");

	$bindParam = new BindParam();
	$qArray = array();
	$query = 'DELETE FROM user WHERE ';

	if ( isset($_GET['user_id']) ) {
		$qArray[] = 'userID = ?';
		$bindParam->add('s', $_GET['user_id']);
	}

	if ( isset($_GET['name']) ) {
		$qArray[] = 'name = ?';
		$bindParam->add('s', $_GET['name']);
	}

	if ( isset($_GET['address']) ) {
		$qArray[] = 'address = ?';
		$bindParam->add('s', $_GET['address']);
	}

	if ( isset($_GET['date_of_birth']) ) {
		$qArray[] = 'date_of_birth = ?';
		$bindParam->add('s', $_GET['date_of_birth']);
	}

	if ( isset($_GET['email']) ) {
		$qArray[] = 'email = ?';
		$bindParam->add('s', $_GET['email']);
	}

	if ( isset($_GET['job']) ) {
		$qArray[] = 'job = ?';
		$bindParam->add('s', $_GET['job']);
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