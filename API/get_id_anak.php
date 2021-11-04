<?php
    include 'connection.php';

    if ($_POST) {
        $id_anak = $_POST['id_anak'];

        $query = "SELECT * FROM anak WHERE id_anak = '$id_anak'";
        $result = $connection->prepare($query);
        $result->execute();

        if ($result->rowCount()) {
            $response['status'] = true;
            $response['message'] = "Data tersedia";
            $response['data'] = array();
            while ($row = $result->fetch(PDO::FETCH_ASSOC)) {
                array_push($response['data'], array(
                    'id_anak' => $row['id_anak'],
                    'nama_anak' => $row['nama_anak'],
                    'anak_ke' => $row['anak_ke'],
                    'no_akte' => $row['no_akte'],
                    'nik_anak' => $row['nik_anak'],
                    'tempat_lahir' => $row['tempat_lahir'],
                    'tgl_lahir' => $row['tgl_lahir'],
                    'jenis_kelamin' => $row['jenis_kelamin'],
                    'gol_darah' => $row['gol_darah'],
                    'user_id' => $row['user_id']
                ));
            }
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