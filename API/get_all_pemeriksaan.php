<?php

    include 'connection.php';

    if($_POST) {

        $user_id = $_POST['user_id'];

        $query = "SELECT u.id_anak, u.nama_anak, i.id_pemeriksaan, i.tgl_pemeriksaan, i.tinggi_badan, i.berat_badan, i.imunisasi, i.vitamin, i.status_gizi, i.penyuluhan
        FROM anak AS u INNER JOIN riwayat_pemeriksaan AS i ON u.id_anak = i.id_anak WHERE u.user_id = '$user_id' ORDER BY i.id_pemeriksaan DESC";
        $result = $connection->prepare($query);
        $result->execute();
        
        if ($result->rowCount()) {
            $response['status'] = true;
            $response['message'] = "Menampilkan data berhasil";
            $response['data'] = array();
            while ($row = $result->fetch(PDO::FETCH_ASSOC)) {
                array_push($response['data'], array(
                    'id_anak' => $row['id_anak'],
                    'nama_anak' => $row['nama_anak'],
                    'id_pemeriksaan' => $row['id_pemeriksaan'],
                    'tgl_pemeriksaan' => $row['tgl_pemeriksaan'],
                    'tinggi_badan' => $row['tinggi_badan'],
                    'berat_badan' => $row['berat_badan'],
                    'imunisasi' => $row['imunisasi'],
                    'vitamin' => $row['vitamin'],
                    'status_gizi' => $row['status_gizi'],
                    'penyuluhan' => $row['penyuluhan']
                ));
            }
        } else {
            $response['status'] = false;
            $response['message'] = "Tidak ada data";
        }
    }
    else {
        $response['status'] = false;
        $response['message'] = "Tidak ada post data";
    }

    $json = json_encode($response, JSON_PRETTY_PRINT);
    echo $json;

?>