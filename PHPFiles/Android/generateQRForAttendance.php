<?php
require "conn.php";
$studentId = $_POST["studentId"];
$timeTableId = $_POST["timeTableId"];
$mysql_query = "select * from eventregistration where TimetableId like '$timeTableId' AND StudentId like '$studentId';";
$result = mysqli_query($conn, $mysql_query);

$response = array();


while($row = mysqli_fetch_array($result)){
	$response = array("RegistrationId"=>$row[0]);
}

if(mysqli_num_rows($result) > 0) {
	echo json_encode($response);
} else {
	echo "fail";
}




?>