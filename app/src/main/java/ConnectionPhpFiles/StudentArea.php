<?php
    
    $con = mysqli_connect("localhost", "id5409183_xzhou26", "QweAsdZxc123", "id5409183_testdb");
	$sID=$_POST["sID"];
    $cID = $_POST["cID"];
    $verificationCode = $_POST["verificationCode"];
    $S_langtitude = $_POST["langtitude"];
    $S_longtitude = $_POST["longtitude"];

    $D_langtitude = (double)$S_langtitude;
    $D_longtitude = (double)$S_longtitude;

    //M
    $M_latitude = 40.769760;    //North
    $M_longitude = -73.982592;
    //OW
    $OW_latitude = 40.814370;  //North
    $OW_longitude = -73.607518;

    $distance = -1;


    //Get course location
    $course_CampusID = mysqli_fetch_assoc(mysqli_query($con, "SELECT CampusID FROM Course WHERE CID='$cID'))["CampusID"];
    //Get distance
    if($course_CampusID == "M"){
        $distance = getDistance(D_longtitude, D_langtitude, $M_longitude, $M_latitude);
    }
    else{
        $distance = getDistance(D_longtitude, D_langtitude, $OW_longitude, $OW_latitude);
    }





    //Insert a record
    $statement = mysqli_prepare($con, "INSERT INTO SignInSheet (SID, CID, SDate,STIME,VerificationCode) VALUES (?, ?,CURRENT_DATE,CURRENT_TIME,?)");
    mysqli_stmt_bind_param($statement, "sss", $sID,$cID, $verificationCode);

    $iverificationCode = mysqli_fetch_assoc(mysqli_query($con,"SELECT VerificationCode FROM VerificationCode WHERE CID='$cID' AND Date=CURRENT_DATE"))["VerificationCode"];
    $response = array();
    $response["success"] = false;

    //distance < 100 m then success also the verificationCode should be right
	if($verificationCode==$iverificationCode && $distance<100){
		if( mysqli_stmt_execute($statement)){
			$response["success"] = true;
			$response["response"]=" ";
		}else{
			$response["response"]="You have already signned in for this class today.";
		}
	}else{
		$response["response"]="Wrong verification code!";

	}

    echo json_encode($response);

    /**
     * @param  Decimal $longitude1 start
     * @param  Decimal $latitude1  start
     * @param  Decimal $longitude2 end
     * @param  Decimal $latitude2  end
     * @param  Int     $unit       1:m 2:km
     * @param  Int     $decimal
     * @return Decimal
     */
    function getDistance($longitude1, $latitude1, $longitude2, $latitude2, $unit=1, $decimal=2){
        $EARTH_RADIUS = 6370.996; // 地球半径系数
        $PI = 3.1415926;
        $radLat1 = $latitude1 * $PI / 180.0;
        $radLat2 = $latitude2 * $PI / 180.0;
        $radLng1 = $longitude1 * $PI / 180.0;
        $radLng2 = $longitude2 * $PI /180.0;
        $a = $radLat1 - $radLat2;
        $b = $radLng1 - $radLng2;
        $distance = 2 * asin(sqrt(pow(sin($a/2),2) + cos($radLat1) * cos($radLat2) * pow(sin($b/2),2)));
        $distance = $distance * $EARTH_RADIUS * 1000;
        if($unit==2){
            $distance = $distance / 1000;
        }
        return round($distance, $decimal);
    }

?>