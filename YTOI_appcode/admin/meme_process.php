<?php
session_start();
ob_start();
error_reporting(E_ERROR | E_WARNING | E_PARSE);
include("include/config.inc.php");
include("class/DB.class.php");
include('session_check.php');

$db = new DB();
$db->connect();
$e=$_REQUEST['e'];
$meme_id=$_REQUEST["meme_id"];

$title=$_POST["title"];
$description=$_POST["description"];

			
			$data=array();
			$data['title'] = mysql_real_escape_string($title);
			$data['description'] = mysql_real_escape_string($description);
			$data['status'] = '1';
				
		if($e=='1')
		{	
			$data['modified_date'] = date('Y-m-d H:i:s');
			$data['modified_by'] = '1';
			$db->update($data,"meme_master","meme_id='".$meme_id."'");
			$last_meme_id=$meme_id;
			?>
			<script language="javascript">
			window.location="<?php echo URL; ?>home.php?p=11&msg=update";
			</script>
		<?php
		}  
		
		if($e=='')
		{
			$data['created_date'] = date('Y-m-d H:i:s');
			$data['created_by'] = '1';
			
			$last_meme_id=$db->insert($data,"meme_master");
		}
		 
?>

<script language="javascript">
window.location="<?php echo URL; ?>home.php?p=11&msg=add";
</script>