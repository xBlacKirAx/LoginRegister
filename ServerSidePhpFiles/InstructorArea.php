<?php
    date_default_timezone_set("America/New_York");
    $date = date("Y-m-d");
    $time = date("H:i:s");
    $con = mysqli_connect("localhost", "id5409183_xzhou26", "QweAsdZxc123", "id5409183_testdb");
    $cID = $_POST["cID"];
    $verificationCode = $_POST["verificationCode"];

    $statement = mysqli_prepare($con, "INSERT INTO VerificationCode (CID, VerificationCode,Date) VALUES (?, ?,?)");
    mysqli_stmt_bind_param($statement, "sss", $cID, $verificationCode,$date);
   
    
    $response = array();
    $response["success"] = false;  
    $response["response"]=" ";
    if( mysqli_stmt_execute($statement)){
        $response["success"] = true;
    }else{
        $response["code"]=mysqli_fetch_assoc(mysqli_query($con,"SELECT VerificationCode FROM VerificationCode WHERE CID='$cID' AND Date='$date'"))["VerificationCode"];
        
       
    }
    
    echo json_encode($response);
?>