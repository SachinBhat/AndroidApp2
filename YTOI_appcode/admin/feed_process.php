<?php
session_start();
ob_start();
error_reporting(E_ERROR | E_WARNING | E_PARSE);
include("include/config.inc.php");
include("class/DB.class.php");
include('session_check.php');
require_once('include/thumb.php');

$db = new DB();
$db->connect();
$e=$_REQUEST['e'];
$feed_id=$_REQUEST["feed_id"];
		
			$data=array();
			$data['feed_name'] = mysql_real_escape_string($_REQUEST["feed_name"]);
			//$data['feed_link'] = mysql_real_escape_string($_REQUEST["feed_link"]);
			$data['location_name'] = mysql_real_escape_string($_REQUEST["location_name"]);
			$data['lattitude'] = mysql_real_escape_string($_REQUEST["lattitude"]);
			$data['longitude'] = mysql_real_escape_string($_REQUEST["longitude"]);
			$data['experiance'] = mysql_real_escape_string($_REQUEST["experiance"]);
			$data['user_id'] = mysql_real_escape_string($_REQUEST["user_id"]);
			//$data['place_id'] = mysql_real_escape_string($_REQUEST["place_id"]);
			$data['category_id'] = mysql_real_escape_string($_REQUEST["category_id"]);
			$data['status'] = '1';
			
			if(!empty($_FILES['image']))
			{																			
				
				$dir="../uploads/feed_pics/";
				$userf = $_FILES['image']['tmp_name'];
				$fname=	strtotime(date('Y-m-d H:i:s'))."_".$_FILES['image']['name'];
				if(move_uploaded_file($userf,$dir.$fname))
				{
					$data['image'] = $fname;
					createthumb("../uploads/feed_pics/" . $fname,$dir."thumb_120/" . $fname, 120, 120);
					createthumb("../uploads/feed_pics/" . $fname,$dir."thumb_480/" . $fname, 480, 480);
					createthumb("../uploads/feed_pics/" . $fname,$dir."thumb_720/" . $fname, 720, 720);
					createthumb("../uploads/feed_pics/" . $fname,$dir."thumb_1080/" .$fname, 1080,1080); 
				}
			} 
				
		if($e=='1')
		{	
			$data['modified_date'] = date('Y-m-d H:i:s');
			$data['modified_by'] = '1';
			$db->update($data,"feed_master","feed_id='".$feed_id."'");
			$last_feed_id=$feed_id; 
			?>
			<script language="javascript">
			window.location="<?php echo URL; ?>home.php?p=2&msg=update";
			</script>
		<?php
		}  
		
		if($e=='')
		{
			$data['created_date'] = date('Y-m-d H:i:s');
			$data['created_by'] = '1';
			
			$last_feed_id=$db->insert($data,"feed_master");
		}
		 
?>

<script language="javascript">
window.location="<?php echo URL; ?>home.php?p=2&msg=add";
</script> 

