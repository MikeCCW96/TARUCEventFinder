<?php
require "conn.php";
$mysql_query = "select * from eventapplication;";
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

//while($row = mysqli_fetch_array($result)){
//	$response = array("EventId"=>$row[0], "EventTitle"=>$row[1], "EventDescription"=>$row[2]);
//}

$dbEventArray = array("EventId"=>$dbEventId, "EventTitle"=>$dbEventTitle, "EventDescription"=>$dbEventDescription);
echo json_encode($dbEventArray);
//echo json_encode(array("EventId"=>$dbEventId));
//echo json_encode(array("EventTitle"=>$dbEventTitle));
//echo json_encode(array("EventDescription"=>$dbEventDescription));


//echo json_encode(array("eventRetrieve"=>$response));
?>