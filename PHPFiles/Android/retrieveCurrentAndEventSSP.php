<?php
require"conn.php";

$registrationId = $_POST['registrationId'];

//Retrieve current SSP from selected student
$mysql_qry = "SELECT SoftSkillPoint FROM StudentSimple WHERE studentId IN (SELECT studentId FROM EventRegistration WHERE registrationId like '$registrationId');";
$result = mysqli_query($conn, $mysql_qry);
while($row = mysqli_fetch_array($result)){
	$dbCurrentSoftSkillPoint[] = array("SoftSkillPoint"=>$row['SoftSkillPoint']);
}

//etrieve timetableId by using registrationId
$mysql_qry2 = "SELECT SoftSkillPoint FROM EventApplication WHERE eventId IN (SELECT eventId FROM EventTimetable WHERE timetableId IN (SELECT timetableId FROM EventRegistration WHERE registrationId = '$registrationId'))";
$result2 = mysqli_query($conn, $mysql_qry2);
while($row2 = mysqli_fetch_array($result2)){
	$dbEventSoftSkillPoint[] = array("SoftSkillPoint"=>$row2['SoftSkillPoint']);
}

echo json_encode(array_merge($dbCurrentSoftSkillPoint, $dbEventSoftSkillPoint));

?>