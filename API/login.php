<?php

include 'connection.php';

if($_POST){

    //Data
    $email = $_POST['email'] ?? '';
    $password = $_POST['password'] ?? '';

    $response = []; //Data Response

    //Cek username didalam databse
    $userQuery = $connection->prepare("SELECT * FROM user where email = ?");
    $userQuery->execute(array($email));
    $query = $userQuery->fetch();

    if($userQuery->rowCount() == 0){
        $response['status'] = false;
        $response['message'] = "Email Tidak Terdaftar";
    } else {
        // Ambil password di db

        $passwordDB = $query['password'];

        if(strcmp(md5($password),$passwordDB) === 0){
            $response['status'] = true;
            $response['message'] = "Login Berhasil";
            $response['data'] = [
                'user_id' => $query['user_id'],
                'nama_ibu' => $query['nama_ibu'],
                'nik_ibu' => $query['nik_ibu'],
                'tempat_lahir' => $query['tempat_lahir'],
                'tgl_lahir' => $query['tgl_lahir'],
                'alamat' => $query['alamat'],
                'posyandu' => $query['posyandu'],
                'telepon' => $query['telepon'],
                'email' => $query['email']
            ];
        } else {
            $response['status'] = false;
            $response['message'] = "Password anda salah";
        }
    }
}
else {
    $response['status']= false;
    $response['message']='Tidak ada post data';
}

//Jadikan data JSON
$json = json_encode($response, JSON_PRETTY_PRINT);

//Print
echo $json;
?>