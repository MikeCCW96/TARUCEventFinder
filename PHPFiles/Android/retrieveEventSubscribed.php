<?php
require "conn.php";
$studentId = $_POST["studentId"];
$strMeeting = 0;
$strAnnual = 0;
$strTraining = 0;
$strClass = 0;
$strGathering = 0;
$strVisit = 0;
$strTrip = 0;
$strCamp = 0;
$strPerformance = 0;
$strNite = 0;
$strTalk = 0;
$strWorkshop = 0;
$strSeminar = 0;
$strConference = 0;
$strExhibition = 0;
$strFundRaising = 0;
$strCompetition = 0;
$strSportsCarnival = 0;
$strTreasureHunt = 0;
$strOthers = 0;

$dbEventId = array();
$dbEventTitle = array();
$dbEventDescription = array();

$mysql_query = "select * from subscription where studentId like '$studentId' and activityType like 'Meeting';";
$result = mysqli_query($conn, $mysql_query);
if(mysqli_num_rows($result) > 0) {
	$strMeeting = 1;
}

if ($strMeeting == 1){
	$mysql_query = "select * from eventapplication where activityType like 'Meeting';";
	$result = mysqli_query($conn, $mysql_query);
	while($row = mysqli_fetch_array($result)){
		$dbEventId[] = array("EventId"=>$row['EventId']);
		$dbEventTitle[] = array("EventTitle"=>$row['EventTitle']);
		$dbEventDescription[] = array("EventDescription"=>$row['EventDescription']);
	}
}



$mysql_query = "select * from subscription where studentId like '$studentId' and activityType like 'Annual General Meeting';";
$result = mysqli_query($conn, $mysql_query);
if(mysqli_num_rows($result) > 0) {
	$strAnnual = 1;
}

if ($strAnnual == 1){
	$mysql_query = "select * from eventapplication where activityType like 'Annual General Meeting';";
	$result = mysqli_query($conn, $mysql_query);
	while($row = mysqli_fetch_array($result)){
		$dbEventId[] = array("EventId"=>$row['EventId']);
		$dbEventTitle[] = array("EventTitle"=>$row['EventTitle']);
		$dbEventDescription[] = array("EventDescription"=>$row['EventDescription']);
	}
}



$mysql_query = "select * from subscription where studentId like '$studentId' and activityType like 'Training/Practice';";
$result = mysqli_query($conn, $mysql_query);
if(mysqli_num_rows($result) > 0) {
	$strTraining = 1;
}

if ($strTraining == 1){
	$mysql_query = "select * from eventapplication where activityType like 'Training/Practice';";
	$result = mysqli_query($conn, $mysql_query);
	while($row = mysqli_fetch_array($result)){
		$dbEventId[] = array("EventId"=>$row['EventId']);
		$dbEventTitle[] = array("EventTitle"=>$row['EventTitle']);
		$dbEventDescription[] = array("EventDescription"=>$row['EventDescription']);
	}
}



$mysql_query = "select * from subscription where studentId like '$studentId' and activityType like 'Class';";
$result = mysqli_query($conn, $mysql_query);
if(mysqli_num_rows($result) > 0) {
	$strClass = 1;
}

if ($strClass == 1){
	$mysql_query = "select * from eventapplication where activityType like 'Class';";
	$result = mysqli_query($conn, $mysql_query);
	while($row = mysqli_fetch_array($result)){
		$dbEventId[] = array("EventId"=>$row['EventId']);
		$dbEventTitle[] = array("EventTitle"=>$row['EventTitle']);
		$dbEventDescription[] = array("EventDescription"=>$row['EventDescription']);
	}
}



$mysql_query = "select * from subscription where studentId like '$studentId' and activityType like 'Gathering';";
$result = mysqli_query($conn, $mysql_query);
if(mysqli_num_rows($result) > 0) {
	$strGathering = 1;
}

if ($strGathering == 1){
	$mysql_query = "select * from eventapplication where activityType like 'Gathering';";
	$result = mysqli_query($conn, $mysql_query);
	while($row = mysqli_fetch_array($result)){
		$dbEventId[] = array("EventId"=>$row['EventId']);
		$dbEventTitle[] = array("EventTitle"=>$row['EventTitle']);
		$dbEventDescription[] = array("EventDescription"=>$row['EventDescription']);
	}
}


$mysql_query = "select * from subscription where studentId like '$studentId' and activityType like 'Visit';";
$result = mysqli_query($conn, $mysql_query);
if(mysqli_num_rows($result) > 0) {
	$strVisit = 1;
}

if ($strVisit == 1){
	$mysql_query = "select * from eventapplication where activityType like 'Visit';";
	$result = mysqli_query($conn, $mysql_query);
	while($row = mysqli_fetch_array($result)){
		$dbEventId[] = array("EventId"=>$row['EventId']);
		$dbEventTitle[] = array("EventTitle"=>$row['EventTitle']);
		$dbEventDescription[] = array("EventDescription"=>$row['EventDescription']);
	}
}


