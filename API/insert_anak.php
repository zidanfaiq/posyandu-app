<?php
include 'connection.php';

if ($_POST) {
    //POST DATA
    $nama_anak = $_POST['nama_anak'];
    $anak_ke = $_POST['anak_ke'];
    $no_akte = $_POST['no_akte'];
    $nik_anak = $_POST['nik_anak'];
    $tempat_lahir = $_POST['tempat_lahir'];
    $tgl_lahir = $_POST['tgl_lahir'];
    $jenis_kelamin = $_POST['jenis_kelamin'];
    $gol_darah = $_POST['gol_darah'];
    $user_id = $_POST['user_id'];

    $userQuery = $connection->prepare("SELECT * FROM anak where nik_anak = ?");
    $userQuery->execute(array($nik_anak));

    if($userQuery->rowCount() != 0) {
        $response['status']= false;
        $response['message']='NIK sudah digunakan';
    }
    else if($userQuery->rowCount() == 0){
        
        $insertAnak = "INSERT INTO anak (nama_anak, anak_ke, no_akte, nik_anak, tempat_lahir, tgl_lahir, jenis_kelamin, gol_darah, user_id)
        VALUES ('$nama_anak', '$anak_ke', '$no_akte', '$nik_anak', '$tempat_lahir', '$tgl_lahir', '$jenis_kelamin', '$gol_darah', '$user_id')";
        $result = $connection->prepare($insertAnak);
        $result->execute();
        $response['status']= true;
        $response['message']='Data anak telah ditambahkan';
            
    } else {
        $response['status']= false;
        $response['message']='Insert data gagal';
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