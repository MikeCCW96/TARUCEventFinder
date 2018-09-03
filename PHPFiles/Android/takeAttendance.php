<?php
require "conn.php";

$regId = $_POST["regId"];


$mysql_query = "select * from eventregistration where registrationId like '$regId';";
$result = mysqli_query($conn, $mysql_query);

$mysql_qry4 = "SELECT MAX(AttendanceId) As LatestID FROM attendance";
$result4 = $conn->query($mysql_qry4);
$row4 = $result4->fetch_assoc();
$latestID = $row4["LatestID"];
$latestID = $latestID + 1;

date_default_timezone_set("Asia/Kuala_Lumpur");
$RegisterTime = date('Y-m-d h:i A');
$RegisterDate = date('Y-m-d');

$mysql_qrygg = "select * from eventregistration er, eventtimetable et where er.RegistrationId like '$regId' AND et.TimetableId like er.TimetableId;";
$resultgg = $conn->query($mysql_qrygg);

if (mysqli_num_rows($resultgg) > 0){
	while ($row = mysqli_fetch_array($resultgg)){
		$DateTimeStart = $row['EventStartDate'];
		$DateTimeEnd = $row['EventEndDate'];
	}
	
	if ($DateTimeStart < $RegisterDate && $DateTimeEnd > $RegisterDate){
		if (mysqli_num_rows($result) > 0){
	$mysql_query2 = "select * from attendance where registrationId like '$regId';";
	$result2 = mysqli_query($conn, $mysql_query2);
	
	if (mysqli_num_rows($result2) > 0){
		echo "Taken";
	} else {
		$mysql_query3 = "select * from eventregistration where registrationId like '$regId' AND Status like 'Not Active';";
		$result3 = mysqli_query($conn, $mysql_query3);
		if (mysqli_num_rows($result3) > 0){
			echo "Cancelled";
		} else {
			$mysql_qryInsert = "INSERT INTO attendance (AttendanceId, EventSession, AttendanceTime, Status, RegistrationId) VALUES ('$latestID', '1', '$RegisterTime', 'Attended', '$regId')";
			if ($conn->query($mysql_qryInsert) === TRUE) {
				echo "Success";
			} else {
		
			}
		}

	}
	
} else {
	echo "lul";
	
}
	} else {
		echo "nope";
	}

}






?>