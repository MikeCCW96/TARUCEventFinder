<?php
require "conn.php";
$input = $_POST["input"];
$mysql_query = "select * from eventapplication where eventTitle like '$input%';";
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