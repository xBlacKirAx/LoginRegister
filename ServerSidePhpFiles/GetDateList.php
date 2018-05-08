<?php
    $con = mysqli_connect("localhost", "id5409183_xzhou26", "QweAsdZxc123", "id5409183_testdb");
    $cID = $_POST["cID"];
    $aID=array();
    $response=array();
    $response["success"] = false;
    
         $statementS = mysqli_prepare($con, "SELECT Date FROM VerificationCode WHERE CID = ?");
        mysqli_stmt_bind_param($statementS, "s", $cID);
        mysqli_stmt_execute($statementS);

        mysqli_stmt_store_result($statementS);
        mysqli_stmt_bind_result($statementS, $Date);

        $count =1;
	while(mysqli_stmt_fetch($statementS)){
	    $response["success"] = true ;
	   
        $aID["'$count'"]=$Date;
        $count=$count+1;
	}    

	    $result = array('response' => $response, 'aID' => $aID);
    echo json_encode($result);
?>