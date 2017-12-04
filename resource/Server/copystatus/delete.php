<?php
	require_once ("../config.php");

	$bindParam = new BindParam();
	$qArray = array();
	$query = 'DELETE FROM copystatus WHERE ';

	if ( isset($_GET['copyID']) ) {
		$qArray[] = 'copyID = ?';
		$bindParam->add('s', $_GET['copyID']);
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