<?php
require "conn.php";

$studentId = $_POST['studentId'];

$mysql_qry = "SELECT * FROM referral WHERE referBy = '$studentId';";
$result = mysqli_query($conn, $mysql_qry);

$dbStudentName = array();
$dbEventTitle = array();
$dbEventStartDate = array();
$dbEventEndDate = array();
$dbEventStartTime = array();
$dbEventEndTime = array(); 

	while($row = mysqli_fetch_array($result)){
		$mysql_qry4 = "SELECT * FROM StudentSimple WHERE StudentID = '".$row['referTo']."';";
			$result4 = mysqli_query($conn, $mysql_qry4);
			if(mysqli_num_rows($result4)>0){
				while($row4 = mysqli_fetch_array($result4)){
				$dbStudentName[] = array("StudentName"=>$row4['StudentName']);
				$mysql_qry2 = "SELECT * FROM EventTimetable WHERE TimetableId = '".$row['TimetableId']."';";
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
			}
	}

$dbEventArray = array("StudentName"=>$dbStudentName, "EventTitle"=>$dbEventTitle, "EventStartDate"=>$dbEventStartDate, "EventEndDate"=>$dbEventEndDate, "EventStartTime"=>$dbEventStartTime, "EventEndTime"=>$dbEventEndTime);
echo json_encode($dbEventArray);

?>