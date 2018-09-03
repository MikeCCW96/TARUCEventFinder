<?php
require "conn.php";
$studentId = $_POST["studentId"];
$mysql_query = "select DISTINCT ea.EventId, ea.EventTitle, ea.EventDescription from eventregistration er, eventtimetable et, eventapplication ea where er.StudentId like '$studentId' AND er.Status like 'Active' AND et.TimetableId like er.TimetableId AND ea.eventId like et.eventId;";
$result = mysqli_query($conn, $mysql_query);

$response = array();

$dbEventId = array();
$dbEventTitle = array();
$dbEventDescription = array();

while($row = mysqli_fetch_array($result)){
	$dbEventId[] = array("EventId"=>$row['EventId']);
	$dbEventTitle[] = array("EventTitle"=>$row['EventTitle']);
	$dbEventDescription[] = array("EventDescription"=>$row['EventDescription']);
}
$dbEventArray = array("EventId"=>$dbEventId, "EventTitle"=>$dbEventTitle, "EventDescription"=>$dbEventDescription);
if(mysqli_num_rows($result) > 0) {
	echo json_encode($dbEventArray);
} else {
	echo "fail";
}




?>