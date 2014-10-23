<?php
include("class/DB.class.php");
$db = new DB();
$db->connect();
$tbl_name="comments_master";
$default_url=URL."home.php?";
$id=$_REQUEST["id"];
$result=$db->select_one($tbl_name,"id='".$id."'");
$feed=$db->select_one("feed_master","feed_id='".$result['feed_id']."'");
$user=$db->select_one("user_master","user_id='".$result['user_id']."'");
?>
<form action="#" method="post" name="my_form" id="my_form" enctype="multipart/form-data" >
      <input type="hidden" name="id" id="id" value="<?php echo $result['id']; ?>" />
  <table width="100%" border="0" cellspacing="0" cellpadding="5" class="table_add">
  <tr>
    <td align="left" valign="middle" bgcolor="#CCCCCC" class="td_heading"><strong>Comments Information </strong></td>
  </tr>
  <tr>
    <td align="left" valign="middle">
    <table width="100%" border="0" cellspacing="0" cellpadding="5">
	<tr>
    <td width="200" align="right" valign="middle"> Comment  </td>
    <td width="10" align="center" valign="middle">:</td>
    <td align="left" valign="middle"><label for="comment"></label>
      <?php echo $result['comment']; ?></td>
  </tr>
  <tr>
    <td width="200" align="right" valign="middle"> Comment On  </td>
    <td width="10" align="center" valign="middle">:</td>
    <td align="left" valign="middle"><label for="post_id"></label>
      <?php echo $feed['feed_name']; ?></td>
  </tr>
  <tr>
    <td width="200" align="right" valign="middle"> Comment By </td>
    <td width="10" align="center" valign="middle">:</td>
    <td align="left" valign="middle"><label for="user_id"></label>
      <?php echo $user['first_name']." ".$user['last_name']; ?></td>
  </tr>
   
   <tr>
    <td width="200" align="right" valign="middle"> Date & Time  </td>
    <td width="10" align="center" valign="middle">:</td>
    <td align="left" valign="middle"><label for="comment"></label>
      <?php echo $result['created_date']; ?></td>
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