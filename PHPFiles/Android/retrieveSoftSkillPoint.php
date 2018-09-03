<?php
require "conn.php";

$studentId = $_POST['studentId'];

$mysql_qry = "SELECT SoftSkillPoint FROM StudentSimple WHERE studentId like '$studentId';";
$result = mysqli_query($conn, $mysql_qry);

while($row = mysqli_fetch_array($result)){
	$dbSoftSkillPoint[] = array("SoftSkillPoint"=>$row['SoftSkillPoint']);
}

echo json_encode($dbSoftSkillPoint);
?>