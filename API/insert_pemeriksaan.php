<?php
include 'connection.php';

if ($_POST) {
    //POST DATA
    $tgl_pemeriksaan = $_POST['tgl_pemeriksaan'];
    $tinggi_badan = $_POST['tinggi_badan'];
    $berat_badan = $_POST['berat_badan'];
    $imunisasi = $_POST['imunisasi'];
    $vitamin = $_POST['vitamin'];
    $status_gizi = $_POST['status_gizi'];
    $penyuluhan = $_POST['penyuluhan'];
    $id_anak = $_POST['id_anak'];

    $query = "INSERT INTO riwayat_pemeriksaan (tgl_pemeriksaan, tinggi_badan, berat_badan, imunisasi, vitamin, status_gizi, penyuluhan, id_anak)
    VALUES ('$tgl_pemeriksaan', '$tinggi_badan', '$berat_badan', '$imunisasi', '$vitamin', '$status_gizi', '$penyuluhan', '$id_anak')";
    $result = $connection->prepare($query);
    $result->execute();

    if($result->rowCount()){
        $response['status']= true;
        $response['message']='Data pemeriksaan telah ditambahkan';
    } else {
        $response['status']= false;
        $response['message']='Insert data gagal';
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