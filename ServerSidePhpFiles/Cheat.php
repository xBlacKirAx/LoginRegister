<?php
    date_default_timezone_set("America/New_York");
    $time = date("H:i:s");
    $oDate = date("Y-m-d");
    
    $date = strtotime($oDate);
    $con = mysqli_connect("localhost", "id5409183_xzhou26", "QweAsdZxc123", "id5409183_testdb");
	$sID=$_POST["sID"];
    $cID = $_POST["cID"];
    
    $sID2=$_POST["sID2"];
    $cID2=$_POST["cID2"];
    $response = array();
    $response["success"] = false;  
    
    $statement = mysqli_query($con, "UPDATE SignInSheet SET Cheat =True WHERE SID= '$sID' AND CID='$cID' AND SDate ='$oDate'");
    
    if($statement){
           
        $response["success"] = true;  
    }
    
    $statement2 = mysqli_query($con, "UPDATE SignInSheet SET Cheat =True WHERE SID= '$sID2' AND CID='$cID2' AND SDate ='$oDate'");


    
    if($statement2){
           
        $response["success"] = true;  
    }
	



    echo json_encode($response);

?>