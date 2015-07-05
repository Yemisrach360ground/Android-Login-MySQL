<?php

$conn = mysqli_connect("192.168.230.1","root","","locator");
mysqli_select_db($conn,"locator");

$mob = $_REQUEST['Mobile'];
$pass = $_REQUEST['Password'];
$p = hash('sha512', $pass);
$sql = "SELECT * FROM user_details WHERE Mobile = '$mob'";

$flag['Password'] = 0;
$r = mysqli_query($conn,$sql);
$row = mysqli_fetch_assoc($r);

if($row['Password'] == $p)
	$flag['Password'] = 1;

print(json_encode($flag));
mysqli_close($conn);

?>