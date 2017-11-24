<?php
	require_once ("../config.php");

	$bindParam = new BindParam();
	$qArray = array();

	if ( isset($_POST['job']) && isset($_POST['user_id']) && 
		isset($_POST['name']) && isset($_POST['address']) && isset($_POST['date_of_birth']) && isset($_POST['email']) ) {

		$query = 'INSERT INTO bookinfo(job, user_id, name, address, date_of_birth, email) VALUES(?,?,?,?,?,?)';
		if ( $stmt = $db->prepare($query) ) {
			$stmt->bind_param('ssssss', $_POST['job'], $_POST['user_id'], $_POST['name'], $_POST['address'], $_POST['date_of_birth'], $_POST['email']);
			$stmt->execute();
			if (mysqli_connect_errno()) {
				echo json_encode(array('status_code' => 'Failure',
									'result' => array()));
			}
			else {
				echo json_encode(array('status_code' => 'Success',
									'result' => array()));
			}
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