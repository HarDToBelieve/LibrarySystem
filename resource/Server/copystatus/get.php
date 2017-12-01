<?php
	require_once ("../config.php");

	$bindParam = new BindParam();
	$qArray = array();
	$query = 'SELECT * FROM copystatus WHERE ';

	if ( isset($_GET['copyID']) ) {
		$qArray[] = 'copyID = ?';
		$bindParam->add('s', $_GET['copyID']);
	}

	if ( isset($_GET['title']) ) {
		$qArray[] = 'title = ?';
		$bindParam->add('s', $_GET['title']);
	}

	if ( isset($_GET['status']) ) {
		$qArray[] = 'status = ?';
		$bindParam->add('s', $_GET['status']);
	}

	$query .= implode(' AND ', $qArray);
	if ( $stmt = $db->prepare($query) ) {
		call_user_func_array( array($stmt, 'bind_param'), $bindParam->get());
		$stmt->execute();
		$stmt->bind_result($id, $copyID, $title, $status);
		$result = array();
		
		while ( $stmt->fetch() ) {
			if ( $copyID && $title && $status ) {
				$tmp = array(	'copyID' => $copyID,
								'title' => $title,
								'status' => $status );
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