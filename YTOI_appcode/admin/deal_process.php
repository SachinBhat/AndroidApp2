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
$deal_id=$_REQUEST["deal_id"];

			$data=array();
			$data['title'] = mysql_real_escape_string($_POST["title"]);
			$data['description'] = mysql_real_escape_string($_POST["description"]);
			$data['status'] = '1';
				
		if($e=='1')
		{	
			$data['modified_date'] = date('Y-m-d H:i:s');
			$data['modified_by'] = '1';
			$db->update($data,"deal_master","deal_id='".$deal_id."'");
			$last_deal_id=$deal_id;
			?>
			<script language="javascript">
			window.location="<?php echo URL; ?>home.php?p=6&msg=update";
			</script>
		<?php
		}  
		
		if($e=='')
		{
			$data['created_date'] = date('Y-m-d H:i:s');
			$data['created_by'] = '1';
			
			$last_deal_id=$db->insert($data,"deal_master");
		}
		 
?>

<script language="javascript">
window.location="<?php echo URL; ?>home.php?p=6&msg=add";
</script>