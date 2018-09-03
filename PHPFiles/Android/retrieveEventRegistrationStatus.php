<?php
require "conn.php";

$studentId = $_POST['studentId'];


$mysql_query = "select * from eventregistration er, eventapplication ea, eventtimetable et where er.studentId = '$studentId' AND er.Status = 'Active' AND et.TimetableId = er.TimetableId AND ea.EventId = et.EventId ;";
$result = mysqli_query($conn, $mysql_query);

$response = array();

$dbWaitingListStatus = array();
$dbEventTitle = array();
$dbEventStartDate = array();
$dbEventEndDate = array();
$dbEventStartTime = array();
$dbEventEndTime = array();

while($row = mysqli_fetch_array($result)){
	$dbWaitingListStatus[] = array("WaitingListStatus"=>$row['WaitingListStatus']);
	$dbEventTitle[] = array("EventTitle"=>$row['EventTitle']);
	$dbEventStartDate[] = array("EventStartDate"=>$row['EventStartDate']);
	$dbEventEndDate[] = array("EventEndDate"=>$row['EventEndDate']);
	$dbEventStartTime[] = array("EventStartTime"=>$row['EventStartTime']);
	$dbEventEndTime[] = array("EventEndTime"=>$row['EventEndTime']);
}



$dbEventArray = array("WaitingListStatus"=>$dbWaitingListStatus, "EventTitle"=>$dbEventTitle, "EventStartDate"=>$dbEventStartDate, "EventEndDate"=>$dbEventEndDate, "EventStartTime"=>$dbEventStartTime, "EventEndTime"=>$dbEventEndTime);
echo json_encode($dbEventArray);

?>