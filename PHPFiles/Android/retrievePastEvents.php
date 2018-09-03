<?php
require "conn.php";

$studentId = $_POST["studentId"];
$all = $_POST["all"];
$monthly = $_POST["monthly"];
$yearly = $_POST["yearly"];
$monthlyMonth = $_POST["monthlyMonth"];
$monthlyYear = $_POST["monthlyYear"];
$yearlyYear = $_POST["yearlyYear"];

date_default_timezone_set("Asia/Kuala_Lumpur");
$today = date('Y-m-d');


if($all == "1"){
	$mysql_query = "select * from eventregistration er, eventapplication ea, eventtimetable et where er.studentId like '$studentId' AND er.Status like 'Active' AND et.TimetableId like er.TimetableId AND ea.EventId = et.EventId;";
	$result = mysqli_query($conn, $mysql_query);
} else if($monthly == "1"){
	$mysql_query = "select * from eventregistration er, eventapplication ea, eventtimetable et where er.studentId like '$studentId' AND er.Status like 'Active' AND MONTH(RegisterTime) like '$monthlyMonth' AND YEAR(RegisterTime) like '$monthlyYear' AND et.TimetableId like er.TimetableId AND ea.EventId = et.EventId;";
	$result = mysqli_query($conn, $mysql_query);
} else if($yearly == "1"){
	$mysql_query = "select * from eventregistration er, eventapplication ea, eventtimetable et where er.studentId like '$studentId' AND er.Status like 'Active' AND YEAR(RegisterTime) like '$yearlyYear' AND et.TimetableId like er.TimetableId AND ea.EventId = et.EventId;";
	$result = mysqli_query($conn, $mysql_query);
}

$response = array();

$dbEventId = array();
$dbEventTitle = array();
$dbEventDescription = array();
$dbTimeTableId = array();

while($row = mysqli_fetch_array($result)){
	$dbEventId[] = array("EventId"=>$row['EventId']);
	$dbEventTitle[] = array("EventTitle"=>$row['EventTitle']);
	$dbEventDescription[] = array("EventDescription"=>$row['EventDescription']);
	$dbTimeTableId[] = array("TimeTableId"=>$row['TimetableId']);
}
$dbEventArray = array("EventId"=>$dbEventId, "EventTitle"=>$dbEventTitle, "EventDescription"=>$dbEventDescription, "TimeTableId"=>$dbTimeTableId);
if(mysqli_num_rows($result) > 0) {
	echo json_encode($dbEventArray);
} else {
	echo "fail";
}




?>