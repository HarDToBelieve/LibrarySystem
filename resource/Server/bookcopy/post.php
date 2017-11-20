<?php
	require_once ("../config.php");

	$bindParam = new BindParam();
	$qArray = array();

	if ( isset($_POST['copyID']) && isset($_POST['type']) && 
		isset($_POST['price']) && isset($_POST['bookID']) ) {

		$query = 'INSERT INTO bookinfo(copyID, type, price, bookID) VALUES(?,?,?,?)';
		if ( $stmt = $db->prepare($query) ) {
			$stmt->bind_param('ssds', $_POST['copyID'], $_POST['type'], $_POST['price'], $_POST['bookID']);
			$stmt->execute();
			if (mysqli_connect_errno()) {
				echo json_encode(array('status_code' => 'Failure',
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