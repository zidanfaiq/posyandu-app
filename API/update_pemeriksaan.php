<?php
include 'connection.php';

if ($_POST) {
    //POST DATA
    $id_pemeriksaan = $_POST['id_pemeriksaan'];
    $tgl_pemeriksaan = $_POST['tgl_pemeriksaan'];
    $tinggi_badan = $_POST['tinggi_badan'];
    $berat_badan = $_POST['berat_badan'];
    $imunisasi = $_POST['imunisasi'];
    $vitamin = $_POST['vitamin'];
    $status_gizi = $_POST['status_gizi'];
    $penyuluhan = $_POST['penyuluhan'];

    $query = "UPDATE riwayat_pemeriksaan SET tgl_pemeriksaan = '$tgl_pemeriksaan', tinggi_badan = '$tinggi_badan', berat_badan = '$berat_badan', imunisasi = '$imunisasi', vitamin = '$vitamin', status_gizi = '$status_gizi', penyuluhan = '$penyuluhan' WHERE id_pemeriksaan = '$id_pemeriksaan'";
    $result = $connection->prepare($query);
    $result->execute();

    if($result->rowCount()){
        $response['status']= true;
        $response['message']='Update data berhasil';
    } else {
        $response['status']= false;
        $response['message']='Update data gagal';
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