$mysql_query = "select * from subscription where studentId like '$studentId' and activityType like 'Trip';";
$result = mysqli_query($conn, $mysql_query);
if(mysqli_num_rows($result) > 0) {
	$strTrip = 1;
}

if ($strTrip == 1){
	$mysql_query = "select * from eventapplication where activityType like 'Trip';";
	$result = mysqli_query($conn, $mysql_query);
	while($row = mysqli_fetch_array($result)){
		$dbEventId[] = array("EventId"=>$row['EventId']);
		$dbEventTitle[] = array("EventTitle"=>$row['EventTitle']);
		$dbEventDescription[] = array("EventDescription"=>$row['EventDescription']);
	}
}


$mysql_query = "select * from subscription where studentId like '$studentId' and activityType like 'Camp';";
$result = mysqli_query($conn, $mysql_query);
if(mysqli_num_rows($result) > 0) {
	$strCamp = 1;
}

if ($strCamp == 1){
	$mysql_query = "select * from eventapplication where activityType like 'Camp';";
	$result = mysqli_query($conn, $mysql_query);
	while($row = mysqli_fetch_array($result)){
		$dbEventId[] = array("EventId"=>$row['EventId']);
		$dbEventTitle[] = array("EventTitle"=>$row['EventTitle']);
		$dbEventDescription[] = array("EventDescription"=>$row['EventDescription']);
	}
}


$mysql_query = "select * from subscription where studentId like '$studentId' and activityType like 'Performance';";
$result = mysqli_query($conn, $mysql_query);
if(mysqli_num_rows($result) > 0) {
	$strPerformance = 1;
}

if ($strPerformance == 1){
	$mysql_query = "select * from eventapplication where activityType like 'Performance';";
	$result = mysqli_query($conn, $mysql_query);
	while($row = mysqli_fetch_array($result)){
		$dbEventId[] = array("EventId"=>$row['EventId']);
		$dbEventTitle[] = array("EventTitle"=>$row['EventTitle']);
		$dbEventDescription[] = array("EventDescription"=>$row['EventDescription']);
	}
}



$mysql_query = "select * from subscription where studentId like '$studentId' and activityType like 'Nite';";
$result = mysqli_query($conn, $mysql_query);
if(mysqli_num_rows($result) > 0) {
	$strNite = 1;
}

if ($strNite == 1){
	$mysql_query = "select * from eventapplication where activityType like 'Nite';";
	$result = mysqli_query($conn, $mysql_query);
	while($row = mysqli_fetch_array($result)){
		$dbEventId[] = array("EventId"=>$row['EventId']);
		$dbEventTitle[] = array("EventTitle"=>$row['EventTitle']);
		$dbEventDescription[] = array("EventDescription"=>$row['EventDescription']);
	}
}



$mysql_query = "select * from subscription where studentId like '$studentId' and activityType like 'Talk';";
$result = mysqli_query($conn, $mysql_query);
if(mysqli_num_rows($result) > 0) {
	$strTalk = 1;
}

if ($strTalk == 1){
	$mysql_query = "select * from eventapplication where activityType like 'Talk';";
	$result = mysqli_query($conn, $mysql_query);
	while($row = mysqli_fetch_array($result)){
		$dbEventId[] = array("EventId"=>$row['EventId']);
		$dbEventTitle[] = array("EventTitle"=>$row['EventTitle']);
		$dbEventDescription[] = array("EventDescription"=>$row['EventDescription']);
	}
}


$mysql_query = "select * from subscription where studentId like '$studentId' and activityType like 'Workshop';";
$result = mysqli_query($conn, $mysql_query);
if(mysqli_num_rows($result) > 0) {
	$strWorkshop = 1;
}

if ($strWorkshop == 1){
	$mysql_query = "select * from eventapplication where activityType like 'Workshop';";
	$result = mysqli_query($conn, $mysql_query);
	while($row = mysqli_fetch_array($result)){
		$dbEventId[] = array("EventId"=>$row['EventId']);
		$dbEventTitle[] = array("EventTitle"=>$row['EventTitle']);
		$dbEventDescription[] = array("EventDescription"=>$row['EventDescription']);
	}
}

$mysql_query = "select * from subscription where studentId like '$studentId' and activityType like 'Seminar';";
$result = mysqli_query($conn, $mysql_query);
if(mysqli_num_rows($result) > 0) {
	$strSeminar = 1;
}

if ($strSeminar == 1){
	$mysql_query = "select * from eventapplication where activityType like 'Seminar';";
	$result = mysqli_query($conn, $mysql_query);
	while($row = mysqli_fetch_array($result)){
		$dbEventId[] = array("EventId"=>$row['EventId']);
		$dbEventTitle[] = array("EventTitle"=>$row['EventTitle']);
		$dbEventDescription[] = array("EventDescription"=>$row['EventDescription']);
	}
}

$mysql_query = "select * from subscription where studentId like '$studentId' and activityType like 'Conference';";
$result = mysqli_query($conn, $mysql_query);
if(mysqli_num_rows($result) > 0) {
	$strConference = 1;
}

