<?php
include("class/DB.class.php");
$db = new DB();
$db->connect();
$tbl_name="follow_master";
$default_url=URL."home.php?";
$id=$_REQUEST["id"];
$result=$db->select_one($tbl_name,"id='".$id."'");
$flwng=$db->select_one("user_master","user_id='".$result['user_id']."'");
$flwr=$db->select_one("user_master","user_id='".$result['follower_id']."'");
?>
<form action="#" method="post" name="my_form" id="my_form" enctype="multipart/form-data" >
      <input type="hidden" name="user_id" id="user_id" value="<?php echo $result['user_id']; ?>" />
  <table width="100%" border="0" cellspacing="0" cellpadding="5" class="table_add">
  <tr>
    <td align="left" valign="middle" bgcolor="#CCCCCC" class="td_heading"><strong>Following Information </strong></td>
  </tr>
  <tr>
    <td align="left" valign="middle">
    <table width="100%" border="0" cellspacing="0" cellpadding="5">
  <tr>
    <td width="200" align="right" valign="middle"> Following Name </td>
    <td width="10" align="center" valign="middle">:</td>
    <td align="left" valign="middle"><label for="comment"></label>
      <?php echo $flwng['first_name']." ".$flwng['last_name']; ?></td>
  </tr>
   <tr>
    <td width="200" align="right" valign="middle"> Follower Name  </td>
    <td width="10" align="center" valign="middle">:</td>
    <td align="left" valign="middle"><label for="following_id"></label>
       <?php echo $flwr['first_name']." ".$flwr['last_name']; ?></td>
  </tr>
 
  <?php if($id!='') { ?>
  <?php } ?>
  
 
    </table>

    </td>
  </tr>
  
  </table>
  </form>