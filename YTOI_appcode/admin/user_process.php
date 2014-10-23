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
$user_id=$_REQUEST["user_id"];

$first_name=$_POST["first_name"];
$last_name=$_POST["last_name"];
$email=$_POST["email"];
$password=base64_encode($_POST["password"]);
$mobile_number=$_POST["mobile_number"];
$button_links=$_POST["button_links"];
$feed=$_POST["feed"];
$option_box=$_POST["option_box"];
$reached_from=$_POST["reached_from"];
$birth_date=$_POST["dateOfBirth"];
$gender=$_POST["gender"];
$street=$_POST["street"];
$city=$_POST["city"];
$state=$_POST["state"];
$country=$_POST["country"];
$zipcode=$_POST["zipcode"];
$profile_picture=$_FILES['file']['name'];	

			$blanck='';
			if($profile_picture!=$blanck)
			{				
																										
				$dir = "../uploads/pro_pics/";
				$userf = $_FILES['file']['tmp_name'];
				$split=explode(".",$_FILES['file']['name']);
				$len=count($split);
				$ext=strtolower($split[$len-1]);
				$video_arr=array('mp4','flv','3gp','jpg');
				if(!in_array($ext,$video_arr))
				{
				?>
					<script language="javascript">
					window.location="<?php echo URL; ?>home.php?p=1&msg=fmt";
					</script>
				<?php
				exit;
				}
				$path="../uploads/pro_pics/";
				$fname="profile_".strtotime(date('Y-m-d H:i:s')).".".$ext;
				if(move_uploaded_file($userf,$dir.$fname))
				{
				$postdata['image'] = $fname;
				
				createthumb("../uploads/pro_pics/" . $fname,$path."thumb_74/" . $fname, 74, 74);
				}
			}
			
			$data=array();
			$data['first_name'] = mysql_real_escape_string($first_name);
			$data['last_name'] = mysql_real_escape_string($last_name);
			$data['email'] = mysql_real_escape_string($email);
			$data['password'] = mysql_real_escape_string($password);
			$data['mobile_number'] = mysql_real_escape_string($mobile_number);
			$data['button_links'] = mysql_real_escape_string($button_links);
			$data['feed'] = mysql_real_escape_string($feed);
			$data['option_box'] = mysql_real_escape_string($option_box);
			$data['reached_from'] = mysql_real_escape_string($reached_from);
			$data['birth_date'] = mysql_real_escape_string($birth_date);
			$data['gender'] = mysql_real_escape_string($gender);
			$data['street'] = mysql_real_escape_string($street);
			$data['city'] = mysql_real_escape_string($city);
			$data['state'] = mysql_real_escape_string($state);
			$data['country'] = mysql_real_escape_string($country);
			$data['zipcode'] = mysql_real_escape_string($zipcode);
			$data['profile_picture'] = mysql_real_escape_string($fname);
			$data['status'] = '1';
				
	

		if($e=='1')
		{	
			$data['modified_date'] = date('Y-m-d H:i:s');
			$data['modified_by'] = '1';
			$db->update($data,"user_master","user_id='".$user_id."'");
			$last_user_id=$user_id;
			?>
			<script language="javascript">
			window.location="<?php echo URL; ?>home.php?&msg=update";
			</script>
		<?php
		}  
		
		if($e=='')
		{
			$query2 = "select email from user_master where email = '$email'";
			$exe2 = mysql_query($query2);
			$row2 = mysql_fetch_array($exe2);
			
			
			$num2 = mysql_num_rows($exe2);
			if($num2 > 0)
			{
				if($row2['email'] != '')
				{
					header('Location:home.php?p=1&er=email');				
				}
				
			}
		
			$data['created_date'] = date('Y-m-d H:i:s');
			$data['created_by'] = '1';
			$last_user_id=$db->insert($data,"user_master");
		}
		 
?>

<script language="javascript">
window.location="<?php echo URL; ?>home.php?&msg=add";
</script> 

