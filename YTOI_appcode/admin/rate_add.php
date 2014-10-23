<?php
include("class/DB.class.php");
//error_reporting(0);
$db = new DB();
$db->connect();
$tbl_name="rate_master";
$default_url=URL."home.php?";
$id=$_REQUEST["id"];
if($_REQUEST['action']=="edit")
{
	$result=$db->select_one($tbl_name,"id='".$id."'");	
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
    <div class="content-header-midd"> <span>Rate Master</span>
      <ul class="content-box-tabs">
        <li><span><a href="<?php echo $default_url."p=29" ?>" title="View Records">View</a></span></li>
        <li><span class="current"><a href="<?php echo $default_url."p=30" ?>" title="Add New Record">Add</a></span></li>
      </ul>
    </div>
    <div class="content-header-right"></div>
    <div class="content-box-content">
    <?php 
	if($id!='')
	{
		$page="rate_process.php?e=1";
	}
	else
	{
		$page="rate_process.php";
	}

	?>
		
      <form action="<?php echo $page; ?>" method="post" name="my_form" id="my_form" enctype="multipart/form-data" >
      <input type="hidden" name="id" id="id" value="<?php echo $result['id']; ?>" />
      <table width="100%" border="0" cellspacing="0" cellpadding="5" class="table_add">
  <tr>
    <td align="left" valign="middle" bgcolor="#CCCCCC" class="td_heading"><strong>Rate Information </strong></td>
  </tr>
  <tr>
    <td align="left" valign="middle">
    <table width="100%" border="0" cellspacing="0" cellpadding="5">
	 <tr>
        <td colspan="3" align="right"><p1 class="c">* Indicate Required Fields</p1></td> 
    </tr>
  <tr>
    <td width="200" align="right" valign="middle"> User Id <p1 class="c">*</p1></td>
    <td width="10" align="center" valign="middle">:</td>
    <td align="left" valign="middle"><label for="user_id"></label>
      <input name="user_id" type="text" class="validate[required] text-input" id="user_id" required value="<?php echo $result['user_id']; ?>" size="30" /></td>
  </tr>
  <tr>
    <td width="200" align="right" valign="middle"> Rated User Id </td>
    <td width="10" align="center" valign="middle">:</td>
    <td align="left" valign="middle"><label for="rated_user_id"></label> 
	 <input name="rated_user_id" type="text" class="validate[required] text-input" id="rated_user_id" required value="<?php echo $result['rated_user_id']; ?>" size="30" />
	</td>
  </tr> 
  <tr>
    <td width="200" align="right" valign="middle"> Place Id </td>
    <td width="10" align="center" valign="middle">:</td>
    <td align="left" valign="middle"><label for="place_id"></label> 
	 <input name="place_id" type="text" class="validate[required] text-input" id="place_id" required value="<?php echo $result['place_id']; ?>" size="30" />
	</td>
  </tr> 
   <tr>
    <td width="200" align="right" valign="middle"> Image </td>
    <td width="10" align="center" valign="middle">:</td>
    <td align="left" valign="middle"><label for="file"></label>
      <input name="file" type="file" id="file" value="" size="30" /></td> 
	  <td rowspan="8">
	  <img src="../uploads/iteam_pics/<?php echo(isset($result['image'])?$result['image']:'default.jpg');?>" height="150px" width="170px">
	  </td>
  </tr> 
  <tr>
    <td width="200" align="right" valign="middle"> Meme </td>
    <td width="10" align="center" valign="middle">:</td>
    <td align="left" valign="middle"><label for="meme"></label> 
	 <input name="meme" type="text" class="validate[required] text-input" id="meme" value="<?php echo $result['meme']; ?>" size="30" />
	</td>
  </tr> 
  <tr>
    <td width="200" align="right" valign="middle"> Iteam id </td>
    <td width="10" align="center" valign="middle">:</td>
    <td align="left" valign="middle"><label for="iteam_id"></label> 
	 <input name="iteam_id" type="text" class="validate[required] text-input" id="iteam_id" value="<?php echo $result['iteam_id']; ?>" size="30" />
	</td>
  </tr> 
  <tr>
    <td width="200" align="right" valign="middle"> Comments </td>
    <td width="10" align="center" valign="middle">:</td>
    <td align="left" valign="middle"><label for="comments"></label> 
	 <input name="comments" type="text" class="validate[required] text-input" id="comments"value="<?php echo $result['comments']; ?>" size="30" />
	</td>
  </tr> 
  <tr>
    <td width="200" align="right" valign="middle"> Slider </td>
    <td width="10" align="center" valign="middle">:</td>
    <td align="left" valign="middle"><label for="slider"></label> 
	 <input name="slider" type="text" class="validate[required] text-input" id="slider" value="<?php echo $result['slider']; ?>" size="30" />
	</td>
  </tr> 
  <tr>
    <td width="200" align="right" valign="middle"> Must Try </td>
    <td width="10" align="center" valign="middle">:</td>
    <td align="left" valign="middle"><label for="must_try"></label> 
	 <input name="must_try" type="text" class="validate[required] text-input" id="must_try" value="<?php echo $result['must_try']; ?>" size="30" />
	</td>
  </tr>

  <tr>
    <td width="200" align="right" valign="middle"> Experts </td>
    <td width="10" align="center" valign="middle">:</td>
    <td align="left" valign="middle"><label for="Experts"></label> 
	 <input name="experts" type="text" class="validate[required] text-input" id="experts" value="<?php echo $result['experts']; ?>" size="30" />
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