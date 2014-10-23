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
$id=$_REQUEST["id"];

$user_id=$_POST["user_id"];
$rated_user_id=$_POST["rated_user_id"];
$place_id=$_POST["place_id"];
$image=$_FILES['file']['name'];
$meme=$_POST["meme"];
$iteam_id=$_POST["iteam_id"];
$comments=$_POST["comments"];
$slider=$_POST["slider"];
$must_try=$_POST["must_try"];
$experts=$_POST["experts"];

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
					window.location="<?php echo URL; ?>home.php?p=29&msg=fmt";
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
			$data['user_id'] = mysql_real_escape_string($user_id);
			$data['rated_user_id'] = mysql_real_escape_string($rated_user_id);
			$data['place_id'] = mysql_real_escape_string($place_id);
			$data['image'] = mysql_real_escape_string($fname);
			$data['meme'] = mysql_real_escape_string($meme);
			$data['iteam_id'] = mysql_real_escape_string($iteam_id);
			$data['comments'] = mysql_real_escape_string($comments);
			$data['slider'] = mysql_real_escape_string($slider);
			$data['must_try'] = mysql_real_escape_string($must_try);
			$data['experts'] = mysql_real_escape_string($experts);
			$data['status'] = '1';
				
		if($e=='1')
		{	
			$data['modified_date'] = date('Y-m-d H:i:s');
			$data['modified_by'] = '1';
			$db->update($data,"rate_master","id='".$id."'");
			$last_iteam_id=$iteam_id;
			?>
			<script language="javascript">
			window.location="<?php echo URL; ?>home.php?p=29&msg=update";
			</script>
		<?php
		}  
		
		if($e=='')
		{
			$data['created_date'] = date('Y-m-d H:i:s');
			$data['created_by'] = '1';
			
			$last_iteam_id=$db->insert($data,"rate_master");
		}
		 
?>

<script language="javascript">
window.location="<?php echo URL; ?>home.php?p=29&msg=add";
</script>