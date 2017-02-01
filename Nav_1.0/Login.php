<?php
    $con = mysqli_connect("localhost", "id676679_raytek", "kaka123", "id676679_andnav");
    
    $userName = $_POST["userName"];
    $password = $_POST["password"];
    
    $statement = mysqli_prepare($con, "SELECT * FROM user WHERE userName = ? AND password = ?");
    mysqli_stmt_bind_param($statement, "ss", $userName, $password);
    mysqli_stmt_execute($statement);
    
    mysqli_stmt_store_result($statement);
    mysqli_stmt_bind_result($statement, $user_id, $userName, $password);
    
    $response = array();
    $response["success"] = false;  
    
    while(mysqli_stmt_fetch($statement)){
        $response["success"] = true;  
        $response["userName"] = $userName;
        $response["password"] = $password;
    }
    
    echo json_encode($response);
?>
