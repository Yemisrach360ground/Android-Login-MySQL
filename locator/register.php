<?php

$conn = mysqli_connect("192.168.230.1","root","","locator");
mysqli_select_db($conn,"locator"); 

$name = $_REQUEST['Name'];
$mobile = $_REQUEST['Mobile'];
$pass = $_REQUEST['Password'];
$p = hash('sha512', $pass);

$exists['Code'] = 0;

$ex = mysqli_query($conn,"SELECT * FROM user_details where Mobile='$mobile'");
$rows = mysqli_num_rows($ex);

if($rows>0)
{
	$exists['Code']=1;
}
else
{
	$r = "INSERT INTO `user_details`(`Name`,`Mobile`,`Password`) VALUES ('$name','$mobile','$p')";
	$flag['code'] = 0;
	if (mysqli_query($conn, $r))
 	{
    	$flag['code'] = 1;
 	}
}
	print(json_encode($exists));
    mysqli_close($conn);
?>