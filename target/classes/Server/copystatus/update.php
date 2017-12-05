<?php
	require_once ("../config.php");

	$bindParam = new BindParam();
	$qArray = array();

	if ( isset($_POST['copyID']) && isset($_POST['status']) ) {

		$query = 'UPDATE copystatus SET status=? WHERE copyID=?';
		if ( $stmt = $db->prepare($query) ) {
			$stmt->bind_param('ss', $_POST['status'], $_POST['copyID']);
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