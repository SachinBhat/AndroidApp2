<?php include('header.php'); ?>
<!-- HEADER STARTS HERE --> 

<!-- MIDDLE ENDS HERE -->
<?php 
$page_is=@$_REQUEST['p'];
switch($page_is)
{
	
	case 1:
	include("user_add.php");
	break;
	
	case 2:
	include("feed_master.php");
	break;
	
	case 3:
	include("feed_add.php");
	break;
	
	case 4:
	include("place_master.php");
	break;
	
	case 5:
	include("place_add.php");
	break;
	
	case 6:
	include("deal_master.php");
	break;
	
	case 7:
	include("deal_add.php");
	break;
	
	case 8:
	include("iteam_master.php");
	break;
	
	case 9:
	include("iteam_add.php");
	break;
	
	case 10:
	include("change_pass.php");
	break;
	
	case 11:
	include("meme_master.php");
	break;
	
	case 12:
	include("meme_add.php");
	break;
	
	case 13:
	include("comment_master.php");
	break;
	
	case 14:
	include("place1_add.php");
	break;
	
	case 15:
	include("follower_master.php");
	break;
	
	case 16:
	include("place1_add.php");
	break;
	
	case 17:
	include("following_master.php");
	break;
	
	case 18:
	include("place1_add.php");
	break;
	
	case 19:
	include("comments_master.php");
	break;
	
	case 20:
	include("comments_add.php");
	break;
	
	case 21:
	include("alert_master.php");
	break;
	
	case 22:
	include("alert_add.php");
	break;
	
	case 23:
	include("device_master.php");
	break;
	
	case 24:
	include("device_add.php");
	break;
	
	case 25:
	include("userpost_master.php");
	break;
	
	case 26:
	include("userpost_add.php");
	break;
	
	case 27:
	include("accountSetting_master.php");
	break;
	
	case 28:
	include("accountSetting_add.php");
	break;
	
	case 29:
	include("rate_master.php");
	break;
	
	case 30:
	include("rate_add.php");
	break;
	
	case 31:
	include("category_master.php");
	break;
	
	case 32:
	include("category_add.php");
	break;
	
	default:
	include("user_master.php"); 	
	break;
	
	
}
 ?>
<!-- MIDDLE ENDS HERE --> 

<!-- FOOTER STARTS HERE -->
<?php include('footer.php'); ?>
<!-- FOOTER ENDS HERE -->