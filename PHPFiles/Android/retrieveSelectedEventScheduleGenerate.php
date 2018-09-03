<?php
require "conn.php";
$studentId = $_POST["studentId"];
$eventId = $_POST["eventId"];


$mysql_query = "select * from eventtimetable e, venue v, eventregistration r where r.StudentId like '$studentId' and r.status like 'Active' and e.TimetableId like r.TimetableId and e.EventId like '$eventId' and v.venueId = e.venueId ORDER BY e.timeTableId, e.eventStartDate, e.eventEndDate, e.eventStartTime, e.eventEndTime, v.venueName, r.WaitingListStatus;";

$mysql_query2 = "select * from venue ;";

$result = mysqli_query($conn, $mysql_query);



$response = array();

$dbTimeTableId = array();
$dbEventStartDate = array();
$dbEventEndDate = array();
$dbEventStartTime = array();
$dbEventEndTime = array();
$dbVenueId = array();
$dbWaitingListStatus = array();

$dbVenueName = array();
$dbVenueDescription = array();




while($row = mysqli_fetch_array($result)){
	$dbTimeTableId[] = array("timeTableId"=>$row[0]);
	$dbEventStartDate[] = array("eventStartDate"=>$row[1]);
	$dbEventEndDate[] = array("eventEndDate"=>$row[2]);
	$dbEventStartTime[] = array("eventStartTime"=>$row[3]);
	$dbEventEndTime[] = array("eventEndTime"=>$row[4]);
	$dbVenueId[] = array("venueId"=>$row[5]);
	$dbWaitingListStatus[] = array("waitingListStatus"=>$row['WaitingListStatus']);
	$venueId = $row[5];
	$mysql_query2 = "select * from venue where venueId like '$venueId';";
	$result2 = mysqli_query($conn, $mysql_query2);
	
	while($row2 = mysqli_fetch_array($result2)){
		$dbVenueName[] = array("venueName"=>$row2[1]);
		$dbVenueDescription[] = array("venueDescription"=>$row2[2]);
	}
}

$dbScheduleArray = array("timeTableId"=>$dbTimeTableId, "eventStartDate"=>$dbEventStartDate, "eventEndDate"=>$dbEventEndDate, "eventStartTime"=>$dbEventStartTime, "eventEndTime"=>$dbEventEndTime, "venueId"=>$dbVenueId, "venueName"=>$dbVenueName, "venueDescription"=>$dbVenueDescription, "waitingListStatus"=>$dbWaitingListStatus);
echo json_encode($dbScheduleArray);
?>

