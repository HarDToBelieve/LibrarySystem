<?php
	require_once ("../config.php");

	$bindParam = new BindParam();
	$qArray = array();

	if ( isset($_POST['title']) && isset($_POST['author']) && 
		isset($_POST['publisher']) && isset($_POST['bookID']) && isset($_POST['isbn']) ) {

		$query = 'INSERT INTO bookinfo(title, author, publisher, bookID, isbn) VALUES(?,?,?,?,?)';
		if ( $stmt = $db->prepare($query) ) {
			$stmt->bind_param('sssss', $_POST['title'], $_POST['author'], $_POST['publisher'], $_POST['bookID'], $_POST['isbn']);
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