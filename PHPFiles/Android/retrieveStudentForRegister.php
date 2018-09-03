<?php
require "conn.php";

$TimeTableID = $_POST['timeTableID'];
$StudentID = $_POST['studentID'];
$StudentID2 = $_POST['studentID2'];
$StudentID3 = $_POST['studentID3'];
$StudentID4 = $_POST['studentID4'];
$StudentID5 = $_POST['studentID5'];


$mysql_qry1 = "select * from studentsimple where StudentID like '$StudentID';";
$mysql_qry2 = "select * from studentsimple where StudentID like '$StudentID2';";
$mysql_qry3 = "select * from studentsimple where StudentID like '$StudentID3';";
$mysql_qry4 = "select * from studentsimple where StudentID like '$StudentID4';";
$mysql_qry5 = "select * from studentsimple where StudentID like '$StudentID5';";

$mysql_qryExist1 = "select * from eventregistration where StudentID like '$StudentID' AND status like 'Active' AND TimetableId like '$TimeTableID';";
$mysql_qryExist2 = "select * from eventregistration where StudentID like '$StudentID2' AND status like 'Active' AND TimetableId like '$TimeTableID';";
$mysql_qryExist3 = "select * from eventregistration where StudentID like '$StudentID3' AND status like 'Active' AND TimetableId like '$TimeTableID';";
$mysql_qryExist4 = "select * from eventregistration where StudentID like '$StudentID4' AND status like 'Active' AND TimetableId like '$TimeTableID';";
$mysql_qryExist5 = "select * from eventregistration where StudentID like '$StudentID5' AND status like 'Active' AND TimetableId like '$TimeTableID';";

$result1 = mysqli_query($conn, $mysql_qry1);
$result2 = mysqli_query($conn, $mysql_qry2);
$result3 = mysqli_query($conn, $mysql_qry3);
$result4 = mysqli_query($conn, $mysql_qry4);
$result5 = mysqli_query($conn, $mysql_qry5);

$resultExist1 = mysqli_query($conn, $mysql_qryExist1);
$resultExist2 = mysqli_query($conn, $mysql_qryExist2);
$resultExist3 = mysqli_query($conn, $mysql_qryExist3);
$resultExist4 = mysqli_query($conn, $mysql_qryExist4);
$resultExist5 = mysqli_query($conn, $mysql_qryExist5);



if($StudentID2 != "" AND $StudentID3 != "" AND $StudentID4 != "" AND $StudentID5 != ""){
	if(mysqli_num_rows($result1) > 0){
		if(mysqli_num_rows($resultExist1) > 0){
			$response = "failExist1";
		} else {
			if(mysqli_num_rows($result2) > 0){
				if(mysqli_num_rows($resultExist2) > 0){
					$response = "failExist2";
				} else {
					if(mysqli_num_rows($result3) > 0){
						if(mysqli_num_rows($resultExist3) > 0){
						$response = "failExist3";
						} else {
							if(mysqli_num_rows($result4) > 0){
								if(mysqli_num_rows($resultExist4) > 0){
									$response = "failExist4";
								} else {
									if(mysqli_num_rows($result5) > 0){
										if(mysqli_num_rows($resultExist5) > 0){
											$response = "failExist5";
										} else {
											$response = "success";
										}
									} else {
										$response = "fail";
									}
								}
							} else {
								$response = "fail";
							}
						}
					} else {
					$response = "fail";
					}
				}
			} else {
				$response = "fail";
			}
		}
	} else {
		$response = "fail";
	}
	
} else if($StudentID2 != "" AND $StudentID3 != "" AND $StudentID4 != ""){

	if(mysqli_num_rows($result1) > 0){
		if(mysqli_num_rows($resultExist1) > 0){
			$response = "failExist1";
		} else {
			if(mysqli_num_rows($result2) > 0){
				if(mysqli_num_rows($resultExist2) > 0){
					$response = "failExist2";
				} else {
					if(mysqli_num_rows($result3) > 0){
						if(mysqli_num_rows($resultExist3) > 0){
						$response = "failExist3";
						} else {
							if(mysqli_num_rows($result4) > 0){
								if(mysqli_num_rows($resultExist4) > 0){
									$response = "failExist4";
								} else {
									$response = "success";
								}
							} else {
								$response = "fail";
							}
						}
					} else {
					$response = "fail";
					}
				}
			} else {
				$response = "fail";
			}
		}

	} else {
		$response = "fail";
	}

} else if($StudentID2 != "" AND $StudentID3 != ""){
	if(mysqli_num_rows($result1) > 0){
		if(mysqli_num_rows($resultExist1) > 0){
			$response = "failExist1";
		} else {
			if(mysqli_num_rows($result2) > 0){
				if(mysqli_num_rows($resultExist2) > 0){
					$response = "failExist2";
				} else {
					if(mysqli_num_rows($result3) > 0){
						if(mysqli_num_rows($resultExist3) > 0){
						$response = "failExist3";
						} else {
						$response = "success";
						}
					} else {
					$response = "fail";
					}
				}
			} else {
				$response = "fail";
			}
		}

	} else {
		$response = "fail";
	}
	
} else if($StudentID2 != ""){
	if(mysqli_num_rows($result1) > 0){
		if(mysqli_num_rows($resultExist1) > 0){
			$response = "failExist1";
		} else {
			if(mysqli_num_rows($result2) > 0){
				if(mysqli_num_rows($resultExist2) > 0){
					$response = "failExist2";
				} else {
					$response = "success";
				}

			} else {
				$response = "fail";
			}
		}

	} else {
		$response = "fail";
	}
} else {
	if(mysqli_num_rows($result1) > 0){
		if(mysqli_num_rows($resultExist1) > 0){
			$response = "failExist1";
		} else {
			$response = "success";
		}
	} else {
		$response = "fail";
	}
}
echo $response;
?>