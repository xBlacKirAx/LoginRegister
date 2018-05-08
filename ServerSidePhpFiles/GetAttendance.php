<?php
    date_default_timezone_set("America/New_York");
    $con = mysqli_connect("localhost", "id5409183_xzhou26", "QweAsdZxc123", "id5409183_testdb");
    $cID = $_POST["cID"];
    $spDate = $_POST["spDate"];
    
    $aID=array();
    $response=array();
    $response["success"] = false;
    
    
    
    $ClassTime = mysqli_fetch_assoc(mysqli_query($con, "SELECT CTime FROM Course WHERE CID = '$cID'"))["CTime"];
    $ClassTime1=strtotime($ClassTime);
        $countS=1;
        $totalStudentOfClass=array();
        $statementTotal = mysqli_prepare($con, "SELECT SID FROM CourseInfo WHERE CID = ?");
        mysqli_stmt_bind_param($statementTotal, "s", $cID);
        mysqli_stmt_execute($statementTotal);
        mysqli_stmt_store_result($statementTotal);
        mysqli_stmt_bind_result($statementTotal, $sID);
        
    while(mysqli_stmt_fetch($statementTotal)){
        $studentName=mysqli_fetch_assoc(mysqli_query($con,"SELECT SName FROM Student WHERE SID = $sID"));
        $studentSignInTime = mysqli_fetch_assoc(mysqli_query($con,"SELECT STime FROM SignInSheet WHERE SID='$sID' AND SDate='$spDate'"))["STime"];
        $cheat = mysqli_fetch_assoc(mysqli_query($con,"SELECT Cheat FROM SignInSheet WHERE SID='$sID' AND SDate='$spDate'"))["Cheat"];
        if(empty($studentSignInTime)){
            $sStatus="Absense";
        }else{
            $studentSignInTime1=strtotime($studentSignInTime);
            $interval  = abs($studentSignInTime1 - $ClassTime1);
            $minutes   = round($interval / 60, 2);
            if($minutes > 10 && $minutes <= 30){
			    $sStatus="Late";
		    }else if($minutes <= 10){
		    	$sStatus="Ontime";
		    }else{
			    $sStatus="Absense";
		    }
		    if($cheat==1){
		        $sStatus="Cheat";
		        
		    }
        }
        $totalStudentOfClass[$countS]=$sID." ".$studentName["SName"]." ".$sStatus;
        $countS=$countS+1;
    }


	    $result = array('response' => $response, 'totalStudentOfClass' => $totalStudentOfClass);
        echo json_encode($result);
?>