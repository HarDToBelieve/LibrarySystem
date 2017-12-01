<?php
	require_once ("../config.php");

	$bindParam = new BindParam();
	$qArray = array();

	if ( isset($_POST['copyID']) && isset($_POST['title']) && isset($_POST['status']) ) {

		$query = 'INSERT INTO copystatus(copyID, title, status) VALUES(?,?,?)';
		if ( $stmt = $db->prepare($query) ) {
			$stmt->bind_param('sss', $_POST['copyID'], $_POST['title'], $_POST['status']);
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
	}
	else {
		echo json_encode(array('status_code' => 'Failure',
									'result' => array()));
	}