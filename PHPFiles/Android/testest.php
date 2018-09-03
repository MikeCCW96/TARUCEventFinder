<?php
require "conn.php";

date_default_timezone_set("Asia/Kuala_Lumpur");
$RegisterTime = date('Y-m-d h:i A');

$mysql_referral = "INSERT INTO referral (referBy, referTo, referDate, TimetableId) VALUES 
							('1610574', '1610571', '$RegisterTime', 'TM00001');";
							
							
	if($conn->query($mysql_referral) === TRUE){
		echo 'lul';
	} else {
		echo "Error updating record: " . $conn->error;
	}
?>