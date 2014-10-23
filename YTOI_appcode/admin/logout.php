<?php
session_start();
include("include/config.inc.php");
include("class/DB.class.php");
$db = new DB();
$db->connect();

$data=array("login_status"=>"0");
$db->update($data,"coz_login_history","login_ip='".$_SERVER['REMOTE_ADDR']."' AND login_status=1");
$_SESSION["coz_user_id"]="";
$_SESSION["coz_username"]="";
$_SESSION["coz_password"]="";
?>
<script language="javascript">
window.location='<?php echo URL."index.php"; ?>';
</script>