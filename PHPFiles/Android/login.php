<?php
require "conn.php";

$eventId = $_POST["eventId"];
$eventId = "EV00001";

$mysql_query = "select * from eventapplication where EventId like '$eventId';";

$result = mysqli_query($conn, $mysql_query);

$response = array();

while($row = mysqli_fetch_array($result)){
	$response = array("eventId"=>$row[0], "eventTitle"=>$row[1], "eventDescription"=>$row[2], "noOfParticipant"=>$row[5], "createEventApprovalStatus"=>$row[6], "activityType"=>$row[7], "EBTicketDueDate"=>$row[11], "EventLocation"=>$row[21]);
}

echo json_encode($response);
?>