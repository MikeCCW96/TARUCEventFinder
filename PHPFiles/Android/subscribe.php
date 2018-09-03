<?php
require "conn.php";

$studentId = $_POST["studentId"];
$strMeeting = $_POST["strMeeting"];
$strAnnual = $_POST["strAnnual"];
$strTraining = $_POST["strTraining"];
$strClass = $_POST["strClass"];
$strGathering = $_POST["strGathering"];
$strVisit = $_POST["strVisit"];
$strTrip = $_POST["strTrip"];
$strCamp = $_POST["strCamp"];
$strPerformance = $_POST["strPerformance"];
$strNite = $_POST["strNite"];
$strTalk = $_POST["strTalk"];
$strWorkshop = $_POST["strWorkshop"];
$strSeminar = $_POST["strSeminar"];
$strConference = $_POST["strConference"];
$strExhibition = $_POST["strExhibition"];
$strFundRaising = $_POST["strFundRaising"];
$strCompetition = $_POST["strCompetition"];
$strSportsCarnival = $_POST["strSportsCarnival"];
$strTreasureHunt = $_POST["strTreasureHunt"];
$strOthers = $_POST["strOthers"];

$mysql_query = "select * from subscription where studentId like '$studentId';";
$result = mysqli_query($conn, $mysql_query);

if($strMeeting == "1"){
	$mysql_query = "select * from subscription where studentId like '$studentId' and activityType like 'Meeting';";
	$result = mysqli_query($conn, $mysql_query);
	if(mysqli_num_rows($result) > 0) {
		
	}
	else{
		$mysql_qry = "INSERT INTO subscription (StudentID, ActivityType) VALUES ('$studentId', 'Meeting')";
		if ($conn->query($mysql_qry) === TRUE) {

		} else {

		}
	}
} else {
	$mysql_query = "select * from subscription where studentId like '$studentId' and activityType like 'Meeting';";
	$result = mysqli_query($conn, $mysql_query);
	if(mysqli_num_rows($result) > 0) {
		$mysql_qry = "DELETE FROM subscription where studentId like '$studentId' and activityType like 'Meeting'";
		if ($conn->query($mysql_qry) === TRUE) {

		} else {
				
		}
	}
}

if($strAnnual == "1"){
	$mysql_query = "select * from subscription where studentId like '$studentId' and activityType like 'Annual General Meeting';";
	$result = mysqli_query($conn, $mysql_query);
	if(mysqli_num_rows($result) > 0) {
		
	}
	else{
		$mysql_qry = "INSERT INTO subscription (StudentID, ActivityType) VALUES ('$studentId', 'Annual General Meeting')";
		if ($conn->query($mysql_qry) === TRUE) {

		} else {

		}
	}
} else {
	$mysql_query = "select * from subscription where studentId like '$studentId' and activityType like 'Annual General Meeting';";
	$result = mysqli_query($conn, $mysql_query);
	if(mysqli_num_rows($result) > 0) {
		$mysql_qry = "DELETE FROM subscription where studentId like '$studentId' and activityType like 'Annual General Meeting'";
		if ($conn->query($mysql_qry) === TRUE) {

		} else {
				
		}
	}
}

if($strTraining == "1"){
	$mysql_query = "select * from subscription where studentId like '$studentId' and activityType like 'Training/Practice';";
	$result = mysqli_query($conn, $mysql_query);
	if(mysqli_num_rows($result) > 0) {
		
	}
	else{
		$mysql_qry = "INSERT INTO subscription (StudentID, ActivityType) VALUES ('$studentId', 'Training/Practice')";
		if ($conn->query($mysql_qry) === TRUE) {

		} else {

		}
	}
} else {
	$mysql_query = "select * from subscription where studentId like '$studentId' and activityType like 'Training/Practice';";
	$result = mysqli_query($conn, $mysql_query);
	if(mysqli_num_rows($result) > 0) {
		$mysql_qry = "DELETE FROM subscription where studentId like '$studentId' and activityType like 'Training/Practice'";
		if ($conn->query($mysql_qry) === TRUE) {

		} else {
				
		}
	}
}

