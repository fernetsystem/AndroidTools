<?php 
$serial = $_POST['sendSerial'];
$brand = $_POST['sendBrand'];
$device = $_POST['sendDevice'];
$id = $_POST['sendID'];
$manf = $_POST['sendManf'];
$prod =$_POST['sendProduct'];
$imei = $_POST['sendIMEI'];

		$contador = 0;
		$conexion = mysqli_connect('localhost','u858464654_fer','w9w9dorotea','u858464654_bdrem');
		$q1 = "insert into registro_datos values('$serial','$brand','$device','$id','$manf','$prod','$imei',now())";
		mysqli_query($conexion,$q1);
		$q = "select * from devices where SSERIAL='$serial' and BRAND='$brand' and DEVICE='$device' and ID='$id' and MANUFACTURER='$manf' and PRODUCT='$prod' and IMEI='$imei'";
		$resultado = mysqli_query($conexion,$q);
		while ($fila = mysqli_fetch_array($resultado,MYSQLI_ASSOC)) {
			$contador++;
		}
		mysqli_close($conexion);


if ($contador == 1) {
	 $arr = array('aceptado'=>'VALIDO');	

}else{
	$arr = array('aceptado'=>'NO');	
}

echo json_encode($arr);
 ?>