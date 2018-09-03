<?php
require "conn.php";

$venueId = $_POST["venueId"];

$mysql_query = "select * from venue where venueId like '$venueId';";

$result = mysqli_query($conn, $mysql_query);

$response = array();

while($row = mysqli_fetch_array($result)){
	$response = array("venueId"=>$row[0], "venueName"=>$row[1], "venueDescription"=>$row[2]);
}

echo json_encode($response);
?>