if($strClass == "1"){
	$mysql_query = "select * from subscription where studentId like '$studentId' and activityType like 'Class';";
	$result = mysqli_query($conn, $mysql_query);
	if(mysqli_num_rows($result) > 0) {
		
	}
	else{
		$mysql_qry = "INSERT INTO subscription (StudentID, ActivityType) VALUES ('$studentId', 'Class')";
		if ($conn->query($mysql_qry) === TRUE) {

		} else {

		}
	}
} else {
	$mysql_query = "select * from subscription where studentId like '$studentId' and activityType like 'Class';";
	$result = mysqli_query($conn, $mysql_query);
	if(mysqli_num_rows($result) > 0) {
		$mysql_qry = "DELETE FROM subscription where studentId like '$studentId' and activityType like 'Class'";
		if ($conn->query($mysql_qry) === TRUE) {

		} else {
				
		}
	}
}

if($strGathering == "1"){
	$mysql_query = "select * from subscription where studentId like '$studentId' and activityType like 'Gathering';";
	$result = mysqli_query($conn, $mysql_query);
	if(mysqli_num_rows($result) > 0) {
		
	}
	else{
		$mysql_qry = "INSERT INTO subscription (StudentID, ActivityType) VALUES ('$studentId', 'Gathering')";
		if ($conn->query($mysql_qry) === TRUE) {

		} else {

		}
	}
} else {
	$mysql_query = "select * from subscription where studentId like '$studentId' and activityType like 'Gathering';";
	$result = mysqli_query($conn, $mysql_query);
	if(mysqli_num_rows($result) > 0) {
		$mysql_qry = "DELETE FROM subscription where studentId like '$studentId' and activityType like 'Gathering'";
		if ($conn->query($mysql_qry) === TRUE) {

		} else {
				
		}
	}
}

if($strVisit == "1"){
	$mysql_query = "select * from subscription where studentId like '$studentId' and activityType like 'Visit';";
	$result = mysqli_query($conn, $mysql_query);
	if(mysqli_num_rows($result) > 0) {
		
	}
	else{
		$mysql_qry = "INSERT INTO subscription (StudentID, ActivityType) VALUES ('$studentId', 'Visit')";
		if ($conn->query($mysql_qry) === TRUE) {

		} else {

		}
	}
} else {
	$mysql_query = "select * from subscription where studentId like '$studentId' and activityType like 'Visit';";
	$result = mysqli_query($conn, $mysql_query);
	if(mysqli_num_rows($result) > 0) {
		$mysql_qry = "DELETE FROM subscription where studentId like '$studentId' and activityType like 'Visit'";
		if ($conn->query($mysql_qry) === TRUE) {

		} else {
				
		}
	}
}

if($strTrip == "1"){
	$mysql_query = "select * from subscription where studentId like '$studentId' and activityType like 'Trip';";
	$result = mysqli_query($conn, $mysql_query);
	if(mysqli_num_rows($result) > 0) {
		
	}
	else{
		$mysql_qry = "INSERT INTO subscription (StudentID, ActivityType) VALUES ('$studentId', 'Trip')";
		if ($conn->query($mysql_qry) === TRUE) {

		} else {

		}
	}
} else {
	$mysql_query = "select * from subscription where studentId like '$studentId' and activityType like 'Trip';";
	$result = mysqli_query($conn, $mysql_query);
	if(mysqli_num_rows($result) > 0) {
		$mysql_qry = "DELETE FROM subscription where studentId like '$studentId' and activityType like 'Trip'";
		if ($conn->query($mysql_qry) === TRUE) {

		} else {
				
		}
	}
}

