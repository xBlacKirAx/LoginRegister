<?php
    $con = mysqli_connect("localhost", "id5409183_xzhou26", "QweAsdZxc123", "id5409183_testdb");
    
    $username = $_POST["SID"];
    $password = $_POST["password"];
    
    $statementS = mysqli_prepare($con, "SELECT * FROM Student WHERE SID = ? AND SPassword = ?");
    mysqli_stmt_bind_param($statementS, "ss", $username, $password);
    mysqli_stmt_execute($statementS);
    
    mysqli_stmt_store_result($statementS);
    mysqli_stmt_bind_result($statementS, $SID, $SName,  $SEmail, $SPassword);
    
    $response = array();
    $response["Ssuccess"] = false;  
    
    while(mysqli_stmt_fetch($statementS)){
        $response["Ssuccess"] = true;  
        $response["ID"] = $SID;
        $response["Name"] = $SName;
        $response["Email"] = $SEmail;
        $response["Password"] = $SPassword;
        
    }
    
    
	$statementI = mysqli_prepare($con, "SELECT * FROM Instructor WHERE IID = ? AND IPassword = ?");
    mysqli_stmt_bind_param($statementI, "ss", $username, $password);
    mysqli_stmt_execute($statementI);
    
    mysqli_stmt_store_result($statementI);
    mysqli_stmt_bind_result($statementI, $IID, $IName,  $IEmail, $IPassword);
    
     
    $response["Isuccess"]=false;
    while(mysqli_stmt_fetch($statementI)){
        $response["Isuccess"] = true;  
        $response["ID"] = $IID;
        $response["Name"] = $IName;
        $response["Email"] = $IEmail;
        $response["Password"] = $IPassword;
    }
	
    
    $response2=array();
    $cID=array();
    $cName=array();
    if($response["Ssuccess"]){
        $statement2=mysqli_prepare($con,"SELECT * FROM CourseInfo WHERE SID=?");
    }else if($response["Isuccess"]){
        $statement2=mysqli_prepare($con,"SELECT * FROM TeachInfo WHERE IID=?");
    }
    
    mysqli_stmt_bind_param($statement2,"s",$response["ID"]);
    mysqli_stmt_execute($statement2);
    mysqli_stmt_store_result($statement2);
    mysqli_stmt_bind_result($statement2,$SID,$CID);
    $count =1;
    
	while(mysqli_stmt_fetch($statement2)){
		
		
		$statement3=mysqli_query($con,"SELECT CName FROM Course WHERE CID='$CID'");
        $row=mysqli_fetch_assoc($statement3);
        $response2["'$count'"]=$CID.$row["CName"];
        $cID["'$count'"]=$CID;
        $cName["'$count'"]=$row["CName"];
        $count=$count+1;
	}    

	
	$result=array('response'=>$response,'response2'=>$response2,'cID'=>$cID,'cName'=>$cName);
    
    echo json_encode($result);
?>