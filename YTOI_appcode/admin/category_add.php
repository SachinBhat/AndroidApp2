<?php
include("class/DB.class.php");
//error_reporting(0);
$db = new DB();
$db->connect();
$tbl_name="category_master";
$default_url=URL."home.php?";
$id=$_REQUEST["id"];
if($_REQUEST['action']=="edit")
{
	$result=$db->select_one($tbl_name,"cat_id='".$id."'");	
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
    <div class="content-header-midd"> <span>Category Master</span>
      <ul class="content-box-tabs">
        <li><span><a href="<?php echo $default_url."p=31" ?>" title="View Records">View</a></span></li>
        <li><span class="current"><a href="<?php echo $default_url."p=32" ?>" title="Add New Record">Add</a></span></li>
      </ul>
    </div>
    <div class="content-header-right"></div>
    <div class="content-box-content">
    <?php 
	if($id!='')
	{
		$page="category_process.php?e=1";
	}
	else
	{
		$page="category_process.php";
	}

	?>
	
	  <form action="<?php echo $page; ?>" method="post" name="my_form" id="my_form" enctype="multipart/form-data" >
      <input type="hidden" name="cat_id" id="cat_id" value="<?php echo $result['cat_id']; ?>" />
      <table width="100%" border="0" cellspacing="0" cellpadding="5" class="table_add">
  <tr>
    <td align="left" valign="middle" bgcolor="#CCCCCC" class="td_heading"><strong>Category Information </strong></td>
  </tr>
  <tr>
    <td align="left" valign="middle">
    <table width="100%" border="0" cellspacing="0" cellpadding="5">
	 <tr>
        <td colspan="5" align="right"><p1 class="c">* Indicate Required Fields</p1></td> 
    </tr>
  <tr>
    <td width="200" align="right" valign="middle">Category Name
      <p1 class="c">*</p1></td>
    <td width="10" align="center" valign="middle">:</td>
    <td align="left" valign="middle"><label for="name"></label>
      <input name="name" type="text" class="validate[required] text-input" id="name" value="<?php echo $result['name']; ?>" size="30" /></td>
	   <td style="width:360px;"></td>
  </tr>
   <tr>
    <td width="200" align="right" valign="middle">Category Link
      <p1 class="c">*</p1></td>
    <td width="10" align="center" valign="middle">:</td>
    <td align="left" valign="middle"><label for="link"></label>
      <input name="link" type="text" class="validate[required] text-input" id="link" value="<?php echo $result['link']; ?>" size="30" /></td>
	  <td style="width:360px;"></td>
  </tr>
  
   <tr rowspan="3">
    <td width="200" align="right" valign="middle"> Selected Image </td>
    <td width="10" align="center" valign="middle">:</td>
    <td align="left" valign="middle" ><label for="file"></label>
      <input name="selected_image" type="file" id="file" value="" size="30" /> </td>
	  
	<td style="width:360px;">  <img src="../uploads/category_pics/<?php echo($result['selected_image']!='')?$result['selected_image']:'default.jpg';?>" height="60px" width="60px">
	  </td> 
  </tr> 
  
   <tr rowspan="3">
    <td width="200" align="right" valign="middle"> Unselected Image </td>
    <td width="10" align="center" valign="middle">:</td>
    <td align="left" valign="middle" ><label for="file"></label>
      <input name="image" type="file" id="file" value="" size="30" /> </td>
	  
	<td style="width:360px;">  <img src="../uploads/category_pics/<?php echo($result['image']!='')?$result['image']:'default.jpg';?>" height="60px" width="60px">
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