<?php
include("class/DB.class.php");
error_reporting(0);
$db = new DB();
$db->connect();
$tbl_name="place_master";
$default_url=URL."home.php?";
$id=$_REQUEST["id"];
if($_REQUEST['action']=="edit")
{
	$result=$db->select_one($tbl_name,"place_id='".$id."'");	
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


<script language="javascript">
  $(function(){
	  $(".err").fadeOut(5000);
	  $("#non").hide(5000);
  });
</script>

<div class="MiddleArea align">
  <div class="content-box">
    <div class="content-header-left"></div>
    <div class="content-header-midd"> <span>Place Master</span>
      <ul class="content-box-tabs">
        <li><span><a href="<?php echo $default_url."p=4" ?>" title="View Records">View</a></span></li>
        <li><span class="current"><a href="<?php echo $default_url."p=5" ?>" title="Add New Record">Add</a></span></li>
      </ul>
    </div>
    <div class="content-header-right"></div>
    <div class="content-box-content">
    <?php 
	if($id!='')
	{
		$page="place_process.php?e=1";
	}
	else
	{
		$page="place_process.php";
	}

	?>
	
	  <form action="<?php echo $page; ?>" method="post" name="my_form" id="my_form" enctype="multipart/form-data" >
      <input type="hidden" name="place_id" id="place_id" value="<?php echo $result['place_id']; ?>" />
      <table width="100%" border="0" cellspacing="0" cellpadding="5" class="table_add">
  <tr>
    <td align="left" valign="middle" bgcolor="#CCCCCC" class="td_heading"><strong>Place Information </strong></td>
  </tr>
  <tr>
    <td align="left" valign="middle">
    <table width="100%" border="0" cellspacing="0" cellpadding="5">
	 <tr>
        <td colspan="4" align="right"><p1 class="c">* Indicate Required Fields</p1></td> 
    </tr>
  <tr>
    <td width="200" align="right" valign="middle"> Place Name
      <p1 class="c">*</p1></td>
    <td width="10" align="center" valign="middle">:</td>
    <td align="left" valign="middle"><label for="place_name"></label>
      <input name="place_name" type="text" class="validate[required] text-input" id="place_name" value="<?php echo $result['place_name']; ?>" size="30" /></td>
  </tr>
  <tr>
    <td width="200" align="right" valign="middle"> Decsription </td>
    <td width="10" align="center" valign="middle">:</td>
    <td align="left" valign="middle"><label for="decsription"></label>
	  <textarea cols="24" rows="5" name="description"><?php echo $result['description']; ?></textarea>
	  </td>
  </tr>
	<tr>
    <td width="200" align="right" valign="middle"> Place Type </td>
    <td width="10" align="center" valign="middle">:</td>
    <td align="left" valign="middle"><label for="type"></label>
      <input name="type" type="text" id="type" value="<?php echo $result['type']; ?>" size="30" /></td>
  </tr>
 
  <tr>
    <td width="200" align="right" valign="middle"> Latitude<p1 class="c">*</p1> </td>
    <td width="10" align="center" valign="middle">:</td>
    <td align="left" valign="middle"><label for="lattitude"></label>
      <input name="lattitude" type="text" id="lattitude" value="<?php echo $result['lattitude']; ?>" size="30" class="validate[required] text-input" /></td>
  </tr>
  <tr>
    <td width="200" align="right" valign="middle"> Longitude <p1 class="c">*</p1></td>
    <td width="10" align="center" valign="middle">:</td>
    <td align="left" valign="middle"><label for="longitude"></label>
      <input name="longitude" type="text" id="longitude" value="<?php echo $result['longitude']; ?>" size="30" class="validate[required] text-input"/></td>
  </tr>  
  <tr>
    <td width="200" align="right" valign="middle">Place Picture </td>
    <td width="10" align="center" valign="middle">:</td>
    <td align="left" valign="middle"><label for="image"></label>
      <input name="image" type="file" id="image" size="30" /></td>
	  <td rowspan="8">
	  <img src="../uploads/place_pics/<?php echo($result['image']!='')?$result['image']:'default.jpg';?>" height="150px" width="150px">
	  </td>
  </tr>	
 <!-- <tr>
    <td width="200" align="right" valign="middle">Rating Image </td>
    <td width="10" align="center" valign="middle">:</td>
    <td align="left" valign="middle"><label for="rate_image"></label>
      <input name="rate_image" type="file" id="rate_image" size="30" /></td>
	  <td rowspan="8">
	  <img src="../uploads/place_pics/<?php echo(isset($result['image'])?$result['image']:'default.jpg');?>" height="122px" width="320px">
	  </td>
  </tr>	-->
  
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