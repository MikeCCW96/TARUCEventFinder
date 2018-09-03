<?php
require "conn.php";

$studentId = $_POST["studentId"];
$timeTableId = $_POST["timeTableId"];


$mysql_query = "select * from eventtimetable et, eventregistration er, eventapplication ea where et.TimeTableId = '$timeTableId' AND ea.EventId = et.EventId AND er.TimeTableId = '$timeTableId' AND er.StudentId = '$studentId' ;";

$result = mysqli_query($conn, $mysql_query);

$response = array();

while($row = mysqli_fetch_array($result)){
	$response = array("EventStartDate"=>$row['EventStartDate'], "RegisterTime"=>$row['RegisterTime']);
}

echo json_encode($response);
?>