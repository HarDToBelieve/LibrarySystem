<?php
	require_once ("../config.php");

	$bindParam = new BindParam();
	$qArray = array();
	$query = 'SELECT * FROM bookinfo'; 

	if ( $stmt = $db->prepare($query) ) {
		$stmt->execute();
		$stmt->bind_result($id, $bookID, $title, $author, $publisher, $isbn);
		$result = array();
		while ( $stmt->fetch() ) {
			if ( $title && $author && $publisher && $bookID && $isbn ) {
				$tmp = array('title' => $title,
								'author' => $author,
								'publisher' => $publisher,
								'bookID' => $bookID,
								'isbn' => $isbn );
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