if($strCamp == "1"){
	$mysql_query = "select * from subscription where studentId like '$studentId' and activityType like 'Camp';";
	$result = mysqli_query($conn, $mysql_query);
	if(mysqli_num_rows($result) > 0) {
		
	}
	else{
		$mysql_qry = "INSERT INTO subscription (StudentID, ActivityType) VALUES ('$studentId', 'Camp')";
		if ($conn->query($mysql_qry) === TRUE) {

		} else {

		}
	}
} else {
	$mysql_query = "select * from subscription where studentId like '$studentId' and activityType like 'Camp';";
	$result = mysqli_query($conn, $mysql_query);
	if(mysqli_num_rows($result) > 0) {
		$mysql_qry = "DELETE FROM subscription where studentId like '$studentId' and activityType like 'Camp'";
		if ($conn->query($mysql_qry) === TRUE) {

		} else {
				
		}
	}
}

if($strPerformance == "1"){
	$mysql_query = "select * from subscription where studentId like '$studentId' and activityType like 'Performance';";
	$result = mysqli_query($conn, $mysql_query);
	if(mysqli_num_rows($result) > 0) {
		
	}
	else{
		$mysql_qry = "INSERT INTO subscription (StudentID, ActivityType) VALUES ('$studentId', 'Performance')";
		if ($conn->query($mysql_qry) === TRUE) {

		} else {

		}
	}
} else {
	$mysql_query = "select * from subscription where studentId like '$studentId' and activityType like 'Performance';";
	$result = mysqli_query($conn, $mysql_query);
	if(mysqli_num_rows($result) > 0) {
		$mysql_qry = "DELETE FROM subscription where studentId like '$studentId' and activityType like 'Performance'";
		if ($conn->query($mysql_qry) === TRUE) {

		} else {
				
		}
	}
}

if($strNite == "1"){
	$mysql_query = "select * from subscription where studentId like '$studentId' and activityType like 'Nite';";
	$result = mysqli_query($conn, $mysql_query);
	if(mysqli_num_rows($result) > 0) {
		
	}
	else{
		$mysql_qry = "INSERT INTO subscription (StudentID, ActivityType) VALUES ('$studentId', 'Nite')";
		if ($conn->query($mysql_qry) === TRUE) {

		} else {

		}
	}
} else {
	$mysql_query = "select * from subscription where studentId like '$studentId' and activityType like 'Nite';";
	$result = mysqli_query($conn, $mysql_query);
	if(mysqli_num_rows($result) > 0) {
		$mysql_qry = "DELETE FROM subscription where studentId like '$studentId' and activityType like 'Nite'";
		if ($conn->query($mysql_qry) === TRUE) {

		} else {
				
		}
	}
}

if($strTalk == "1"){
	$mysql_query = "select * from subscription where studentId like '$studentId' and activityType like 'Talk';";
	$result = mysqli_query($conn, $mysql_query);
	if(mysqli_num_rows($result) > 0) {
		
	}
	else{
		$mysql_qry = "INSERT INTO subscription (StudentID, ActivityType) VALUES ('$studentId', 'Talk')";
		if ($conn->query($mysql_qry) === TRUE) {

		} else {

		}
	}
} else {
	$mysql_query = "select * from subscription where studentId like '$studentId' and activityType like 'Talk';";
	$result = mysqli_query($conn, $mysql_query);
	if(mysqli_num_rows($result) > 0) {
		$mysql_qry = "DELETE FROM subscription where studentId like '$studentId' and activityType like 'Talk'";
		if ($conn->query($mysql_qry) === TRUE) {

		} else {
				
		}
	}
}


if($strWorkshop == "1"){
	$mysql_query = "select * from subscription where studentId like '$studentId' and activityType like 'Workshop';";
	$result = mysqli_query($conn, $mysql_query);
	if(mysqli_num_rows($result) > 0) {
		
	}
	else{
		$mysql_qry = "INSERT INTO subscription (StudentID, ActivityType) VALUES ('$studentId', 'Workshop')";
		if ($conn->query($mysql_qry) === TRUE) {

		} else {

		}
	}
} else {
	$mysql_query = "select * from subscription where studentId like '$studentId' and activityType like 'Workshop';";
	$result = mysqli_query($conn, $mysql_query);
	if(mysqli_num_rows($result) > 0) {
		$mysql_qry = "DELETE FROM subscription where studentId like '$studentId' and activityType like 'Workshop'";
		if ($conn->query($mysql_qry) === TRUE) {

		} else {
				
		}
	}
}

