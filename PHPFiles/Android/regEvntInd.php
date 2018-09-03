<?php
require "conn.php";

$TimetableID = $_POST['timetableID'];
$StudentID = $_POST['studentID'];

//Use to retrieve eventId
$mysql_qry = "SELECT `eventId` FROM EventTimetable WHERE timetableId LIKE '$TimetableID' ";
$result = $conn->query($mysql_qry);
$row = $result->fetch_assoc();
$EventId = $row["eventId"];

//Use to retrieve number of participants
$mysql_qry2 = "SELECT `noOfParticipant` FROM EventApplication WHERE eventId LIKE '$EventId'"; 
$result2 = $conn->query($mysql_qry2);
$row2 = $result2->fetch_assoc();
$NOP = $row2["noOfParticipant"];

//Use to count total of timetableId
$mysql_qry3 = "SELECT COUNT(timetableId) AS totalID FROM eventRegistration WHERE timetableId LIKE '$TimetableID' GROUP BY timetableId"; 
$result3 = $conn->query($mysql_qry3);
$row3 = $result3->fetch_assoc();
$totalID = $row3["totalID"];
$totalID = $totalID + 1;

//Get leaderID by retrieving the current reg ID + 1
$mysql_qry4 = "SELECT MAX(RegistrationID) As LatestID FROM EventRegistration";
$result4 = $conn->query($mysql_qry4);
$row4 = $result4->fetch_assoc();
$latestID = $row4["LatestID"];

$RegistrationID = $latestID + 1;
$LeaderId = $latestID + 1;


date_default_timezone_set("Asia/Kuala_Lumpur");
$RegisterTime = date('Y-m-d h:i A');
$Description = "";
$Status = "Active";

//For waiting list purpose
if($NOP < $totalID){

$WaitingList = "Waiting";

}
else{

$WaitingList = "";

}

//Use to early bird ticket due date
$mysql_qry5 = "SELECT `EBTicketDueDate` FROM EventApplication WHERE eventId LIKE '$EventId'"; 
$result5 = $conn->query($mysql_qry5);
$row5 = $result5->fetch_assoc();
$EBDueDate = $row5["EBTicketDueDate"];

$today = date('Y-m-d');

if($EBDueDate > $today){
	$RedeemStatus = "Redeemed";
}else{
	$RedeemStatus = "";
}

$mysql_qry6 = "INSERT INTO EventRegistration (RegistrationId, TimetableId, StudentId, registerTime, leaderId, Description, waitingListStatus, redeemStatus, Status) VALUES ('$RegistrationID','$TimetableID', '$StudentID', '$RegisterTime', '$LeaderId', '$Description', '$WaitingList', '$RedeemStatus', '$Status')";
if($conn->query($mysql_qry6) === TRUE) {
	if($NOP < $totalID){
	if($EBDueDate > $today){
		echo "Registered Successful! You're in waiting list. Congratulation you earn an early bird gift!";
		}else{
		echo "Registered Successful! You're in waiting list.";	
		}
	}
	else {
		echo "Registered Successful!";
	}
}
else{
echo "Error: " . $mysql_qry6 . "<br>" . $conn->error;
}

$conn->close();
?>