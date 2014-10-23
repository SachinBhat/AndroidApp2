<?php
include("class/DB.class.php");
$db = new DB();
$db->connect();
$tbl_name="iteam_master";
$default_url=URL."home.php?";
$id=$_REQUEST["id"];
$result=$db->select_one($tbl_name,"iteam_id='".$id."'");
?>
<form action="#" method="post" name="my_form" id="my_form" enctype="multipart/form-data" >
      <input type="hidden" name="iteam_id" id="iteam_id" value="<?php echo $result['iteam_id']; ?>" />
  <table width="100%" border="0" cellspacing="0" cellpadding="5" class="table_add">
  <tr>
    <td align="left" valign="middle" bgcolor="#CCCCCC" class="td_heading"><strong>Iteam Information </strong></td>
  </tr>
  <tr>
    <td align="left" valign="middle">
    <table width="100%" border="0" cellspacing="0" cellpadding="5">
  <tr>
    <td width="200" align="right" valign="middle">Iteam Title </td>
    <td width="10" align="center" valign="middle">:</td>
    <td align="left" valign="middle"><label for="title"></label>
      <?php echo $result['title']; ?></td>
	  <td rowspan="3">
	  <img src="../uploads/iteam_pics/<?php echo ($result['image']!='')?$result['image']:'default.jpg'; ?>" height="150px" width="150px">
	  </td>
  </tr>
   <tr>
    <td width="200" align="right" valign="middle"> Description </td>
    <td width="10" align="center" valign="middle">:</td>
    <td align="left" valign="middle"><label for="description"></label>
      <?php echo $result['description']; ?></td>
  </tr>
  
  <?php if($id!='') { ?>
  <?php } ?>
  
 
    </table>

    </td>
  </tr>
  
  </table>
  </form>