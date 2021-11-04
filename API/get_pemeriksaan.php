<?php

    include 'connection.php';

    if($_POST) {

        $id_anak = $_POST['id_anak'];

        $query = "SELECT u.id_anak, u.nama_anak, i.id_pemeriksaan, i.tgl_pemeriksaan, i.tinggi_badan, i.berat_badan, i.imunisasi, i.vitamin, i.status_gizi, i.penyuluhan, i.id_pemeriksaan
        FROM riwayat_pemeriksaan AS i INNER JOIN anak AS u ON i.id_anak = u.id_anak WHERE i.id_anak = '$id_anak' ORDER BY i.id_pemeriksaan DESC";
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