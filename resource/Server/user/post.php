<?php
	require_once ("../config.php");

	$bindParam = new BindParam();
	$qArray = array();

	if ( isset($_POST['job']) && isset($_POST['id']) && isset($_POST['password']) &&
		isset($_POST['name']) && isset($_POST['address']) && isset($_POST['date_of_birth']) && isset($_POST['email']) ) {

		$query = 'INSERT INTO bookinfo(id, name, password, address, date_of_birth, email, job) VALUES(?,?,?,?,?,?,?)';
		if ( $stmt = $db->prepare($query) ) {
			$stmt->bind_param('sssssss', $_POST['id'], $_POST['name'], $_POST['password'], $_POST['address'], $_POST['date_of_birth'], $_POST['email'], $_POST['job']);
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