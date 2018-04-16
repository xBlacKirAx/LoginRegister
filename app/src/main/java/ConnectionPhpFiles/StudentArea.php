<?php
    
    $con = mysqli_connect("localhost", "id5409183_xzhou26", "QweAsdZxc123", "id5409183_testdb");
	$sID=$_POST["sID"];
    $cID = $_POST["cID"];
    $verificationCode = $_POST["verificationCode"];

    
    $statement = mysqli_prepare($con, "INSERT INTO SignInSheet (SID, CID, SDate,STIME,VerificationCode) VALUES (?, ?,CURRENT_DATE,CURRENT_TIME,?)");
    mysqli_stmt_bind_param($statement, "sss", $sID,$cID, $verificationCode);
   
    
    $response = array();
    $response["success"] = false;  
    if( mysqli_stmt_execute($statement)){
        $response["success"] = true;
    }else{
        
    }
    
    echo json_encode($response);
?>