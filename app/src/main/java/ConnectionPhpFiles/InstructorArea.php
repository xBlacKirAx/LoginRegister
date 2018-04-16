<?php
    
    $con = mysqli_connect("localhost", "id5409183_xzhou26", "QweAsdZxc123", "id5409183_testdb");
    $cID = $_POST["cID"];
    $verificationCode = $_POST["verificationCode"];
    $date=$_POST["date"];
    
    $statement = mysqli_prepare($con, "INSERT INTO VerificationCode (CID, VerificationCode,Date) VALUES (?, ?,CURRENT_DATE)");
    mysqli_stmt_bind_param($statement, "ss", $cID, $verificationCode);
   
    
    $response = array();
    $response["success"] = false;  
    if( mysqli_stmt_execute($statement)){
        $response["success"] = true;
    }else{
        $response["code"]=mysqli_fetch_assoc(mysqli_query($con,"SELECT VerificationCode FROM VerificationCode WHERE CID='$cID' AND Date=CURRENT_DATE"))["VerificationCode"];
        
       
    }
    
    echo json_encode($response);
?>