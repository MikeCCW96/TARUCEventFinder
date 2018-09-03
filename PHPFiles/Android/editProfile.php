<?php
require "conn.php";
$studentId = $_POST["studentId"];
$nameEdit = $_POST["nameEdit"];
$contactEdit = $_POST["contactEdit"];
$addressEdit = $_POST["addressEdit"];

$mysql_query = "UPDATE studentsimple SET StudentName='$nameEdit', ContactNum='$contactEdit', Address='$addressEdit' where StudentId = '$studentId'";
$response = array();

if ($conn->query($mysql_query) === TRUE) {
    $response = array("studentId"=>$studentId,"nameEdit"=>$nameEdit,"contactEdit"=>$contactEdit,"addressEdit"=>$addressEdit);
} else {
    echo "Error updating record: " . $conn->error;
}


?>