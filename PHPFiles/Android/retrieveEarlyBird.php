<?php
require "conn.php";

$studentId = $_POST['studentId'];

$mysql_qry = "SELECT * FROM eventRegistration WHERE studentId = '$studentId' AND RedeemStatus = 'Redeemed' AND Status = 'Active';";
$result = mysqli_query($conn, $mysql_qry);

$dbRedeemStatus = array();
$dbEventTitle = array();
$dbEventStartDate = array();
$dbEventEndDate = array();
$dbEventStartTime = array();
$dbEventEndTime = array(); 

while($row = mysqli_fetch_array($result)){
		$dbRedeemStatus[] = array("RedeemStatus"=>$row['RedeemStatus']);
		
		$mysql_qry2 = "SELECT * FROM eventTimetable WHERE TimetableId = '".$row['TimetableId']."';";
			$result2 = mysqli_query($conn, $mysql_qry2);
			if(mysqli_num_rows($result2)>0){
				while($row2 = mysqli_fetch_array($result2)){
				$dbEventStartDate[] = array("EventStartDate"=>$row2['EventStartDate']);
				$dbEventEndDate[] = array("EventEndDate"=>$row2['EventEndDate']);
				$dbEventStartTime[] = array("EventStartTime"=>$row2['EventStartTime']);
				$dbEventEndTime[] = array("EventEndTime"=>$row2['EventEndTime']);
				$mysql_qry3 = "SELECT * FROM EventApplication WHERE EventId = '".$row2['EventId']."';";
				$result3 = mysqli_query($conn, $mysql_qry3);
				if(mysqli_num_rows($result3)>0){
					while($row3 = mysqli_fetch_array($result3)){
						$dbEventTitle[] = array("EventTitle"=>$row3['EventTitle']);
						}
				}
				}
			}
}


$dbEventArray = array("RedeemStatus"=>$dbRedeemStatus, "EventTitle"=>$dbEventTitle, "EventStartDate"=>$dbEventStartDate, "EventEndDate"=>$dbEventEndDate, "EventStartTime"=>$dbEventStartTime, "EventEndTime"=>$dbEventEndTime);
echo json_encode($dbEventArray);

?>