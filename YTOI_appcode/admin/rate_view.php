<?php
include("class/DB.class.php");
$db = new DB();
$db->connect();
$tbl_name="rate_master";
$default_url=URL."home.php?";
$id=$_REQUEST["id"];
$result=$db->select_one($tbl_name,"id='".$id."'");
?>
<form action="#" method="post" name="my_form" id="my_form" enctype="multipart/form-data" >
  <input type="hidden" name="id" id="id" value="<?php echo $result['id']; ?>" />
  <table width="100%" border="0" cellspacing="0" cellpadding="5" class="table_add">
  <tr>
  <td align="left" valign="middle" bgcolor="#CCCCCC" class="td_heading"><strong>Rate Information </strong></td>
  </tr>
  <tr>
    <td align="left" valign="middle">
    <table width="100%" border="0" cellspacing="0" cellpadding="5">
  <tr>
    <td width="200" align="right" valign="middle"> Id </td>
    <td width="10" align="center" valign="middle">:</td>
    <td align="left" valign="middle"><label for="id"></label>
      <?php echo $result['id']; ?></td>
	  <td rowspan="8">
	  <img src="../uploads/iteam_pics/<?php echo(isset($result['image'])?$result['image']:'default.jpg');?>" height="150px" width="170px">
	  </td>
  </tr>
  <tr>
    <td width="200" align="right" valign="middle"> User Id </td>
    <td width="10" align="center" valign="middle">:</td>
    <td align="left" valign="middle"><label for="user_id"></label>
      <?php echo $result['user_id']; ?></td>
  </tr>
  <tr>
    <td width="200" align="right" valign="middle"> Rated User Id </td>
    <td width="10" align="center" valign="middle">:</td>
    <td align="left" valign="middle"><label for="rated_user_id"></label>
      <?php echo $result['rated_user_id']; ?></td>
  </tr>
  <tr>
    <td width="200" align="right" valign="middle"> Place Id </td>
    <td width="10" align="center" valign="middle">:</td>
    <td align="left" valign="middle"><label for="place_id"></label>
      <?php echo $result['place_id']; ?></td>
  </tr>
  <tr>
    <td width="200" align="right" valign="middle"> Meme </td>
    <td width="10" align="center" valign="middle">:</td>
    <td align="left" valign="middle"><label for="meme"></label>
      <?php echo $result['meme']; ?></td>
  </tr>
  <tr>
    <td width="200" align="right" valign="middle"> Iteam Id </td>
    <td width="10" align="center" valign="middle">:</td>
    <td align="left" valign="middle"><label for="iteam_id"></label>
      <?php echo $result['iteam_id']; ?></td>
  </tr>
  <tr>
    <td width="200" align="right" valign="middle"> Comments </td>
    <td width="10" align="center" valign="middle">:</td>
    <td align="left" valign="middle"><label for="comments"></label>
      <?php echo $result['comments']; ?></td>
  </tr>
  <tr>
    <td width="200" align="right" valign="middle"> Slider </td>
    <td width="10" align="center" valign="middle">:</td>
    <td align="left" valign="middle"><label for="slider"></label>
      <?php echo $result['slider']; ?></td>
  </tr>
  <tr>
    <td width="200" align="right" valign="middle"> Must Try </td>
    <td width="10" align="center" valign="middle">:</td>
    <td align="left" valign="middle"><label for="must_try"></label>
      <?php echo $result['must_try']; ?></td>
  </tr>
  <tr>
    <td width="200" align="right" valign="middle"> Experts </td>
    <td width="10" align="center" valign="middle">:</td>
    <td align="left" valign="middle"><label for="experts"></label>
      <?php echo $result['experts']; ?></td>
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