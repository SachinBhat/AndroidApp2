<?php
include("class/DB.class.php");
$db = new DB();
$db->connect();
$tbl_name="account_master";
$default_url=URL."home.php?";
$id=$_REQUEST["id"];
$result=$db->select_one($tbl_name,"id='".$id."'");
?>
<form action="#" method="post" name="my_form" id="my_form" enctype="multipart/form-data" >
      <input type="hidden" name="id" id="id" value="<?php echo $result['id']; ?>" />
  <table width="100%" border="0" cellspacing="0" cellpadding="5" class="table_add">
  <tr>
    <td align="left" valign="middle" bgcolor="#CCCCCC" class="td_heading"><strong>accountSetting Information </strong></td>
  </tr>
  <tr>
    <td align="left" valign="middle">
    <table width="100%" border="0" cellspacing="0" cellpadding="5">
  <tr>
    <td width="200" align="right" valign="middle"> Id </td>
    <td width="10" align="center" valign="middle">:</td>
    <td align="left" valign="middle"><label for="id"></label>
      <?php echo $result['id']; ?></td>
  </tr>
  <tr>
    <td width="200" align="right" valign="middle"> User Id </td>
    <td width="10" align="center" valign="middle">:</td>
    <td align="left" valign="middle"><label for="user_id"></label>
      <?php echo $result['user_id']; ?></td>
  </tr>
   <tr>
    <td width="200" align="right" valign="middle"> Sound Effect  </td>
    <td width="10" align="center" valign="middle">:</td>
    <td align="left" valign="middle"><label for="sound_effect"></label>
      <?php echo $result['sound_effect']; ?></td>
  </tr>
  <tr>
    <td width="200" align="right" valign="middle"> Font Size  </td>
    <td width="10" align="center" valign="middle">:</td>
    <td align="left" valign="middle"><label for="font_size"></label>
      <?php echo $result['font_size']; ?></td>
  </tr>
  <tr>
    <td width="200" align="right" valign="middle"> Proxy </td>
    <td width="10" align="center" valign="middle">:</td>
    <td align="left" valign="middle"><label for="proxy"></label>
      <?php echo $result['proxy']; ?></td>
  </tr>
   <tr>
    <td width="200" align="right" valign="middle"> Location  </td>
    <td width="10" align="center" valign="middle">:</td>
    <td align="left" valign="middle"><label for="sound_effect"></label>
      <?php echo $result['location']; ?></td>
  </tr>
  <tr>
    <td width="200" align="right" valign="middle"> About  </td>
    <td width="10" align="center" valign="middle">:</td>
    <td align="left" valign="middle"><label for="about"></label>
      <?php echo $result['about']; ?></td>
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