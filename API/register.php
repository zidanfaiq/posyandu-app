<?php

include 'connection.php';

if($_POST){

    //POST DATA
    $nama_ibu = filter_input(INPUT_POST, 'nama_ibu', FILTER_SANITIZE_STRING);
    $nik_ibu = filter_input(INPUT_POST, 'nik_ibu', FILTER_SANITIZE_STRING);
    $tempat_lahir = filter_input(INPUT_POST, 'tempat_lahir', FILTER_SANITIZE_STRING);
    $tgl_lahir = filter_input(INPUT_POST, 'tgl_lahir', FILTER_SANITIZE_STRING);
    $alamat = filter_input(INPUT_POST, 'alamat', FILTER_SANITIZE_STRING);
    $posyandu = filter_input(INPUT_POST, 'posyandu', FILTER_SANITIZE_STRING);
    $telepon = filter_input(INPUT_POST, 'telepon', FILTER_SANITIZE_STRING);
    $email = filter_input(INPUT_POST, 'email', FILTER_SANITIZE_STRING);
    $password = filter_input(INPUT_POST, 'password', FILTER_SANITIZE_STRING);

    $response = [];

    //Cek nik, telepon, email didalam databse
    $userQuery = $connection->prepare("SELECT * FROM user where nik_ibu = ?");
    $userQuery->execute(array($nik_ibu));

    $userQuery2 = $connection->prepare("SELECT * FROM user where telepon = ?");
    $userQuery2->execute(array($telepon));

    $userQuery3 = $connection->prepare("SELECT * FROM user where email = ?");
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
        $insertAccount = 'INSERT INTO user (nama_ibu, nik_ibu, tempat_lahir, tgl_lahir, alamat, posyandu, telepon, email, password)
        values (:nama_ibu, :nik_ibu, :tempat_lahir, :tgl_lahir, :alamat, :posyandu, :telepon, :email, :password)';
        $statement = $connection->prepare($insertAccount);

        try{
            //Eksekusi statement db
            $statement->execute([
                ':nama_ibu' => $nama_ibu,
                ':nik_ibu' => $nik_ibu,
                ':tempat_lahir' => $tempat_lahir,
                ':tgl_lahir' => $tgl_lahir,
                ':alamat' => $alamat,
                ':posyandu' => $posyandu,
                ':telepon' => $telepon,
                ':email' => $email,
                ':password' => md5($password)
            ]);

            //Beri response
            $response['status']= true;
            $response['message']='Akun berhasil didaftar';
            $response['data'] = [
                'nama_ibu' => $nama_ibu,
                'nik_ibu' => $nik_ibu,
                'tempat_lahir' => $tempat_lahir,
                'tgl_lahir' => $tgl_lahir,
                'alamat' => $alamat,
                'posyandu' => $posyandu,
                'telepon' => $telepon,
                'email' => $email
            ];
        } catch (Exception $e){
            die($e->getMessage());
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