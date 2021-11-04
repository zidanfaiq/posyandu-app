<?php
    include 'connection.php';

    if ($_POST) {
        $user_id = $_POST['user_id'];

        $query = "SELECT * FROM user WHERE user_id ='$user_id'";
        $result = $connection->prepare($query);
        $result->execute();
        $row = $result->fetch(); 

        if ($result->rowCount()) {
            $response['status'] = true;
            $response['message'] = "Data tersedia";
            $response['data'] = [
                'user_id' => $row['user_id'],
                'nama_ibu' => $row['nama_ibu'],
                'nik_ibu' => $row['nik_ibu'],
                'tempat_lahir' => $row['tempat_lahir'],
                'tgl_lahir' => $row['tgl_lahir'],
                'alamat' => $row['alamat'],
                'posyandu' => $row['posyandu'],
                'telepon' => $row['telepon'],
                'email' => $row['email']
            ];
        } else {
            $response['status'] = false;
            $response['message'] = "Data tidak ada";
        }
    }
    else {
        $response['status'] = false;
        $response['message'] = "Tidak ada post data";
    }
    
    //Jadikan data JSON
    $json = json_encode($response, JSON_PRETTY_PRINT);

    //Print JSON
    echo $json;
?>