<?php
require "conn.php";

$regId = $_POST["regId"];
$timetableId = $_POST["timeTableId"];


$mysql_qryCheck = "select * from attendance where registrationId LIKE '$regId';";
$resultCheck = $conn->query($mysql_qryCheck);
if(mysqli_num_rows($resultCheck)>0){
	echo "attended";
} else {

//Update the status to Not Active
$mysql_qry2 = "UPDATE eventRegistration SET status = 'Not Active' WHERE registrationId LIKE '$regId';";
$result2 = $conn->query($mysql_qry2);

//Validate if there is any waiting list
$mysql_qry3 = "SELECT * FROM eventRegistration where waitingListStatus = 'Waiting' AND timetableId like '$timetableId';";
$result3 = $conn->query($mysql_qry3);
if(mysqli_num_rows($result3)>0){
//Take the first waiting list participant off the waiting list
$mysql_qry4 = "SELECT MIN(registrationId) As firstWaitingListId FROM EventRegistration WHERE waitingListStatus like 'Waiting' AND timetableId like '$timetableId';";
$result4 = $conn->query($mysql_qry4);
$row4 = $result4->fetch_assoc();
$firstWaitingListId = $row4["firstWaitingListId"];

$mysql_qry5 = "UPDATE eventRegistration SET status = 'Active', waitingListStatus = '' WHERE registrationId LIKE '$firstWaitingListId';";
$result5 = $conn->query($mysql_qry5);

if($result5 === TRUE){
echo "Cancel successful";
}else{
echo "Cancel failed";
}


}


}


$conn->close();
?>