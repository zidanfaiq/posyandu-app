<?php
    include 'connection.php';

    if ($_POST) {
        $id_anak = $_POST['id_anak'];

        $query = "DELETE anak, riwayat_pemeriksaan FROM anak INNER JOIN riwayat_pemeriksaan
        WHERE anak.id_anak = riwayat_pemeriksaan.id_anak AND anak.id_anak = '$id_anak'";
        $result = $connection->prepare($query);
        $result->execute();

        if ($result->rowCount() != 0) {
            $response['status'] = true;
            $response['message'] = "Hapus data Berhasil";
        }
        else if($result->rowCount() == 0){
			$deleteQuery = "DELETE FROM anak WHERE id_anak = '$id_anak'";
			$hasil = $connection->prepare($deleteQuery);
			$hasil->execute();
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