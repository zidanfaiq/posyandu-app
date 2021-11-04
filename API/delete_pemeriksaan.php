<?php
    include 'connection.php';

    if ($_POST) {
        $id_pemeriksaan = $_POST['id_pemeriksaan'];

        $query = "DELETE FROM riwayat_pemeriksaan WHERE id_pemeriksaan = '$id_pemeriksaan'";
        $result = $connection->prepare($query);
        $result->execute();

        if ($result->rowCount()) {
            $response['status'] = true;
            $response['message'] = "Hapus data Berhasil";
        } else {
            $response['status'] = false;
            $response['message'] = "Gagal menghapus data";
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