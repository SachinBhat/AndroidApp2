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
$iteam_id=$_REQUEST["iteam_id"];

$title=$_POST["title"];
$description=$_POST["description"];
$image=$_FILES['file']['name'];

			$blanck='';
			if($image!=$blanck)
			{				
																										
				$dir = "../uploads/iteam_pics/";
				$userf = $_FILES['file']['tmp_name'];
				$split=explode(".",$_FILES['file']['name']);
				$len=count($split);
				$ext=strtolower($split[$len-1]);
				$video_arr=array('jpeg','png','jpg');
				if(!in_array($ext,$video_arr))
				{
				?>
					<script language="javascript">
					window.location="<?php echo URL; ?>home.php?p=8&msg=fmt";
					</script>
				<?php
				exit;
				}
				$path="../uploads/iteam_pics/";
				$fname="iteam_".strtotime(date('Y-m-d H:i:s')).".".$ext;
				if(move_uploaded_file($userf,$dir.$fname))
				{
				$postdata['image'] = $fname;
				
				createthumb("../uploads/iteam_pics/" . $fname,$path."thumb_120/" . $fname, 74, 74);
				}
			}
			
			$data=array();
			$data['title'] = mysql_real_escape_string($title);
			$data['description'] = mysql_real_escape_string($description);
			$data['image'] = mysql_real_escape_string($fname);
			$data['status'] = '1';
				
		if($e=='1')
		{	
			$data['modified_date'] = date('Y-m-d H:i:s');
			$data['modified_by'] = '1';
			$db->update($data,"iteam_master","iteam_id='".$iteam_id."'");
			$last_iteam_id=$iteam_id;
			?>
			<script language="javascript">
			window.location="<?php echo URL; ?>home.php?p=8&msg=update";
			</script>
		<?php
		}  
		
		if($e=='')
		{
			$data['created_date'] = date('Y-m-d H:i:s');
			$data['created_by'] = '1';
			
			$last_iteam_id=$db->insert($data,"iteam_master");
		}
		 
?>

<script language="javascript">
window.location="<?php echo URL; ?>home.php?p=8&msg=add";
</script>