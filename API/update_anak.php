<?php
    include 'connection.php';

    if ($_POST) {
        $id_anak = $_POST['id_anak'];
        $nama_anak = $_POST['nama_anak'];
        $anak_ke = $_POST['anak_ke'];
        $no_akte = $_POST['no_akte'];
        $nik_anak = $_POST['nik_anak'];
        $tempat_lahir = $_POST['tempat_lahir'];
        $tgl_lahir = $_POST['tgl_lahir'];
        $jenis_kelamin = $_POST['jenis_kelamin'];
        $gol_darah = $_POST['gol_darah'];

        $userQuery = $connection->prepare("SELECT * FROM anak WHERE id_anak != '$id_anak' AND nik_anak = ?");
        $userQuery->execute(array($nik_anak));

        if($userQuery->rowCount() != 0){
            $response['status']= false;
            $response['message']='NIK sudah digunakan';
        } else {
			$query = "UPDATE anak SET nama_anak = '$nama_anak', anak_ke = '$anak_ke', no_akte = '$no_akte', nik_anak = '$nik_anak', tempat_lahir = '$tempat_lahir', tgl_lahir = '$tgl_lahir', jenis_kelamin = '$jenis_kelamin', gol_darah = '$gol_darah' WHERE id_anak = '$id_anak'";
            $result = $connection->prepare($query);
            $result->execute();
			
			if ($result->rowCount()) {
				$response['status'] = true;
				$response['message'] = "Update data berhasil";
            
			} else {
				$response['status'] = false;
				$response['message'] = "Update data gagal";
			}
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