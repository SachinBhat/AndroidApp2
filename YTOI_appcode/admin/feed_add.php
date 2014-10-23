<?php
include("class/DB.class.php");
//error_reporting(0);
$db = new DB();
$db->connect();
$tbl_name="feed_master";
$default_url=URL."home.php?";
$id=$_REQUEST["id"];
if($_REQUEST['action']=="edit")
{
	$result=$db->select_one($tbl_name,"feed_id='".$id."'");	
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

<script language="javascript">
  $(function(){
	  $(".err").fadeOut(5000);
	  $("#non").hide(5000);
  });
</script>

<div class="MiddleArea align">
  <div class="content-box">
    <div class="content-header-left"></div>
    <div class="content-header-midd"> <span>Feed Master</span>
      <ul class="content-box-tabs">
        <li><span><a href="<?php echo $default_url."p=2" ?>" title="View Records">View</a></span></li>
        <li><span class="current"><a href="<?php echo $default_url."p=3" ?>" title="Add New Record">Add</a></span></li>
      </ul>
    </div>
    <div class="content-header-right"></div>
    <div class="content-box-content">
    <?php 
	if($id!='')
	{
		$page="feed_process.php?e=1";
	}
	else
	{
		$page="feed_process.php";
	}

	?>
	
	<table>
	  <tr>
	  <td>
	<?php if($_REQUEST['er']=='email') { ?>

                <center><div id="err" class="err"><span style="text-align:center;">Email already exists !!</span></div></center>
			<?php } ?>	
	  </tr>
	  </td>
	</table>
	  
	
	
      <form action="<?php echo $page; ?>" method="post" name="my_form" id="my_form" enctype="multipart/form-data" >
      <input type="hidden" name="feed_id" id="feed_id" value="<?php echo $result['feed_id']; ?>" />
      <table width="100%" border="0" cellspacing="0" cellpadding="5" class="table_add">
  <tr>
    <td align="left" valign="middle" bgcolor="#CCCCCC" class="td_heading"><strong>Feed Information </strong></td>
  </tr>
  
  <tr>
    <td align="left" valign="middle">
    <table width="100%" border="0" cellspacing="0" cellpadding="5">
	 <tr>
        <td colspan="4" align="right"><p1 class="c">* Indicate Required Fields</p1></td> 
    </tr>
	
	<tr>
   <td align="right" valign="middle">User Name <p1 class="c">*</p1></td>
   <td align="center" valign="middle">:</td>
   <td align="left" valign="middle">
   <select name="user_id" id="user_id" class="select-box validate[required]">
   <option  value="" selected="selected">Select One</option>
   <?php
	 $user_master="user_master"; 
	 $var= $db->select($user_master,"status=1");
	foreach($var as $var1)
	{
		?>             
   <option value="<?php echo $var1['user_id']; ?>" <?php if($result['user_id']==$var1['user_id']) { ?> selected="selected" <?php } ?> > <?php echo $var1['first_name']." ".$var1['last_name'];?>  </option> 
   <?php } ?>   
   </select>
   </td>
 </tr> 
  
<!--<tr>
   <td align="right" valign="middle">Place Name <p1 class="c">*</p1></td>
   <td align="center" valign="middle">:</td>
   <td align="left" valign="middle">
   <select name="place_id" id="place_id" class="select-box validate[required]">
   <option  value="" selected="selected">Select One</option>
   <?php
	 $place_master="place_master"; 
	 $var= $db->select($place_master,"status=1");
	foreach($var as $var1)
	{
		?>             
   <option value="<?php echo $var1['place_id']; ?>" <?php if($result['place_id']==$var1['place_id']) { ?> selected="selected" <?php } ?> > <?php echo $var1['place_name'];?>  </option> 
   <?php } ?>   
   </select>
   </td>
 </tr>  -->

<tr>
   <td align="right" valign="middle">Category Name <p1 class="c">*</p1></td>
   <td align="center" valign="middle">:</td>
   <td align="left" valign="middle">
   <select name="category_id" id="category_id" class="select-box validate[required]">
   <option  value="" selected="selected">Select One</option>
   <?php
	 $category_master="category_master"; 
	 $var= $db->select($category_master,"status=1");
	foreach($var as $var1)
	{
		?>             
   <option value="<?php echo $var1['cat_id']; ?>" <?php if($result['category_id']==$var1['cat_id']) { ?> selected="selected" <?php } ?> > <?php echo $var1['name'];?>  </option> 
   <?php } ?>   
   </select>
   </td>
 </tr>    
 
  <tr>
    <td width="200" align="right" valign="middle"> Feed Name
      <p1 class="c">*</p1></td>
    <td width="10" align="center" valign="middle">:</td>
    <td align="left" valign="middle"><label for="feed_name"></label>
      <input name="feed_name" type="text" class="validate[required] text-input" id="feed_name" required value="<?php echo $result['feed_name']; ?>" size="30" /></td>
  </tr>
  <!--<tr>
    <td width="200" align="right" valign="middle"> Feed Link
      <p1 class="c">*</p1></td>
    <td width="10" align="center" valign="middle">:</td>
    <td align="left" valign="middle"><label for="feed_link"></label>
      <input name="feed_link" type="text" class="validate[required] text-input" id="feed_link" value="<?php echo $result['feed_link']; ?>" size="30" /></td>
  </tr> -->
  
 <tr>
    <td width="200" align="right" valign="middle"> Location Name
      <p1 class="c">*</p1></td>
    <td width="10" align="center" valign="middle">:</td>
    <td align="left" valign="middle"><label for="location_name"></label>
      <input name="location_name" type="text" class="validate[required] text-input" id="location_name" value="<?php echo $result['location_name']; ?>" size="30" /></td>
  </tr> 
  
 <tr>
    <td width="200" align="right" valign="middle"> Lattitude
      <p1 class="c">*</p1></td>
    <td width="10" align="center" valign="middle">:</td>
    <td align="left" valign="middle"><label for="lattitude"></label>
      <input name="lattitude" type="text" class="validate[required] text-input" id="lattitude" required value="<?php echo $result['lattitude']; ?>" size="30" /></td>
  </tr>
   <tr>
    <td width="200" align="right" valign="middle"> Longitude
      <p1 class="c">*</p1></td>
    <td width="10" align="center" valign="middle">:</td>
    <td align="left" valign="middle"><label for="longitude"></label>
      <input name="longitude" type="text" class="validate[required] text-input" id="longitude" required value="<?php echo $result['longitude']; ?>" size="30" /></td>
  </tr>
  
  <tr>
    <td width="200" align="right" valign="middle"> Experience
      <p1 class="c">*</p1></td>
    <td width="10" align="center" valign="middle">:</td>
    <td align="left" valign="middle"><label for="experiance"></label>
      <input name="experiance" type="text" class="validate[required] text-input" id="experiance" value="<?php echo $result['experiance']; ?>" size="30" /></td>
  </tr> 
  
   <tr>
    <td width="200" align="right" valign="middle"> Feed Picture </td>
    <td width="10" align="center" valign="middle">:</td>
    <td align="left" valign="middle"><label for="image"></label>
      <input name="image" type="file" id="image" value="" size="30" /></td> 
	  <td rowspan="8">
	  <img src="../uploads/feed_pics/<?php echo($result['image']!='')?$result['image']:'default.jpg';?>" height="150px" width="150px">
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