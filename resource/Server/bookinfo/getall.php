<?php
	require_once ("../config.php");

	$bindParam = new BindParam();
	$qArray = array();
	$query = 'SELECT * FROM bookinfo'; 

	if ( $stmt = $db->prepare($query) ) {
		call_user_func_array( array($stmt, 'bind_param'), $bindParam->get());
		$stmt->execute();
		$stmt->bind_result($title, $author, $publisher, $bookID, $isbn);
		$stmt->fetch();
		if ( $title && $author && $publisher && $bookID && $isbn ) {
			$result = array('title' => $title,
							'author' => $author,
							'publisher' => $publisher,
							'bookID' => $bookID,
							'isbn' => $isbn );
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