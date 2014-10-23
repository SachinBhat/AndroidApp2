<?php session_start();
include("include/config.inc.php");
error_reporting(0);
	if(isset($_COOKIE['coz_username']) && isset($_COOKIE['coz_password'])){
      $_SESSION['coz_username'] = $_COOKIE['coz_username'];
      $_SESSION['coz_password'] = $_COOKIE['coz_password'];
   }
 ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title><?php echo ADMIN_TITLE; ?></title>
	<link type="text/css" rel="stylesheet" href="<?php echo CSS; ?>style.css" />
    <script src="<?php echo JS; ?>jquery-1.7.2.min.js"></script>   
  <script language="javascript">
  $(function(){
	  $(".err").fadeOut(5000);
  });
  </script>
</head>

<body id="login">
    <!-- LOGIN WRAPPER STARTS HERE -->
	<div id="login-wrapper"><div align="center"><!--<img alt="COZ logo" src="images/login_l" />--></div> </div>    
   
    <div id="LoginArea">
    	<div id="top">Log in to your account</div>
        
        <div id="midd">
         <div id="LoginBox">
         
         	<?php if($_REQUEST['err']=="yes") { ?>
         	<div id="err" class="err">Please enter username and password !!</div>
            <?php } ?>
            <?php if($_REQUEST['valid']=="yes") { ?>
         	<div id="err" class="err">Please enter valid username and password !!</div>
            <?php } ?>
            <br /><br />
         	<form action="<?php echo URL; ?>login_process.php" method="post" name="frm_login" id="frm_login">
         	<div class="FieldName">Username:</div>
            <div class="TextBox FielType">
            	<div class="Left"></div>
                <div class="Midd"><span><input name="txt_username" type="text" id="txt_username" value="<?php echo $_SESSION["coz_username"]; ?>" /></span></div>
                <div class="Right"></div>
                <div class="clear"></div>
             </div>
             <div class="clear"></div>
             <div class="FieldName">Password:</div>
            <div class="TextBox FielType">
            	<div class="Left"></div>
                <div class="Midd"><span><input name="txt_password" type="password" id="txt_password" value="<?php echo $_SESSION["coz_password"]; ?>" /></span></div>
                <div class="Right"></div>
                <div class="clear"></div>
             </div>
            <div style="margin-top:10px; margin-bottom:10px;" class="clear SignIn">
            <input name="chk_remember" type="checkbox" id="chk_remember" align="top"  value="1" <?php if($_SESSION["coz_username"]!='' && $_SESSION["coz_password"]!='') { ?>  checked="checked" <?php } ?>/> Remember Me 
             <!--<a href="#">Forgot Password?</a>-->
            </div>
            <div class="ButtonBg SignIn">
                <input name="submit" type="image"  src="images/signing_btn.png" height="27" width="65" id="submit" value="" border="0"/>
             </div>
             <div class="clear"></div>
             </form>
         </div>
         </div>
         <div id="bot"></div>
         <div class="clear"></div>
    </div>
   
    
    <!-- LOGIN WRAPPER ENDS HERE -->
</body>
</html>