<?php
require "conn.php";

$registrationId = $_POST['registrationId'];
$SoftSkillPoint = $_POST['SoftSkillPoint'];

$mysql_qry = "SELECT studentId FROM EventRegistration WHERE registrationId like '$registrationId'";
$result = mysqli_query($conn, $mysql_qry);
$row = mysqli_fetch_assoc($result);
$studentId = $row['studentId'];

$mysql_qry2 = "UPDATE StudentSimple SET SoftSkillPoint = '$SoftSkillPoint' WHERE studentId = '$studentId'";
$result2 = mysqli_query($conn, $mysql_qry2);

if($result2 === TRUE){
echo "Update SSP successful";
}else{
echo "Update SSP failed";
}

?>