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
$place_id=$_REQUEST["place_id"];

$place_name=$_POST["place_name"];
$description=$_POST["description"];
$lattitude=$_POST["lattitude"];
$longitude=$_POST["longitude"];
$type=$_POST["type"];
$name=$_POST["name"];
$image=$_FILES['image']['name'];

$blanck='';
			if($image!=$blanck)
			{																			
				$dir = "../uploads/place_pics/";
				$userf = $_FILES['image']['tmp_name'];
				$split=explode(".",$_FILES['image']['name']);
				$len=count($split);
				$ext=strtolower($split[$len-1]);
				$video_arr=array('bmp','jpeg','png','jpg');
				if(!in_array($ext,$video_arr))
				{
					$jsonArray['Success']="0";
					$jsonArray['Message']='Invalid file format. Please try again!!!';
					show_output($jsonArray);
				}
				$path="../uploads/place_pics/";
				$fname="article_".strtotime(date('Y-m-d H:i:s')).".".$ext;
				if(move_uploaded_file($userf,$dir.$fname))
				{
				$postdata['image'] = $fname;
				createthumb("../uploads/place_pics/" . $fname,$path."thumb_320/" . $fname, 320, 122);
				}
			}
			
			$data=array();
			$data['place_name'] = mysql_real_escape_string($place_name);
			$data['description'] = mysql_real_escape_string($description);
			$data['lattitude'] = mysql_real_escape_string($lattitude);
			$data['longitude'] = mysql_real_escape_string($longitude);	
			$data['type'] = mysql_real_escape_string($type);
			$data['name'] = mysql_real_escape_string($name);
			$data['image'] = mysql_real_escape_string($fname);	
			$data['rate'] = '5';
			$data['rate_image'] = 'rate.png';
			$data['status'] = '1';
				
		if($e=='1')
		{	
			$data['modified_date'] = date('Y-m-d H:i:s');
			$data['modified_by'] = '1';
			$db->update($data,"place_master","place_id='".$place_id."'");
			$last_place_id=$place_id;
			?>
			<script language="javascript">
			window.location="<?php echo URL; ?>home.php?p=4&msg=update";
			</script>
		<?php
		}  
		
		if($e=='')
		{
			$data['created_date'] = date('Y-m-d H:i:s');
			$data['created_by'] = '1';
			
			$last_place_id=$db->insert($data,"place_master");
		}
		 
?>

<script language="javascript">
window.location="<?php echo URL; ?>home.php?p=4&msg=add";
</script> 

