<?php
include("class/DB.class.php");
$db = new DB();
$db->connect();
//$tbl_name="coz_news_mst";
$default_url=URL."home.php?";
?>
<script>
		jQuery(document).ready(function(){
			//$("#err").fadeOut(5000);
			jQuery("#frm").validationEngine();
		});
	</script>
 <script language="javascript">
  $(function(){
	  $(".err").fadeOut(5000);
  });
</script>
<?php 
$m=$_REQUEST['msg'];
if($m==1)
{
	$msg="Password has been updated successfully !!";
}
if($m==2)
{
	$msg="Old password you have entered is not correct !!";
}
if($m==3)
{
	$msg="Please enter required field first !!";
}
 ?>

<div class="MiddleArea align">
  <div class="content-box">
    <div class="content-header-left"></div>
    <div class="content-header-midd"> <span>Change Passwword</span>
    </div>
    <div class="content-header-right"></div>
    <div class="content-box-content">
     
     <form method="post" name="frm" id="frm" action="pass_ajax.php">
     <table width="100%" border="0" cellspacing="0" cellpadding="5">
	
     <tr>
        <td colspan="3" align="right"><p1 class="c">* Indicate Required Fields</p1></td> 
     </tr>
<?php if(isset($m)!=''){ ?>
	<tr>
    <td  colspan="3"  align="center" valign="middle"><span class="err"><?php echo $msg;  ?></span></td>
    </tr>     
<?php } ?>

  <tr>  
    <td width="250" align="right" valign="middle">Old Password <p1 class="c">*</p1></td>
    <td width="10" align="center" valign="middle">:</td>
    <td align="left" valign="middle"><label for="old_pass"></label>
      <input type="password" name="old_pass" id="old_pass" class="validate[required] text-input" /></td>
  </tr>
  <tr>
    <td align="right" valign="middle">New Password <p1 class="c">*</p1></td>
    <td align="center" valign="middle">:</td>
    <td align="left" valign="middle"><label for="new_pass"></label>
      <input type="password" name="new_pass" id="new_pass"  class="validate[required] text-input"/></td>
  </tr>
  <tr>
    <td align="right" valign="middle">Confirm Password <p1 class="c">*</p1></td>
    <td align="center" valign="middle">:</td>
    <td align="left" valign="middle"><label for="old_pass3"></label>
      <input type="password" name="con_pass" id="old_pass3" class="validate[required,equals[new_pass]] text-input"/></td>
  </tr>
  <tr>
    <td align="right" valign="middle">&nbsp;</td>
    <td colspan="2" align="left" valign="middle"><input type="submit" name="submit_pass" id="submit_pass" value="Change Password" />&nbsp;<input type="button" value="Back" name="back" onClick="javascript:history.go(-1);" /></td>
    </tr>
</table>

      </form>
    </div>
  </div>
</div>