<?php 
ob_start();
error_reporting(E_ERROR | E_WARNING | E_PARSE);
@session_start();
//error_reporting(0);
include("include/config.inc.php");
include("session_check.php");
include("pagination/pagination.php");
$page = (int) (!isset($_GET["page"]) ? 1 : $_GET["page"]);
$page = ($page == 0 ? 1 : $page);
$perpage = PAGE_LIMIT;//limit in each page
$startpoint = ($page * $perpage) - $perpage;

/*$db_host="109.163.228.47";
$db_user="ytoicoin_amit";
$db_pwd="Mn=;cDKg2VPh";
$db_dbname="ytoicoin_ytoilive";
*/
$db_dbname = 'lakadia7_ytoi';
$db_user = 'lakadia7_ytoi';
$db_pwd = 'ytoi#007';
$db_host = 'localhost';

$conn=mysql_connect($db_host,$db_user,$db_pwd);
mysql_select_db($db_dbname);	
?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title><?php echo ADMIN_TITLE; ?></title>
<link type="text/css" rel="stylesheet" href="<?php echo CSS; ?>style.css" />
<link type="text/css" rel="stylesheet" href="<?php echo CSS; ?>style_progress.css" />
<link type="text/css" href="<?php echo CSS; ?>jquery-ui-1.8.21.custom.css" rel="stylesheet" />
<link rel="stylesheet" type="text/css" href="<?php echo CSS; ?>jquery-ui-timepicker.css">
<link type="text/css" rel="stylesheet" href="<?php echo URL; ?>pagination/style2.css" />
<script src="<?php echo JS; ?>jquery-1.7.2.min.js"></script>   
<script type="text/javascript" src="<?php echo JS; ?>jquery-ui-1.8.21.custom.min.js"></script>
<script language="JavaScript" src="<?php echo JS; ?>jquery.ui.timepicker.js"></script>


<!--<script type="text/javascript" src="<?php echo JS; ?>jquery.hashchange.js"></script>
<script type="text/javascript" src="<?php echo JS; ?>ajaxml.js"></script>-->

<!--Validation JS & CSS -->

<link rel="stylesheet" href="<?php echo CSS; ?>validationEngine.jquery.css" type="text/css"/>
<link rel="stylesheet" href="<?php echo CSS; ?>template.css" type="text/css"/>
<script src="<?php echo JS; ?>jquery.validationEngine-en.js" type="text/javascript" charset="utf-8"></script>
<script src="<?php echo JS; ?>jquery.validationEngine.js" type="text/javascript" charset="utf-8"></script>

</head>
<script language="javascript">
$(function(){
	$('#dialog').dialog({
					autoOpen: false,
					width: 850,
					show: "blind",
					hide: "fadeout"
				});
				
	//$('div #MenuBar ul li a').click(function(){
	//	$('.active').removeClass("active");
	//	$('div #MenuBar ul li a').addClass("active");
	//});			
	
})
</script>

<style type="text/css">
p1.c {color:red;}
</style>

<style type="text/css">
.demoHeaders { margin-top: 2em; }
#dialog_link {padding: .4em 1em .4em 20px;text-decoration: none;position: relative;}
#dialog_link span.ui-icon {margin: 0 5px 0 0;position: absolute;left: .2em;top: 50%;margin-top: -8px;}
ul#icons {margin: 0; padding: 0;}
ul#icons li {margin: 2px; position: relative; padding: 4px 0; cursor: pointer; float: left;  list-style: none;}
ul#icons span.ui-icon {float: left; margin: 0 4px;}

#MenuBar ul li.hover,
#MenuBar ul li:hover {
  position: relative;
  z-index: 599;
  cursor: default;
}

#MenuBar ul ul {
  visibility: hidden;
  position: absolute;
  top: 100%;
  left: 0;
  z-index: 598;
  width: 100%;
}
#MenuBar ul li:hover > ul {
  visibility: visible;
}
#submenu{
	background: none repeat scroll 0 0 rgb(0, 126, 58) !important;
    width: 212% !important;
}
</style>
<body>

