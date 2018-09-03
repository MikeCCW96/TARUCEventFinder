<?php
require "conn.php";

$studentId = $_POST["studentId"];
$mysql_query = "select * from subscription where studentId like '$studentId';";
$result = mysqli_query($conn, $mysql_query);

$response = array();

$dbActivityType = array();

while($row = mysqli_fetch_array($result)){
	$dbActivityType[] = array("ActivityType"=>$row['ActivityType']);
}


$dbActivityArray = array("ActivityType"=>$dbActivityType);
echo json_encode($dbActivityArray);
?>