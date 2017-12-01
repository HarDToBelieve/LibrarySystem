<?php
	require_once ("../config.php");

	$bindParam = new BindParam();
	$qArray = array();

	if ( isset($_POST['copyID']) && isset($_POST['card_number']) && 
		isset($_POST['lentDate']) && isset($_POST['bookID']) && isset($_POST['returnDate']) && isset($_POST['user_id']) ) {

		$query = 'INSERT INTO bookborrowhistory(copyID, card_number, lentDate, bookID, returnDate, user_id) VALUES(?,?,?,?,?,?)';
		if ( $stmt = $db->prepare($query) ) {
			$stmt->bind_param('ssssss', $_POST['copyID'], $_POST['card_number'], $_POST['lentDate'], $_POST['bookID'], $_POST['returnDate'], $_POST['user_id']);
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
	}
	else {
		echo json_encode(array('status_code' => 'Failure',
									'result' => array()));
	}