if ($strConference == 1){
	$mysql_query = "select * from eventapplication where activityType like 'Conference';";
	$result = mysqli_query($conn, $mysql_query);
	while($row = mysqli_fetch_array($result)){
		$dbEventId[] = array("EventId"=>$row['EventId']);
		$dbEventTitle[] = array("EventTitle"=>$row['EventTitle']);
		$dbEventDescription[] = array("EventDescription"=>$row['EventDescription']);
	}
}

$mysql_query = "select * from subscription where studentId like '$studentId' and activityType like 'Exhibition';";
$result = mysqli_query($conn, $mysql_query);
if(mysqli_num_rows($result) > 0) {
	$strExhibition = 1;
}

if ($strExhibition == 1){
	$mysql_query = "select * from eventapplication where activityType like 'Exhibition';";
	$result = mysqli_query($conn, $mysql_query);
	while($row = mysqli_fetch_array($result)){
		$dbEventId[] = array("EventId"=>$row['EventId']);
		$dbEventTitle[] = array("EventTitle"=>$row['EventTitle']);
		$dbEventDescription[] = array("EventDescription"=>$row['EventDescription']);
	}
}

$mysql_query = "select * from subscription where studentId like '$studentId' and activityType like 'Fund Raising';";
$result = mysqli_query($conn, $mysql_query);
if(mysqli_num_rows($result) > 0) {
	$strFundRaising = 1;
}

if ($strFundRaising == 1){
	$mysql_query = "select * from eventapplication where activityType like 'Fund Raising';";
	$result = mysqli_query($conn, $mysql_query);
	while($row = mysqli_fetch_array($result)){
		$dbEventId[] = array("EventId"=>$row['EventId']);
		$dbEventTitle[] = array("EventTitle"=>$row['EventTitle']);
		$dbEventDescription[] = array("EventDescription"=>$row['EventDescription']);
	}
}

$mysql_query = "select * from subscription where studentId like '$studentId' and activityType like 'Competition';";
$result = mysqli_query($conn, $mysql_query);
if(mysqli_num_rows($result) > 0) {
	$strCompetition = 1;
}

if ($strCompetition == 1){
	$mysql_query = "select * from eventapplication where activityType like 'Competition';";
	$result = mysqli_query($conn, $mysql_query);
	while($row = mysqli_fetch_array($result)){
		$dbEventId[] = array("EventId"=>$row['EventId']);
		$dbEventTitle[] = array("EventTitle"=>$row['EventTitle']);
		$dbEventDescription[] = array("EventDescription"=>$row['EventDescription']);
	}
}


$mysql_query = "select * from subscription where studentId like '$studentId' and activityType like 'Sports Carnival';";
$result = mysqli_query($conn, $mysql_query);
if(mysqli_num_rows($result) > 0) {
	$strSportsCarnival = 1;
}

if ($strSportsCarnival == 1){
	$mysql_query = "select * from eventapplication where activityType like 'Sports Carnival';";
	$result = mysqli_query($conn, $mysql_query);
	while($row = mysqli_fetch_array($result)){
		$dbEventId[] = array("EventId"=>$row['EventId']);
		$dbEventTitle[] = array("EventTitle"=>$row['EventTitle']);
		$dbEventDescription[] = array("EventDescription"=>$row['EventDescription']);
	}
}

$mysql_query = "select * from subscription where studentId like '$studentId' and activityType like 'Treasure Hunt';";
$result = mysqli_query($conn, $mysql_query);
if(mysqli_num_rows($result) > 0) {
	$strTreasureHunt = 1;
}

if ($strTreasureHunt == 1){
	$mysql_query = "select * from eventapplication where activityType like 'Treasure Hunt';";
	$result = mysqli_query($conn, $mysql_query);
	while($row = mysqli_fetch_array($result)){
		$dbEventId[] = array("EventId"=>$row['EventId']);
		$dbEventTitle[] = array("EventTitle"=>$row['EventTitle']);
		$dbEventDescription[] = array("EventDescription"=>$row['EventDescription']);
	}
}

$mysql_query = "select * from subscription where studentId like '$studentId' and activityType like 'Others';";
$result = mysqli_query($conn, $mysql_query);
if(mysqli_num_rows($result) > 0) {
	$strOthers = 1;
}

if ($strOthers == 1){
	$mysql_query = "select * from eventapplication where activityType like 'Others';";
	$result = mysqli_query($conn, $mysql_query);
	while($row = mysqli_fetch_array($result)){
		$dbEventId[] = array("EventId"=>$row['EventId']);
		$dbEventTitle[] = array("EventTitle"=>$row['EventTitle']);
		$dbEventDescription[] = array("EventDescription"=>$row['EventDescription']);
	}
}




$dbEventArray = array("EventId"=>$dbEventId, "EventTitle"=>$dbEventTitle, "EventDescription"=>$dbEventDescription);
echo json_encode($dbEventArray);



?>