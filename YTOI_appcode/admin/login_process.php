<?php
ob_start();
session_start();
error_reporting(0);
include("include/config.inc.php");
include("class/DB.class.php");
$db = new DB();
$db->connect();

$username=$_REQUEST['txt_username'];
$password=base64_encode($_REQUEST['txt_password']);
//echo $username, $password; exit;
if($username!='' && $password!='')
{
	$query=$db->select_one("admin_master","admin_username='".mysql_real_escape_string($username)."' AND admin_password='".mysql_real_escape_string($password)."' AND admin_is_active=1");
	//$query=mysql_query("SELECT count(admin_id) as num FROM admin_master WHERE admin_username='".mysql_real_escape_string($username)."' AND admin_password='".mysql_real_escape_string($password)."' AND admin_is_active=1");
	$rows=mysql_affected_rows();
	if($rows==1)
	{
		$remote_address=$_SERVER['REMOTE_ADDR'];
		$login_in_time=date("Y-m-d H:i:s");
		$data=array("login_username"=>$username,"login_ip"=>$remote_address,"login_in_datetime"=>$login_in_time,"login_status"=>"1");
		$last_id=$db->insert($data,"coz_login_history"); 
		$_SESSION["coz_user_id"]=$query['admin_id'];
		$_SESSION["coz_username"]=$username;
		$_SESSION["coz_password"]=base64_decode($password);
		
			if(isset($_REQUEST['chk_remember']))
			{
				  setcookie("coz_username", $_SESSION['coz_username'], time()+60*60*24*100, "/");
				  setcookie("coz_password", $_SESSION['coz_password'], time()+60*60*24*100, "/");
				  
			}
			else
			{
				setcookie("coz_username", $_SESSION['coz_username'], time()-60*60*24*100, "/");
				  setcookie("coz_password", $_SESSION['coz_password'], time()-60*60*24*100, "/");				
			}
		
		 ?>
		<script language="javascript">
		window.location="<?php echo URL."home.php"; ?>";
		</script>
		
	<?php exit;	
	}
	else
	{  ?>
	<script language="javascript">
	window.location="<?php echo URL."index.php?valid=yes"; ?>";
	</script>	
	<?php exit; }
}
else
{ ?>
	<script language="javascript">
	window.location="<?php echo URL."index.php?err=yes"; ?>";
	</script>	
<?php }
?>