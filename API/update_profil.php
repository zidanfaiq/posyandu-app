<?php

include 'connection.php';

if($_POST){

    //POST DATA
    $user_id = $_POST['user_id'];
    $nama_ibu = $_POST['nama_ibu'];
    $nik_ibu = $_POST['nik_ibu'];
    $tempat_lahir = $_POST['tempat_lahir'];
    $tgl_lahir = $_POST['tgl_lahir'];
    $alamat = $_POST['alamat'];
    $posyandu = $_POST['posyandu'];
    $telepon = $_POST['telepon'];
    $email = $_POST['email'];

    $response = [];

    //Cek nik, telepon, email didalam databse
    $userQuery = $connection->prepare("SELECT * FROM user WHERE user_id != '$user_id' AND nik_ibu = ?");
    $userQuery->execute(array($nik_ibu));

    $userQuery2 = $connection->prepare("SELECT * FROM user WHERE user_id != '$user_id' AND telepon = ?");
    $userQuery2->execute(array($telepon));

    $userQuery3 = $connection->prepare("SELECT * FROM user WHERE user_id != '$user_id' AND email = ?");
    $userQuery3->execute(array($email));

    // Cek nik, telepon dan email apakah ada atau tidak
    if($userQuery->rowCount() != 0){
        // Beri Response
        $response['status']= false;
        $response['message']='NIK sudah digunakan';
    }
    else if($userQuery2->rowCount() != 0){
        // Beri Response
        $response['status']= false;
        $response['message']='Telepon sudah digunakan';
    }
    else if($userQuery3->rowCount() != 0){
        // Beri Response
        $response['status']= false;
        $response['message']='Email sudah digunakan';
    } else {
        $updateAccount = "UPDATE user SET nama_ibu = '$nama_ibu', nik_ibu = '$nik_ibu', tempat_lahir = '$tempat_lahir', tgl_lahir = '$tgl_lahir', alamat = '$alamat', posyandu = '$posyandu', telepon = '$telepon', email = '$email' WHERE user_id = '$user_id'";
        $statement = $connection->prepare($updateAccount);
        $statement->execute();
        
        if ($statement->rowCount()) {
            //Beri response
            $response['status']= true;
            $response['message']='Profil berhasil diubah';
            $response['data'] = [
                'user_id' => $user_id,
                'nama_ibu' => $nama_ibu,
                'nik_ibu' => $nik_ibu,
                'tempat_lahir' => $tempat_lahir,
                'tgl_lahir' => $tgl_lahir,
                'alamat' => $alamat,
                'posyandu' => $posyandu,
                'telepon' => $telepon,
                'email' => $email
            ];
        } else {
            $response['status'] = false;
            $response['message'] = "Update data gagal";
        }
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