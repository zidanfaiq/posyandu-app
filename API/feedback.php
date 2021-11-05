<?php
include 'connection.php';

if ($_POST) {
    //POST DATA
    $kategori = $_POST['kategori'];
    $pesan = $_POST['pesan'];
    $user_id = $_POST['user_id'];

    $query = "INSERT INTO feedback (kategori, pesan, user_id)
    VALUES ('$kategori', '$pesan', '$user_id')";
    $result = $connection->prepare($query);
    $result->execute();

    if($result->rowCount()){
        $response['status']= true;
        $response['message']='Feedback telah dikirim';
    } else {
        $response['status']= false;
        $response['message']='Gagal mengirim feedback';
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