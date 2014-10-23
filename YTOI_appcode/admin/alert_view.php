<?php
include("class/DB.class.php");
$db = new DB();
$db->connect();
$tbl_name="alert_master";
$default_url=URL."home.php?";
$id=$_REQUEST["id"];
$result=$db->select_one($tbl_name,"id='".$id."'");
?>
<form action="#" method="post" name="my_form" id="my_form" enctype="multipart/form-data" >
      <input type="hidden" name="id" id="id" value="<?php echo $result['id']; ?>" />
  <table width="100%" border="0" cellspacing="0" cellpadding="5" class="table_add">
  <tr>
    <td align="left" valign="middle" bgcolor="#CCCCCC" class="td_heading"><strong>AlertMessage Information </strong></td>
  </tr>
  <tr>
    <td align="left" valign="middle">
    <table width="100%" border="0" cellspacing="0" cellpadding="5">
  <tr>
    <td width="200" align="right" valign="middle"> User Id </td>
    <td width="10" align="center" valign="middle">:</td>
    <td align="left" valign="middle"><label for="user_id"></label>
      <?php echo $result['user_id']; ?></td>
  </tr>
   <tr>
    <td width="200" align="right" valign="middle"> Device Id  </td>
    <td width="10" align="center" valign="middle">:</td>
    <td align="left" valign="middle"><label for="device_id"></label>
      <?php echo $result['device_id']; ?></td>
  </tr>
  <tr>
    <td width="200" align="right" valign="middle"> Alert Message  </td>
    <td width="10" align="center" valign="middle">:</td>
    <td align="left" valign="middle"><label for="alert_message"></label>
      <?php echo $result['alert_message']; ?></td>
  </tr>
  <tr>
    <td align="right" valign="middle">Status
      </td>
    <td width="10" align="center" valign="middle">:</td>
    <td align="left" valign="middle"><label for="status"></label>
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