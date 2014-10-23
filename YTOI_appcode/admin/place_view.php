<?php
include("class/DB.class.php");
$db = new DB();
$db->connect();
$tbl_name="place_master";
$default_url=URL."home.php?";
$id=$_REQUEST["id"];
$result=$db->select_one($tbl_name,"place_id='".$id."'");
?>
<form action="#" method="post" name="my_form" id="my_form" enctype="multipart/form-data" >
      <input type="hidden" name="place_id" id="place_id" value="<?php echo $result['place_id']; ?>" />
  <table width="100%" border="0" cellspacing="0" cellpadding="5" class="table_add">
  <tr>
    <td align="left" valign="middle" bgcolor="#CCCCCC" class="td_heading"><strong>Place Information </strong></td>
  </tr>
  <tr>
    <td align="left" valign="middle">
    <table width="100%" border="0" cellspacing="0" cellpadding="5">
  <tr>
    <td width="200" align="right" valign="middle"> Place Name </td>
    <td width="10" align="center" valign="middle">:</td>
    <td align="left" valign="middle"><label for="place_name"></label>
      <?php echo $result['place_name']; ?></td>
	 <td rowspan="4">
	  <img src="../uploads/place_pics/<?php echo ($result['image']!='')?$result['image']:'default.jpg'; ?>" height="150px" width="150px">
	  </td>
  </tr>
   <tr>
    <td width="200" align="right" valign="middle"> Description </td>
    <td width="10" align="center" valign="middle">:</td>
    <td align="left" valign="middle"><label for="description"></label>
      <?php echo $result['description']; ?></td>
  </tr>
  <tr>
    <td width="200" align="right" valign="middle"> Place Type </td>
    <td width="10" align="center" valign="middle">:</td>
    <td align="left" valign="middle"><label for="type"></label>
      <?php echo $result['type']; ?></td>
  </tr>
  
  <tr>
    <td width="200" align="right" valign="middle"> Latitude </td>
    <td width="10" align="center" valign="middle">:</td>
    <td align="left" valign="middle"><label for="lattitude"></label>
      <?php echo $result['lattitude']; ?></td>
  </tr>
  <tr>
    <td width="200" align="right" valign="middle"> Longitude </td>
    <td width="10" align="center" valign="middle">:</td>
    <td align="left" valign="middle"><label for="longitude"></label>
      <?php echo $result['longitude']; ?></td>
  </tr>
 
  <?php if($id!='') { ?>
  <?php } ?>
  
 
    </table>

    </td>
  </tr>
  
  </table>
  </form>