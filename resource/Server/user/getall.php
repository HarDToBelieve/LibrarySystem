<?php
	require_once ("../config.php");

	$bindParam = new BindParam();
	$qArray = array();
	$query = 'SELECT * FROM bookborrowhistory'; 

	if ( $stmt = $db->prepare($query) ) {
		$stmt->execute();
		$stmt->bind_result($user_id, $name, $address, $date_of_birth, $email, $job);
		$result = array();
		
		while ( $stmt->fetch() ) {
			if ( $user_id && $name && $address && $date_of_birth && $email ) {
				$tmp = array('job' => $job,
								'user_id' => $user_id,
								'name' => $name,
								'address' => $address,
								'date_of_birth' => $date_of_birth,
								'email' => $email );
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