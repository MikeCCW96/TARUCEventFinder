<?php
require "conn.php";

$TimetableID = $_POST['timeTableID'];
$StudentID = $_POST['studentID'];
$StudentID2 = $_POST['studentID2'];
$StudentID3 = $_POST['studentID3'];
$StudentID4 = $_POST['studentID4'];
$StudentID5 = $_POST['studentID5'];
$referBy = $_POST['referBy'];

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

$mysql_qryWaiting = "SELECT COUNT(timetableId) AS totalID FROM eventRegistration WHERE timetableId LIKE '$TimetableID' AND status like 'Active' GROUP BY timetableId"; 
$resultWaiting = $conn->query($mysql_qryWaiting);
$rowWaiting = $resultWaiting->fetch_assoc();
$totalIDWaiting = $rowWaiting["totalID"];
$totalIDWaiting = $totalIDWaiting + 1;

$RegistrationID = $latestID + 1;
$LeaderId = $latestID + 1;

date_default_timezone_set("Asia/Kuala_Lumpur");
$RegisterTime = date('Y-m-d h:i A');
$Description = "";
$Status = "Active";

//For waiting list purpose
if($NOP < $totalIDWaiting){

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



if($StudentID2 != "" AND $StudentID3 != "" AND $StudentID4 != "" AND $StudentID5 != ""){
	$RegistrationID2 = $RegistrationID + 1;
	$RegistrationID3 = $RegistrationID2 + 1;
	$RegistrationID4 = $RegistrationID3 + 1;
	$RegistrationID5 = $RegistrationID4 + 1;
	$mysql_qry6 = "INSERT INTO EventRegistration (RegistrationId, TimetableId, StudentId, registerTime, leaderId, Description, waitingListStatus, redeemStatus, Status) VALUES 
					('$RegistrationID','$TimetableID', '$StudentID', '$RegisterTime', '$LeaderId', '$Description', '$WaitingList', '$RedeemStatus', '$Status'),
					('$RegistrationID2','$TimetableID', '$StudentID2', '$RegisterTime', '$LeaderId', '$Description', '$WaitingList', '$RedeemStatus', '$Status'),
					('$RegistrationID3','$TimetableID', '$StudentID3', '$RegisterTime', '$LeaderId', '$Description', '$WaitingList', '$RedeemStatus', '$Status'),
					('$RegistrationID4','$TimetableID', '$StudentID4', '$RegisterTime', '$LeaderId', '$Description', '$WaitingList', '$RedeemStatus', '$Status'),
					('$RegistrationID5','$TimetableID', '$StudentID5', '$RegisterTime', '$LeaderId', '$Description', '$WaitingList', '$RedeemStatus', '$Status');";
} else if ($StudentID2 != "" AND $StudentID3 != "" AND $StudentID4 != ""){
	$RegistrationID2 = $RegistrationID + 1;
	$RegistrationID3 = $RegistrationID2 + 1;
	$RegistrationID4 = $RegistrationID3 + 1;
	$mysql_qry6 = "INSERT INTO EventRegistration (RegistrationId, TimetableId, StudentId, registerTime, leaderId, Description, waitingListStatus, redeemStatus, Status) VALUES 
					('$RegistrationID','$TimetableID', '$StudentID', '$RegisterTime', '$LeaderId', '$Description', '$WaitingList', '$RedeemStatus', '$Status'),
					('$RegistrationID2','$TimetableID', '$StudentID2', '$RegisterTime', '$LeaderId', '$Description', '$WaitingList', '$RedeemStatus', '$Status'),
					('$RegistrationID3','$TimetableID', '$StudentID3', '$RegisterTime', '$LeaderId', '$Description', '$WaitingList', '$RedeemStatus', '$Status'),
					('$RegistrationID4','$TimetableID', '$StudentID4', '$RegisterTime', '$LeaderId', '$Description', '$WaitingList', '$RedeemStatus', '$Status');";
} else if ($StudentID2 != "" AND $StudentID3 != ""){
	$RegistrationID2 = $RegistrationID + 1;
	$RegistrationID3 = $RegistrationID2 + 1;
	$mysql_qry6 = "INSERT INTO EventRegistration (RegistrationId, TimetableId, StudentId, registerTime, leaderId, Description, waitingListStatus, redeemStatus, Status) VALUES 
					('$RegistrationID','$TimetableID', '$StudentID', '$RegisterTime', '$LeaderId', '$Description', '$WaitingList', '$RedeemStatus', '$Status'),
					('$RegistrationID2','$TimetableID', '$StudentID2', '$RegisterTime', '$LeaderId', '$Description', '$WaitingList', '$RedeemStatus', '$Status'),
					('$RegistrationID3','$TimetableID', '$StudentID3', '$RegisterTime', '$LeaderId', '$Description', '$WaitingList', '$RedeemStatus', '$Status');";
} else if ($StudentID2 != ""){
	$RegistrationID2 = $RegistrationID + 1;
	$mysql_qry6 = "INSERT INTO EventRegistration (RegistrationId, TimetableId, StudentId, registerTime, leaderId, Description, waitingListStatus, redeemStatus, Status) VALUES 
					('$RegistrationID','$TimetableID', '$StudentID', '$RegisterTime', '$LeaderId', '$Description', '$WaitingList', '$RedeemStatus', '$Status'),
					('$RegistrationID2','$TimetableID', '$StudentID2', '$RegisterTime', '$LeaderId', '$Description', '$WaitingList', '$RedeemStatus', '$Status');";
} else {
	$mysql_qry6 = "INSERT INTO EventRegistration (RegistrationId, TimetableId, StudentId, registerTime, leaderId, Description, waitingListStatus, redeemStatus, Status) VALUES 
					('$RegistrationID','$TimetableID', '$StudentID', '$RegisterTime', '$LeaderId', '$Description', '$WaitingList', '$RedeemStatus', '$Status');";
}



if($referBy != "None"){
	
	if($StudentID2 != "" AND $StudentID3 != "" AND $StudentID4 != "" AND $StudentID5 != ""){
		$mysql_referral = "INSERT INTO referral (referBy, referTo, referDate, TimetableId) VALUES 
							('$referBy', '$StudentID', '$RegisterTime', '$TimetableID'),
							('$referBy', '$StudentID2', '$RegisterTime', '$TimetableID'),
							('$referBy', '$StudentID3', '$RegisterTime', '$TimetableID'),
							('$referBy', '$StudentID4', '$RegisterTime', '$TimetableID'),
							('$referBy', '$StudentID5', '$RegisterTime', '$TimetableID');";
	} else if ($StudentID2 != "" AND $StudentID3 != "" AND $StudentID4 != ""){
		$mysql_referral = "INSERT INTO referral (referBy, referTo, referDate, TimetableId) VALUES 
							('$referBy', '$StudentID', '$RegisterTime', '$TimetableID'),
							('$referBy', '$StudentID2', '$RegisterTime', '$TimetableID'),
							('$referBy', '$StudentID3', '$RegisterTime', '$TimetableID'),
							('$referBy', '$StudentID4', '$RegisterTime', '$TimetableID');";
	} else if ($StudentID2 != "" AND $StudentID3 != ""){
		$mysql_referral = "INSERT INTO referral (referBy, referTo, referDate, TimetableId) VALUES 
							('$referBy', '$StudentID', '$RegisterTime', '$TimetableID'),
							('$referBy', '$StudentID2', '$RegisterTime', '$TimetableID'),
							('$referBy', '$StudentID3', '$RegisterTime', '$TimetableID');";
	} else if ($StudentID2 != ""){
		$mysql_referral = "INSERT INTO referral (referBy, referTo, referDate, TimetableId) VALUES 
							('$referBy', '$StudentID', '$RegisterTime', '$TimetableID'),
							('$referBy', '$StudentID2', '$RegisterTime', '$TimetableID');";
	} else {
		$mysql_referral = "INSERT INTO referral (referBy, referTo, referDate, TimetableId) VALUES 
							('$referBy', '$StudentID', '$RegisterTime', '$TimetableID');";
	}
	
	
	if($conn->query($mysql_referral) === TRUE){
	
	}
}
	
if($conn->query($mysql_qry6) === TRUE) {
	if($NOP < $totalIDWaiting){
		if($EBDueDate > $today){
		echo "Registered Successful! You're in waiting list. Congratulation you earn an early bird gift!";
		}else{
		echo "Registered Successful! You're in waiting list.";	
		}
	}
	else {

		if($EBDueDate > $today){
		echo "Registered Successful! Congratulation you earn an early bird gift!";
		}else{
		echo "Registered Successful!";
		}
	}
}

else{
echo "Error: " . $mysql_qry6 . "<br>" . $conn->error;
}



$conn->close();
?>