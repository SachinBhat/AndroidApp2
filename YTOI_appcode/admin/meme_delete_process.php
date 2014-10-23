<?php
session_start();
include("include/config.inc.php");
include("class/DB.class.php");
include('session_check.php');
$db = new DB();
$db->connect();

$chk=$_POST["chk"];
if(isset($_REQUEST["delete"]))
{
	$chk=$_POST["chk"];
	foreach($chk as $val)
	{		
		$query=$db->select_one("meme_master","meme_id='".$val."'");							
		$db->delete("meme_master","meme_id='".$val."'");	
		
	}
?>
	<script language="javascript">
	window.location='home.php?p=11&msg=delete';
	</script>
<?php
exit;
}
?>

<?php
if(isset($_REQUEST["active"]))
{
	foreach($chk as $val)
	{
		$data=array("status"=>"1");
		$db->update($data,"meme_master","meme_id='".$val."'");
	}
?>
	<script language="javascript">
	window.location='home.php?p=11&msg=active';
	</script>
<?php
exit;
}
?>

<?php
if(isset($_REQUEST["inactive"]))
{
	foreach($chk as $val)
	{
		$data=array("status"=>"0");
		$db->update($data,"meme_master","meme_id='".$val."'");
	}
?>
	<script language="javascript">
	window.location='home.php?p=11&msg=deactive';
	</script>
<?php
exit;
}
?>