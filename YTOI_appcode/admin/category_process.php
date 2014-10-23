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
$cat_id=$_REQUEST["cat_id"];

$user_id=$_REQUEST['user_id'];
$name=$_REQUEST["name"];
$image=$_FILES["image"]['name'];
$image1=$_FILES["selected_image"]['name'];
$link=$_REQUEST["link"];

		$data=array();	
		if($image!='')
			{				
																										
				$dir = "../uploads/category_pics/";
				$userf = $_FILES['image']['tmp_name'];
				
				$fname="cat_".strtotime(date('Y-m-d H:i:s'))."_".$image;
				if(move_uploaded_file($userf,$dir.$fname))
				{
					$data['image'] = $fname;
					createthumb("../uploads/category_pics/" . $fname,$dir."thumb_480/" . $fname, 68, 68);
					createthumb("../uploads/category_pics/" . $fname,$dir."thumb_720/" . $fname, 95, 95);
					createthumb("../uploads/category_pics/" . $fname,$dir."thumb_1080/" .$fname, 138,138);
					
				}
			}
		if($image1!='')
			{				
																										
				$dir = "../uploads/category_pics/";
				$userf = $_FILES['selected_image']['tmp_name'];
				
				$fname="cat_".strtotime(date('Y-m-d H:i:s'))."_".$image1;
				if(move_uploaded_file($userf,$dir.$fname))
				{
					$data['selected_image'] = $fname;
					createthumb("../uploads/category_pics/" . $fname,$dir."thumb_480/" . $fname, 68, 68);
					createthumb("../uploads/category_pics/" . $fname,$dir."thumb_720/" . $fname, 95, 95);
					createthumb("../uploads/category_pics/" . $fname,$dir."thumb_1080/" .$fname, 138,138);
				}
			}
			
				$data['name'] = mysql_real_escape_string($name);
				$data['link'] = mysql_real_escape_string($link);
				$data['status'] = '1';
			
				
		if($e=='1')
		{	
			$data['modified_date'] = date('Y-m-d H:i:s');
			$data['modified_by'] = '1';
			$db->update($data,"category_master","cat_id='".$cat_id."'");
			$last_cat_id=$cat_id;
			?>
			<script language="javascript">
			window.location="<?php echo URL; ?>home.php?p=31&msg=update";
			</script>
		<?php
		}  
		
		if($e=='')
		{
			$data['created_date'] = date('Y-m-d H:i:s');
			$data['created_by'] = '1';
			
			$last_cat_id=$db->insert($data,"category_master");
		}
		 
?>

<script language="javascript">
window.location="<?php echo URL; ?>home.php?p=31&msg=add";
</script> 

