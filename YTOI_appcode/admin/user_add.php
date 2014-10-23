<?php
include("class/DB.class.php");
//error_reporting(0);
$db = new DB();
$db->connect();
$tbl_name="user_master";
$default_url=URL."home.php?";
$id=$_REQUEST["id"];
if($_REQUEST['action']=="edit")
{
	$result=$db->select_one($tbl_name,"user_id='".$id."'");	
}
?>
<script language="javascript">
$(function(){
});
</script>
<script>
jQuery(document).ready(function(){
jQuery("#my_form").validationEngine();
});
function () {
    $( "#datepicker" ).datepicker({
      changeMonth: true,
      changeYear: true 
    });
  }
function validate()
{
	var formName = document.my_form;
	var password = formName.password.value; 
	var confirm_password = formName.confirm_password.value;
	
	if(password != confirm_password)
	{
		alert('Password and confirm_password does not match');
		$('#receiver_email').focus;
		return false;
	}
}
</script>
<script>

$(document).ready(
  function () {
    $( "#datepicker" ).datepicker({
      changeMonth: true,
      changeYear: true 
    });
  }

);
</script>
<script language="javascript">
  $(function(){
	  $(".err").fadeOut(5000);
	  $("#non").hide(5000);
  });
</script>
<link rel="stylesheet" href="http://code.jquery.com/ui/1.10.3/themes/smoothness/jquery-ui.css" />
    
   
    <script src="http://code.jquery.com/jquery-1.9.1.js"></script>
   
    <script src="http://code.jquery.com/ui/1.10.3/jquery-ui.js"></script>
