<?php
require "conn.php";

date_default_timezone_set("Asia/Kuala_Lumpur");
$RegisterTime = date('Y-m-d h:i A');

	$mysql_referral = "INSERT INTO referral (referBy, referTo, referDate) VALUES ('1610574', '1610574', '$RegisterTime');";

	if($conn->query($mysql_referral) === TRUE){
	
	}else{
	echo "Error: " . $mysql_referral . "<br>" . $conn->error;}
?>