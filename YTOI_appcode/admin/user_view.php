<?php
include("class/DB.class.php");
$db = new DB();
$db->connect();
$tbl_name="user_master";
$default_url=URL."home.php?";
$id=$_REQUEST["id"];
$result=$db->select_one($tbl_name,"user_id='".$id."'");
?>
<form action="#" method="post" name="my_form" id="my_form" enctype="multipart/form-data" >
      <input type="hidden" name="venue_id" id="venue_id" value="<?php echo $result['user_id']; ?>" />
  <table width="100%" border="0" cellspacing="0" cellpadding="5" class="table_add">
  <tr>
    <td align="left" valign="middle" bgcolor="#CCCCCC" class="td_heading"><strong>User Information </strong></td>
  </tr>
  <tr>
    <td align="left" valign="middle">
    <table width="100%" border="0" cellspacing="0" cellpadding="5">
  <tr>
    <td width="200" align="right" valign="middle"> User Name
      </td>
    <td width="10" align="center" valign="middle">:</td>
    <td align="left" valign="middle"><label for="user_name"></label>
      <?php echo $result['first_name'].' '.$result['last_name']; ?></td>
	  <td rowspan="8">
	  <img src="../uploads/pro_pics/<?php echo ($result['profile_picture']!='')?$result['profile_picture']:'default.jpg';?>" height="150px" width="150px">
	  </td>
  </tr>
   <tr>
    <td width="200" align="right" valign="middle"> Email
      </td>
    <td width="10" align="center" valign="middle">:</td>
    <td align="left" valign="middle"><label for="email"></label>
      <?php echo $result['email']; ?></td>
  </tr>
  <tr>
    <td width="200" align="right" valign="middle"> Mobile Number
      </td>
    <td width="10" align="center" valign="middle">:</td>
    <td align="left" valign="middle"><label for="mobile_number"></label>
      <?php echo $result['mobile_number']; ?></td>
  </tr>
  <tr>
    <td width="200" align="right" valign="middle"> Button Links
      </td>
    <td width="10" align="center" valign="middle">:</td>
    <td align="left" valign="middle"><label for="button_links"></label>
      <?php echo $result['button_links']; ?></td>
  </tr>
  <tr>
    <td width="200" align="right" valign="middle"> Feed
      </td>
    <td width="10" align="center" valign="middle">:</td>
    <td align="left" valign="middle"><label for="feed"></label>
      <?php echo $result['feed']; ?></td>
  </tr>
  <tr>
    <td width="200" align="right" valign="middle"> Option Box
      </td>
    <td width="10" align="center" valign="middle">:</td>
    <td align="left" valign="middle"><label for="option_box"></label>
      <?php echo $result['option_box']; ?></td>
  </tr>
  <tr>
    <td width="200" align="right" valign="middle"> Reached From
      </td>
    <td width="10" align="center" valign="middle">:</td>
    <td align="left" valign="middle"><label for="reaached_from"></label>
      <?php echo $result['reached_from']; ?></td>
  </tr>
  <tr>
    <td width="200" align="right" valign="middle"> Birth Date
      </td>
    <td width="10" align="center" valign="middle">:</td>
    <td align="left" valign="middle"><label for="birth_date"></label>
      <?php echo $result['birth_date']; ?></td>
  </tr>
  <tr>
    <td width="200" align="right" valign="middle"> Gender
      </td>
    <td width="10" align="center" valign="middle">:</td>
    <td align="left" valign="middle"><label for="Gender"></label>
      <?php echo $result['gender']; ?></td>
  </tr>
  <tr>
    <td width="200" align="right" valign="middle"> Street
      </td>
    <td width="10" align="center" valign="middle">:</td>
    <td align="left" valign="middle"><label for="street"></label>
      <?php echo $result['street']; ?></td>
  </tr>
  <tr>
    <td width="200" align="right" valign="middle"> City</td>
    <td width="10" align="center" valign="middle">:</td>
    <td align="left" valign="middle"><label for="city"></label>
      <?php echo $result['city']; ?></td>
  </tr>
  <tr>
    <td width="200" align="right" valign="middle"> State</td>
    <td width="10" align="center" valign="middle">:</td>
    <td align="left" valign="middle"><label for="state"></label>
      <?php echo $result['state']; ?></td>
  </tr>
  <tr>
    <td width="200" align="right" valign="middle"> Country</td>
    <td width="10" align="center" valign="middle">:</td>
    <td align="left" valign="middle"><label for="country"></label>
      <?php echo $result['country']; ?></td>
  </tr>
  <tr>
    <td width="200" align="right" valign="middle"> Zipcode</td>
    <td width="10" align="center" valign="middle">:</td>
    <td align="left" valign="middle"><label for="zipcode"></label>
      <?php echo $result['zipcode']; ?></td>
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