<?php
session_start();
include("include/config.inc.php");
include("class/DB.class.php");
include('session_check.php');
$db = new DB();
$db->connect();

$old_pass=base64_encode($_REQUEST['old_pass']);
$new_pass=base64_encode($_REQUEST['new_pass']);
if($old_pass!='' && $new_pass!='')
{
	$db->select_one("admin_master","admin_id='".$_SESSION["coz_user_id"]."' AND admin_is_active =1 AND admin_password='".$old_pass."'");
	$rows=mysql_affected_rows();
	if($rows==1)
	{
		
		$data=array("admin_password"=>$new_pass);
		$db->update($data,"admin_master","admin_id='".$_SESSION["coz_user_id"]."'");
		//echo "Password updated successfully !!";
		$msg=1;
	}	
	else
	{
		//echo "Please enter valid old password !!";
		$msg=2;
	}
}
else
{
	//echo "Please enter required field first !!";	
	$msg=3;
}
?>
<script language="javascript">
window.location='home.php?p=10&msg=<?php echo $msg; ?>';
</script>
