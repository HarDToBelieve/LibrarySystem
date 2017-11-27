<?php
	require_once ("../config.php");

	$bindParam = new BindParam();
	$qArray = array();
	$query = 'SELECT * FROM booklenthistory WHERE '; 

	if ( isset($_GET['user_id']) ) {
		$qArray[] = 'user_id = ?';
		$bindParam->add('s', $_GET['user_id']);
	}

	if ( isset($_GET['card_number']) ) {
		$qArray[] = 'card_number = ?';
		$bindParam->add('s', $_GET['card_number']);
	}

	if ( isset($_GET['copyID']) ) {
		$qArray[] = 'copyID = ?';
		$bindParam->add('s', $_GET['copyID']);
	}

	if ( isset($_GET['date']) ) {
		$qArray[] = 'date = ?';
		$bindParam->add('s', $_GET['date']);
	}

    if ( isset($_GET['is_returned']) ) {
        $qArray[] = 'is_returned = ?';
        $bindParam->add('s', $_GET['is_returned']);
    }

	$query .= implode(' AND ', $qArray);
	if ( $stmt = $db->prepare($query) ) {
		call_user_func_array( array($stmt, 'bind_param'), $bindParam->get());
		$stmt->execute();
		$stmt->bind_result($id, $card_number, $copyID, $date, $user_id, $is_returned);
		$result = array();

		while ( $stmt->fetch() ) {
			if ( $user_id && $card_number && $copyID && $date && is_returned ) {
				$tmp = array('user_id' => $user_id,
								'card_number' => $card_number,
								'copyID' => $copyID,
								'date' => $date,
								'is_returned' => $is_returned );
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