<!-- HEADER STARTS HERE -->
    <div id="Header">
    	<div class="align">
    		<div id="Logo"><a href="home.php"><!--<img alt="Logo" src="images/logo.png" />--></a></div>
            <div id="MenuArea">
            	<div id="UserName">
               	  <img alt="UserIcon" align="absmiddle" src="images/user-icon.png" />Welcome <span><a href="#">Administrator</a></span>
                </div>
                <div id="UserAccount">
               	 <a href="home.php?p=10">Change Password</a> | <a href="logout.php">Logout</a> 
                </div>
                
                <!-- MENUBAR STARTS HERE -->
                <div id="MenuBar">
                	<ul>
                    	<li><a href="home.php" <?php if($_REQUEST['p']=='' || $_REQUEST['p']=='1' || $_REQUEST['p']=='2' || $_REQUEST['p']=='3' || $_REQUEST['p']=='15' || $_REQUEST['p']=='17' || $_REQUEST['p']=='19') {  ?> class="active" <?php } ?>><span>User</span></a></li>
						
						<!--<li><a href="home.php?p=2" <?php if($_REQUEST['p']=='2' || $_REQUEST['p']=='3') {  ?> class="active" <?php } ?>><span>Feed</span></a></li>-->
						
                        <li><a href="home.php?p=4" <?php if($_REQUEST['p']=='4' || $_REQUEST['p']=='5') {  ?> class="active" <?php } ?> ><span>Places
						
						<li><a href="home.php?p=6" <?php if($_REQUEST['p']=='6' || $_REQUEST['p']=='7') {  ?> class="active" <?php } ?>><span>Deal</span></a></li>
						
						<li><a href="home.php?p=8" <?php if($_REQUEST['p']=='8' || $_REQUEST['p']=='9') {  ?> class="active" <?php } ?>><span>Iteam</span></a></li>
						
						<li><a href="home.php?p=11" <?php if($_REQUEST['p']=='11' || $_REQUEST['p']=='12') {  ?> class="active" <?php } ?>><span>Meme</span></a></li>
						
						<!--<li><a href="home.php?p=13" <?php if($_REQUEST['p']=='13' || $_REQUEST['p']=='13') {  ?> class="active" <?php } ?>><span>Comments</span></a></li>-->
						
						<!--<li><a href="home.php?p=15" <?php if($_REQUEST['p']=='15' || $_REQUEST['p']=='15') {  ?> class="active" <?php } ?>><span>Followers</span></a></li>
                        
						<li><a href="home.php?p=17" <?php if($_REQUEST['p']=='17' || $_REQUEST['p']=='17') {  ?> class="active" <?php } ?>><span>Following</span></a></li>-->
						
						<!--<li><a href="home.php?p=19" <?php if($_REQUEST['p']=='19' || $_REQUEST['p']=='19') {  ?> class="active" <?php } ?>><span>Comment</span></a></li>-->
						
						<!--<li><a href="home.php?p=21" <?php if($_REQUEST['p']=='21' || $_REQUEST['p']=='21') {  ?> class="active" <?php } ?>><span>Alert</span></a></li>
						
						<li><a href="home.php?p=23" <?php if($_REQUEST['p']=='23' || $_REQUEST['p']=='23') {  ?> class="active" <?php } ?>><span>Device</span></a></li>
						
						<li><a href="home.php?p=25" <?php if($_REQUEST['p']=='25' || $_REQUEST['p']=='25') {  ?> class="active" <?php } ?>><span>User Post</span></a></li>-->
						
						<li><a href="home.php?p=31" <?php if($_REQUEST['p']=='31' || $_REQUEST['p']=='32') {  ?> class="active" <?php } ?>><span>Category</span></a></li>
						
						<!--<li><a href="home.php?p=27" <?php if($_REQUEST['p']=='27' || $_REQUEST['p']=='29' || $_REQUEST['p']=='30' || $_REQUEST['p']=='31' || $_REQUEST['p']=='32') {  ?> class="active" <?php } ?>><span>More..</span></a>
						<ul style="width: 210%; background:#000;  border-radius: 4px 4px 4px 4px;">
						<li><a href="home.php?p=27" <?php if($_REQUEST['p']=='27' || $_REQUEST['p']=='27') {  ?> <?php } ?>><span>Account Setting</span></a></li>
						<li><a href="home.php?p=29" <?php if($_REQUEST['p']=='29' || $_REQUEST['p']=='30') {  ?> <?php } ?>><span style="width: 240%;">Rate</span></a></li>
						<li><a href="home.php?p=31" <?php if($_REQUEST['p']=='31' || $_REQUEST['p']=='32') {  ?> <?php } ?>><span style="width: 146%;">Category</span></a></li>
						</ul> 					
						
						
						</li>-->
						
						<!--<li><a href="home.php?p=29" <?php if($_REQUEST['p']=='29' || $_REQUEST['p']=='29') {  ?> class="active" <?php } ?>><span>Rate</span></a></li>
                        <li><a href="#"><span>Contact</span></a></li>
                        <li><a href="#"><span>Settings</span></a></li>-->
                    </ul>
                    <div class="clear"></div>
                </div>
                <!-- MENUBAR ENDS HERE -->
            </div>
            <div class="clear"></div>
        </div>
    </div>
    <!-- HEADER ENDS HERE -->
<?php ob_end_flush(); ?>