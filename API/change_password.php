<?php
include 'connection.php';

if ($_POST) {
    //POST DATA
    $user_id = $_POST['user_id'];
    $password = $_POST['password'];

    $query = "UPDATE user SET password = md5('$password') WHERE user_id = '$user_id'";
    $result = $connection->prepare($query);
    $result->execute();

    if($result->rowCount()){
        $response['status']= true;
        $response['message']='Password berhasil diubah';
    } else {
        $response['status']= false;
        $response['message']='Password masih sama';
    }
}
else {
    $response['status']= false;
    $response['message']='Tidak ada post data';
}

//Jadikan data JSON
$json = json_encode($response, JSON_PRETTY_PRINT);

//Print JSON
echo $json;
?>