if($strSeminar == "1"){
	$mysql_query = "select * from subscription where studentId like '$studentId' and activityType like 'Seminar';";
	$result = mysqli_query($conn, $mysql_query);
	if(mysqli_num_rows($result) > 0) {
		
	}
	else{
		$mysql_qry = "INSERT INTO subscription (StudentID, ActivityType) VALUES ('$studentId', 'Seminar')";
		if ($conn->query($mysql_qry) === TRUE) {

		} else {

		}
	}
} else {
	$mysql_query = "select * from subscription where studentId like '$studentId' and activityType like 'Seminar';";
	$result = mysqli_query($conn, $mysql_query);
	if(mysqli_num_rows($result) > 0) {
		$mysql_qry = "DELETE FROM subscription where studentId like '$studentId' and activityType like 'Seminar'";
		if ($conn->query($mysql_qry) === TRUE) {

		} else {
				
		}
	}
}


if($strConference == "1"){
	$mysql_query = "select * from subscription where studentId like '$studentId' and activityType like 'Conference';";
	$result = mysqli_query($conn, $mysql_query);
	if(mysqli_num_rows($result) > 0) {
		
	}
	else{
		$mysql_qry = "INSERT INTO subscription (StudentID, ActivityType) VALUES ('$studentId', 'Conference')";
		if ($conn->query($mysql_qry) === TRUE) {

		} else {

		}
	}
} else {
	$mysql_query = "select * from subscription where studentId like '$studentId' and activityType like 'Conference';";
	$result = mysqli_query($conn, $mysql_query);
	if(mysqli_num_rows($result) > 0) {
		$mysql_qry = "DELETE FROM subscription where studentId like '$studentId' and activityType like 'Conference'";
		if ($conn->query($mysql_qry) === TRUE) {

		} else {
				
		}
	}
}


if($strExhibition == "1"){
	$mysql_query = "select * from subscription where studentId like '$studentId' and activityType like 'Exhibition';";
	$result = mysqli_query($conn, $mysql_query);
	if(mysqli_num_rows($result) > 0) {
		
	}
	else{
		$mysql_qry = "INSERT INTO subscription (StudentID, ActivityType) VALUES ('$studentId', 'Exhibition')";
		if ($conn->query($mysql_qry) === TRUE) {

		} else {

		}
	}
} else {
	$mysql_query = "select * from subscription where studentId like '$studentId' and activityType like 'Exhibition';";
	$result = mysqli_query($conn, $mysql_query);
	if(mysqli_num_rows($result) > 0) {
		$mysql_qry = "DELETE FROM subscription where studentId like '$studentId' and activityType like 'Exhibition'";
		if ($conn->query($mysql_qry) === TRUE) {

		} else {
				
		}
	}
}


if($strFundRaising == "1"){
	$mysql_query = "select * from subscription where studentId like '$studentId' and activityType like 'Fund Raising';";
	$result = mysqli_query($conn, $mysql_query);
	if(mysqli_num_rows($result) > 0) {
		
	}
	else{
		$mysql_qry = "INSERT INTO subscription (StudentID, ActivityType) VALUES ('$studentId', 'Fund Raising')";
		if ($conn->query($mysql_qry) === TRUE) {

		} else {

		}
	}
} else {
	$mysql_query = "select * from subscription where studentId like '$studentId' and activityType like 'Fund Raising';";
	$result = mysqli_query($conn, $mysql_query);
	if(mysqli_num_rows($result) > 0) {
		$mysql_qry = "DELETE FROM subscription where studentId like '$studentId' and activityType like 'Fund Raising'";
		if ($conn->query($mysql_qry) === TRUE) {

		} else {
				
		}
	}
}

