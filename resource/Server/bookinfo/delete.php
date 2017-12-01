<?php
	require_once ("../config.php");

	$bindParam = new BindParam();
	$qArray = array();
	$query = 'DELETE FROM bookinfo WHERE ';

	if ( isset($_GET['title']) ) {
		$qArray[] = 'title = ?';
		$bindParam->add('s', $_GET['title']);
	}

	if ( isset($_GET['author']) ) {
		$qArray[] = 'author = ?';
		$bindParam->add('s', $_GET['author']);
	}

	if ( isset($_GET['publisher']) ) {
		$qArray[] = 'publisher = ?';
		$bindParam->add('s', $_GET['publisher']);
	}

	if ( isset($_GET['bookID']) ) {
		$qArray[] = 'bookID = ?';
		$bindParam->add('s', $_GET['bookID']);
	}

	if ( isset($_GET['isbn']) ) {
		$qArray[] = 'isbn = ?';
		$bindParam->add('s', $_GET['isbn']);
	}

	$query .= implode(' AND ', $qArray);
	if ( $stmt = $db->prepare($query) ) {
		call_user_func_array( array($stmt, 'bind_param'), $bindParam->get());
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