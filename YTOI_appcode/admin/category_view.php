<?php
include("class/DB.class.php");
$db = new DB();
$db->connect();
$tbl_name="category_master";
$default_url=URL."home.php?";
$cat_id=$_REQUEST["cat_id"];
$result=$db->select_one($tbl_name,"cat_id='".$cat_id."'");
?>
<form action="#" method="post" name="my_form" id="my_form" enctype="multipart/form-data" >
      <input type="hidden" name="cat_id" id="cat_id" value="<?php echo $result['cat_id']; ?>" />
  <table width="100%" border="0" cellspacing="0" cellpadding="5" class="table_add">
  <tr>
    <td align="left" valign="middle" bgcolor="#CCCCCC" class="td_heading"><strong>Category Information </strong></td>
  </tr>
  <tr>
    <td align="left" valign="middle">
    <table width="100%" border="0" cellspacing="0" cellpadding="5">
 
  <tr>
    <td width="200" align="right" valign="middle"> Category Name </td>
    <td width="10" align="center" valign="middle">:</td>
    <td align="left" valign="middle"><label for="name"></label>
      <?php echo $result['name']; ?></td>
  </tr>
   <tr>
    <td width="200" align="right" valign="middle"> Category Link </td>
    <td width="10" align="center" valign="middle">:</td>
    <td align="left" valign="middle"><label for="link"></label>
      <?php echo $result['link']; ?></td>
  </tr>
  <tr>
    <td width="200" align="right" valign="middle"> Selected Image </td>
    <td width="10" align="center" valign="middle">:</td>
    <td align="left" valign="middle"><label for="link"></label>
     <img src="../uploads/category_pics/<?php echo($result['selected_image']!='')?$result['selected_image']:'default.jpg';?>" height="60px" width="60px"></td>
  </tr>
   <tr>
    <td width="200" align="right" valign="middle"> Unselected Image </td>
    <td width="10" align="center" valign="middle">:</td>
    <td align="left" valign="middle"><label for="link"></label>
     <img src="../uploads/category_pics/<?php echo($result['image']!='')?$result['image']:'default.jpg';?>" height="60px" width="60px"></td>
  </tr>
  <tr>
    <td align="right" valign="middle">Status
      </td>
    <td width="10" align="center" valign="middle">:</td>
    <td align="left" valign="middle"><label for="password"></label>
      <?php
	  if($result['status']=='1')
		{
			echo "Active";
		}
	  else
	    {
			echo "Inactive";
		}
	  ?></td>
  </tr>
  <?php if($id!='') { ?>
  <?php } ?>
  
 
    </table>

    </td>
  </tr>
  </table>
  </form>