if($strCompetition == "1"){
	$mysql_query = "select * from subscription where studentId like '$studentId' and activityType like 'Competition';";
	$result = mysqli_query($conn, $mysql_query);
	if(mysqli_num_rows($result) > 0) {
		
	}
	else{
		$mysql_qry = "INSERT INTO subscription (StudentID, ActivityType) VALUES ('$studentId', 'Competition')";
		if ($conn->query($mysql_qry) === TRUE) {

		} else {

		}
	}
} else {
	$mysql_query = "select * from subscription where studentId like '$studentId' and activityType like 'Competition';";
	$result = mysqli_query($conn, $mysql_query);
	if(mysqli_num_rows($result) > 0) {
		$mysql_qry = "DELETE FROM subscription where studentId like '$studentId' and activityType like 'Competition'";
		if ($conn->query($mysql_qry) === TRUE) {

		} else {
				
		}
	}
}

if($strSportsCarnival == "1"){
	$mysql_query = "select * from subscription where studentId like '$studentId' and activityType like 'Sports Carnival';";
	$result = mysqli_query($conn, $mysql_query);
	if(mysqli_num_rows($result) > 0) {
		
	}
	else{
		$mysql_qry = "INSERT INTO subscription (StudentID, ActivityType) VALUES ('$studentId', 'Sports Carnival')";
		if ($conn->query($mysql_qry) === TRUE) {

		} else {

		}
	}
} else {
	$mysql_query = "select * from subscription where studentId like '$studentId' and activityType like 'Sports Carnival';";
	$result = mysqli_query($conn, $mysql_query);
	if(mysqli_num_rows($result) > 0) {
		$mysql_qry = "DELETE FROM subscription where studentId like '$studentId' and activityType like 'Sports Carnival'";
		if ($conn->query($mysql_qry) === TRUE) {

		} else {
				
		}
	}
}

if($strTreasureHunt == "1"){
	$mysql_query = "select * from subscription where studentId like '$studentId' and activityType like 'Treasure Hunt';";
	$result = mysqli_query($conn, $mysql_query);
	if(mysqli_num_rows($result) > 0) {
		
	}
	else{
		$mysql_qry = "INSERT INTO subscription (StudentID, ActivityType) VALUES ('$studentId', 'Treasure Hunt')";
		if ($conn->query($mysql_qry) === TRUE) {

		} else {

		}
	}
} else {
	$mysql_query = "select * from subscription where studentId like '$studentId' and activityType like 'Treasure Hunt';";
	$result = mysqli_query($conn, $mysql_query);
	if(mysqli_num_rows($result) > 0) {
		$mysql_qry = "DELETE FROM subscription where studentId like '$studentId' and activityType like 'Treasure Hunt'";
		if ($conn->query($mysql_qry) === TRUE) {

		} else {
				
		}
	}
}


if($strOthers == "1"){
	$mysql_query = "select * from subscription where studentId like '$studentId' and activityType like 'Others';";
	$result = mysqli_query($conn, $mysql_query);
	if(mysqli_num_rows($result) > 0) {
		
	}
	else{
		$mysql_qry = "INSERT INTO subscription (StudentID, ActivityType) VALUES ('$studentId', 'Others')";
		if ($conn->query($mysql_qry) === TRUE) {

		} else {

		}
	}
} else {
	$mysql_query = "select * from subscription where studentId like '$studentId' and activityType like 'Others';";
	$result = mysqli_query($conn, $mysql_query);
	if(mysqli_num_rows($result) > 0) {
		$mysql_qry = "DELETE FROM subscription where studentId like '$studentId' and activityType like 'Others'";
		if ($conn->query($mysql_qry) === TRUE) {

		} else {
				
		}
	}
}




?>