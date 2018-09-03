<?php
require "conn.php";
$eventId = $_POST["eventId"];

date_default_timezone_set("Asia/Kuala_Lumpur");
$TimeNow = date('Y-m-d h:i A');

$mysql_query = "select * from eventtimetable e, venue v where e.EventId like '$eventId' and e.EventStartDate > '$TimeNow' and v.venueId = e.venueId ORDER BY e.timeTableId, e.eventStartDate, e.eventEndDate, e.eventStartTime, e.eventEndTime, v.venueName;";
$mysql_query2 = "select * from venue ;";

$result = mysqli_query($conn, $mysql_query);



$response = array();

$dbTimeTableId = array();
$dbEventStartDate = array();
$dbEventEndDate = array();
$dbEventStartTime = array();
$dbEventEndTime = array();
$dbVenueId = array();

$dbVenueName = array();
$dbVenueDescription = array();




while($row = mysqli_fetch_array($result)){
	$dbTimeTableId[] = array("timeTableId"=>$row[0]);
	$dbEventStartDate[] = array("eventStartDate"=>$row[1]);
	$dbEventEndDate[] = array("eventEndDate"=>$row[2]);
	$dbEventStartTime[] = array("eventStartTime"=>$row[3]);
	$dbEventEndTime[] = array("eventEndTime"=>$row[4]);
	$dbVenueId[] = array("venueId"=>$row[5]);
	$venueId = $row[5];
	$mysql_query2 = "select * from venue where venueId like '$venueId';";
	$result2 = mysqli_query($conn, $mysql_query2);
	
	while($row2 = mysqli_fetch_array($result2)){
		$dbVenueName[] = array("venueName"=>$row2[1]);
		$dbVenueDescription[] = array("venueDescription"=>$row2[2]);
	}
}

$dbScheduleArray = array("timeTableId"=>$dbTimeTableId, "eventStartDate"=>$dbEventStartDate, "eventEndDate"=>$dbEventEndDate, "eventStartTime"=>$dbEventStartTime, "eventEndTime"=>$dbEventEndTime, "venueId"=>$dbVenueId, "venueName"=>$dbVenueName, "venueDescription"=>$dbVenueDescription);
echo json_encode($dbScheduleArray);
?>