<div class="MiddleArea align">
  <div class="content-box">
    <div class="content-header-left"></div>
    <div class="content-header-midd"> <span>User Master</span>
      <ul class="content-box-tabs">
        <li><span><a href="<?php echo $default_url."" ?>" title="View Records">View</a></span></li>
        <li><span class="current"><a href="<?php echo $default_url."p=1" ?>" title="Add New Record">Add</a></span></li>
      </ul>
    </div>
    <div class="content-header-right"></div>
    <div class="content-box-content">
    <?php 
	if($id!='')
	{
		$page="user_process.php?e=1";
	}
	else
	{
		$page="user_process.php";
	}

	?>
	
	<table>
	  <tr>
	  <td>
	<?php if($_REQUEST['er']=='uname') { ?>

                <center><div id="err" class="err"><span style="text-align:center;">UserName already exists !!</span></div></center>
			<?php } ?>	
	<?php if($_REQUEST['er']=='email') { ?>

                <center><div id="err" class="err"><span style="text-align:center;">Email already exists !!</span></div></center>
			<?php } ?>	
	  </tr>
	  </td>
	</table>
	  
	
	
      <form action="<?php echo $page; ?>" method="post" name="my_form" id="my_form" enctype="multipart/form-data" >
      <input type="hidden" name="user_id" id="venue_id" value="<?php echo $result['user_id']; ?>" />
      <table width="100%" border="0" cellspacing="0" cellpadding="5" class="table_add">
  <tr>
    <td align="left" valign="middle" bgcolor="#CCCCCC" class="td_heading"><strong>User Information </strong></td>
  </tr>
  <tr>
    <td align="left" valign="middle">
    <table width="100%" border="0" cellspacing="0" cellpadding="5">
	 <tr>
        <td colspan="4" align="right"><p1 class="c">* Indicate Required Fields</p1></td> 
    </tr>
  <tr>
    <td width="200" align="right" valign="middle"> First Name
      <p1 class="c">*</p1></td>
    <td width="10" align="center" valign="middle">:</td>
    <td align="left" valign="middle"><label for="first_name"></label>
      <input name="first_name" type="text" class="validate[required] text-input" id="first_name" required value="<?php echo $result['first_name']; ?>" size="30" /></td>
  </tr>
  <tr>
    <td width="200" align="right" valign="middle"> Last Name
      <p1 class="c">*</p1></td>
    <td width="10" align="center" valign="middle">:</td>
    <td align="left" valign="middle"><label for="last_name"></label>
      <input name="last_name" type="text" class="validate[required] text-input" id="last_name" required value="<?php echo $result['last_name']; ?>" size="30" /></td>
  </tr>
  <tr>
    <td align="right" valign="middle"> User Email
      <p1 class="c">*</p1></td>
    <td width="10" align="center" valign="middle">:</td>
    <td align="left" valign="middle"><label for="email_address"></label>
      <input name="email" type="text" class="validate[required,custom[email]] text-input" id="venue_address" required value="<?php echo $result['email']; ?>" size="30" /></td>
  </tr>
  <tr>
    <td align="right" valign="middle"> User Password
      <p1 class="c">*</p1></td>
    <td width="10" align="center" valign="middle">:</td>
    <td align="left" valign="middle"><label for="password"></label>
      <input name="password" type="password" class="validate[required] text-input" id="venue_code" required value="<?php echo base64_decode($result['password']); ?>" size="30" /></td>
  </tr>
  
  <tr>
    <td align="right" valign="middle"> Confirm Password
      <p1 class="c">*</p1></td>
    <td width="10" align="center" valign="middle">:</td>
    <td align="left" valign="middle"><label for="confirm password"></label>
      <input name="confirm_password" type="password" class="validate[required] text-input" id="receiver_email" required value="<?php echo base64_decode($result['password']); ?>" size="30" /></td>
  </tr>
  <tr>
    <td width="200" align="right" valign="middle"> Mobile Number</td>
	<td width="10" align="center" valign="middle">:</td>
    <td align="left" valign="middle"><label for="mobile_number"></label>
      <input name="mobile_number" type="text" id="mobile_number" value="<?php echo $result['mobile_number']; ?>" size="30" /></td>
  </tr>
  <tr>
    <td width="200" align="right" valign="middle"> Button Links</td>
    <td width="10" align="center" valign="middle">:</td>
    <td align="left" valign="middle"><label for="button_links"></label>
      <select name="button_links" class="select-box">
		<option>select</option>
		<option <?php $opt = $result['button_links']; if($opt=='Home'){ echo "selected"; }?> >Home</option>
		<option <?php $opt = $result['button_links']; if($opt=='Repo'){ echo "selected"; }?>>Repo</option>
		<option <?php $opt = $result['button_links']; if($opt=='Followers'){ echo "selected"; }?>>Followers</option>
		<option <?php $opt = $result['button_links']; if($opt=='Following'){ echo "selected"; }?>>Following</option>
		<option <?php $opt = $result['button_links']; if($opt=='Explore'){ echo "selected"; }?>>Explore</option>
	  </select>
	  </td>
  </tr>
  <tr>
    <td width="200" align="right" valign="middle"> Feed</td>
    <td width="10" align="center" valign="middle">:</td>
    <td align="left" valign="middle"><label for="feed"></label>
      <select name="feed" class="select-box">
		<option>select</option>
		<option <?php $opt = $result['feed']; if($opt=='Friend feed'){ echo "selected"; }?>>Friend feed</option>
		<option <?php $opt = $result['feed']; if($opt=='All feed'){ echo "selected"; }?>>All feed</option>
	  </select>
	  </td>
  </tr>
  <tr>
    <td width="200" align="right" valign="middle"> Option Box </td>
    <td width="10" align="center" valign="middle">:</td>
    <td align="left" valign="middle"><label for="option_box"></label>
      <select name="option_box" class="select-box">
		<option>select</option>
		<option <?php $opt = $result['option_box']; if($opt=='Deal'){ echo "selected"; }?>>Deal</option>
		<option <?php $opt = $result['option_box']; if($opt=='Setting'){ echo "selected"; }?>>Setting</option>
		<option <?php $opt = $result['option_box']; if($opt=='Followers'){ echo "selected"; }?>>Followers</option>
		<option <?php $opt = $result['option_box']; if($opt=='Following'){ echo "selected"; }?>>Following</option>
		<option <?php $opt = $result['option_box']; if($opt=='Search'){ echo "selected"; }?> >Search</option>
		<option <?php $opt = $result['option_box']; if($opt=='Account'){ echo "selected"; }?> >Account</option>
	  </select>
	  </td>
  </tr>
  <tr>
  <tr>
    <td align="right" valign="middle"> Reached From</td>
    <td width="10" align="center" valign="middle">:</td>
    <td align="left" valign="middle"><label for="reached_from"></label>
      <input name="reached_from" type="text" id="reached_from" value="<?php echo $result['reached_from']; ?>" size="30" /></td>
  </tr>
   <tr>
    <td width="200" align="right" valign="middle"> Date of Birth</td>
    <td width="10" align="center" valign="middle">:</td>
    <td align="left" valign="middle"><label for="DateOfBirth"></label>
      <input name="dateOfBirth" type="text" id="datepicker" value="<?php echo $result['birth_date']; ?>" size="30" /></td>
  </tr>
  <tr>
    <td width="200" align="right" valign="middle"> Gender</td>
    <td width="10" align="center" valign="middle">:</td>
    <td align="left" valign="middle"><label for="DateOfBirth"></label>
	  <?php if($result['gender'] == 'Male') {?>
      <label><input name="gender" type="radio" class="validate[required] radio-input" id="gender" value="Male" required size="30" checked />Male</label>
	  <label><input name="gender" type="radio" class="validate[required] radio-input" id="gender" value="Female" required size="30" />Female</label></td>
	  <?php } else if($result['gender'] == 'Female') {?>
	  <label><input name="gender" type="radio" class="validate[required] radio-input" id="gender" value="Male" required size="30" />Male</label>
	  <label><input name="gender" type="radio" class="validate[required] radio-input" id="gender" value="Female" required size="30" checked />Female</label></td>
	  <?php } else {  ?>
	  <label><input name="gender" type="radio" class="validate[required] radio-input" id="gender" value="Male" required size="30" checked/>Male</label>
	  <label><input name="gender" type="radio" class="validate[required] radio-input" id="gender" value="Female" required size="30" />Female</label></td>
	  <?php } ?>
  </tr>
  <tr>
    <td width="200" align="right" valign="middle"> Street </td>
    <td width="10" align="center" valign="middle">:</td>
    <td align="left" valign="middle"><label for="street"></label>
      <input name="street" type="text" id="street" value="<?php echo $result['street']; ?>" size="30" /></td>
  </tr>
  <tr>
    <td width="200" align="right" valign="middle"> City </td>
    <td width="10" align="center" valign="middle">:</td>
    <td align="left" valign="middle"><label for="City"></label>
      <input name="city" type="text" id="city" value="<?php echo $result['city']; ?>" size="30" /></td>
  </tr>
  <tr>
    <td width="200" align="right" valign="middle"> State </td>
    <td width="10" align="center" valign="middle">:</td>
    <td align="left" valign="middle"><label for="state"></label>
      <input name="state" type="text" id="state" value="<?php echo $result['state']; ?>" size="30" /></td>
  </tr>
  <tr>
    <td width="200" align="right" valign="middle"> Country </td>
    <td width="10" align="center" valign="middle">:</td>
    <td align="left" valign="middle"><label for="country"></label>
      <input name="country" type="text" id="country" value="<?php echo $result['country']; ?>" size="30" /></td>
  </tr>
  <tr>
    <td width="200" align="right" valign="middle"> ZipCode </td>
    <td width="10" align="center" valign="middle">:</td>
    <td align="left" valign="middle"><label for="zipcode"></label>
      <input name="zipcode" type="text" class="validate[required] text-input" id="zipcode"  required value="<?php echo $result['zipcode']; ?>" size="30" /></td>
  </tr>
  <tr>
    <td width="200" align="right" valign="middle"> Profile Picture </td>
    <td width="10" align="center" valign="middle">:</td>
    <td align="left" valign="middle"><label for="file"></label>
      <input name="file" type="file" id="file" value="" size="30" /></td> 
	  <td rowspan="8">
	  <img src="../uploads/pro_pics/<?php echo($result['profile_picture']!='')?$result['profile_picture']:'default.jpg';?>" height="150px" width="150px">
	  </td>
  </tr> 
    </table>
    </td>
  </tr>
  <tr>
    <td align="left" valign="middle"><table width="100%" border="0" cellspacing="0" cellpadding="5">
      <tr>
        <td width="100"  valign="middle"><input type="submit" name="submit" value="Submit" style="margin-left:250px;" onclick="return validate();"/>&nbsp;<input type="button" name="back" value="Back" onclick="javascript:history.go(-1);" /></td>
      </tr>
      </table></td>
  </tr>
  
      </table>
      </form>

    </div>
  </div>
</div>