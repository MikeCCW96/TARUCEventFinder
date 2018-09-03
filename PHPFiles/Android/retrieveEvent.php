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



$dbEventArray = array("EventId"=>$dbEventId, "EventTitle"=>$dbEventTitle, "EventDescription"=>$dbEventDescription);
echo json_encode($dbEventArray);

?>