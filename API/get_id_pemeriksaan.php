<?php

    include 'connection.php';

    if($_POST) {

        $id_pemeriksaan = $_POST['id_pemeriksaan'];

        $query = "SELECT * FROM riwayat_pemeriksaan WHERE id_pemeriksaan = '$id_pemeriksaan'";
        $result = $connection->prepare($query);
        $result->execute();
        
        if ($result->rowCount()) {
            $response['status'] = true;
            $response['message'] = "Menampilkan data berhasil";
            $response['data'] = array();
            while ($row = $result->fetch(PDO::FETCH_ASSOC)) {
                array_push($response['data'], array(
                    'id_pemeriksaan' => $row['id_pemeriksaan'],
                    'tgl_pemeriksaan' => $row['tgl_pemeriksaan'],
                    'tinggi_badan' => $row['tinggi_badan'],
                    'berat_badan' => $row['berat_badan'],
                    'imunisasi' => $row['imunisasi'],
                    'vitamin' => $row['vitamin'],
                    'status_gizi' => $row['status_gizi'],
                    'penyuluhan' => $row['penyuluhan'],
                    'id_anak' => $row['id_anak'],
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