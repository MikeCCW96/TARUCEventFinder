<?php
require "conn.php";

$studentId = $_POST["studentId"];


$mysql_qry = "select * from StudentSimple where StudentID like '$studentId'";
$result = mysqli_query($conn, $mysql_qry);
$response = array();

while($row = mysqli_fetch_array($result)){
	$response = array("studentId"=>$row[0], "studentName"=>$row[1], "contactNum"=>$row[2], "address"=>$row[3]);
}

if(mysqli_num_rows($result) > 0) {
echo json_encode($response);
}
else{
echo "fail";
}
?>