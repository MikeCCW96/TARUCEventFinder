<?php
require "conn.php";

$username = $_POST["username"];
$password = $_POST["password"];
$mysql_qry = "select * from StudentSimple where StudentID like '$username' and StudentID like '$password'";
$result = mysqli_query($conn, $mysql_qry);
$response = array();

while($row = mysqli_fetch_array($result)){
	$response = array("studentId"=>$row[0]);
}




if(mysqli_num_rows($result) > 0) {
echo json_encode($response);
}
else{
echo "fail";
}
?>
