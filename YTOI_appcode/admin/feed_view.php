<?php
include("class/DB.class.php");
$db = new DB();
$db->connect();
$tbl_name="feed_master";
$default_url=URL."home.php?";
$id=$_REQUEST["id"];
$result=$db->select_one($tbl_name,"feed_id='".$id."'");
?>
<form action="#" method="post" name="my_form" id="my_form" enctype="multipart/form-data" >
      <input type="hidden" name="feed_id" id="feed_id" value="<?php echo $result['feed_id']; ?>" />
  <table width="100%" border="0" cellspacing="0" cellpadding="5" class="table_add">
  <tr>
    <td align="left" valign="middle" bgcolor="#CCCCCC" class="td_heading"><strong>Feed Information </strong></td>
  </tr>
  <tr>
    <td align="left" valign="middle">
    <table width="100%" border="0" cellspacing="0" cellpadding="5">
  <tr>
   <td align="right" valign="middle">User Name</td>
   <td align="center" valign="middle">:</td>
   <td align="left" valign="middle">
   
   <?php
	 $user_master="user_master"; 
	 $var= $db->select($user_master,"status=1");
	foreach($var as $var1)
	{
		if($result['user_id']==$var1['user_id']) {  echo $var1['first_name']." ".$var1['last_name']; }
	} ?>   
  
   </td>
 </tr> 
  
<!--<tr>
   <td align="right" valign="middle">Place Name</td>
   <td align="center" valign="middle">:</td>
   <td align="left" valign="middle">
   
   <?php
	 $place_master="place_master"; 
	 $var= $db->select($place_master,"status=1");
	foreach($var as $var1)
	{
		if($result['place_id']==$var1['place_id']) {  echo $var1['place_name'];} 
	} ?>   
  
   </td>
 </tr> --> 

<tr>
   <td align="right" valign="middle">Category Name</td>
   <td align="center" valign="middle">:</td>
   <td align="left" valign="middle">
   
   <?php
	 $category_master="category_master"; 
	 $var= $db->select($category_master,"status=1");
	foreach($var as $var1)
	{
		if($result['category_id']==$var1['cat_id']) {  echo $var1['name']; } 
    } ?>   
 
   </td>
 </tr>    
 
  <tr>
    <td width="200" align="right" valign="middle"> Feed Name</td>
    <td width="10" align="center" valign="middle">:</td>
    <td align="left" valign="middle"><label for="feed_name"></label>
      <?php echo $result['feed_name']; ?></td>
  </tr>
  <!--<tr>
    <td width="200" align="right" valign="middle"> Feed Link
      <p1 class="c">*</p1></td>
    <td width="10" align="center" valign="middle">:</td>
    <td align="left" valign="middle"><label for="feed_link"></label>
      <input name="feed_link" type="text" class="validate[required] text-input" id="feed_link" value="<?php echo $result['feed_link']; ?>" size="30" /></td>
  </tr> -->
  
 <tr>
    <td width="200" align="right" valign="middle"> Location Name</td>
    <td width="10" align="center" valign="middle">:</td>
    <td align="left" valign="middle"><label for="location_name"></label>
      <?php echo $result['location_name']; ?></td>
  </tr>
  
<tr>
    <td width="200" align="right" valign="middle"> Lattitude</td>
    <td width="10" align="center" valign="middle">:</td>
    <td align="left" valign="middle"><label for="lattitude"></label>
      <?php echo $result['lattitude']; ?></td>
  </tr>
   <tr>
    <td width="200" align="right" valign="middle"> Longitude</td>
    <td width="10" align="center" valign="middle">:</td>
    <td align="left" valign="middle"><label for="longitude"></label>
      <?php echo $result['longitude']; ?></td>
  </tr>
  
  <tr>
    <td width="200" align="right" valign="middle"> Experience</td>
    <td width="10" align="center" valign="middle">:</td>
    <td align="left" valign="middle"><label for="experiance"></label>
     <?php echo $result['experiance']; ?></td>
  </tr> 
  
   <tr>
    <td width="200" align="right" valign="middle"> Feed Picture </td>
    <td width="10" align="center" valign="middle">:</td>
    <td align="left" valign="middle"><label for="image"></label>

	  <img src="../uploads/feed_pics/<?php echo($result['image']!='')?$result['image']:'default.jpg';?>" height="150px" width="150px">
	  </td>
  </tr> 
  
  <?php if($id!='') { ?>
  <?php } ?>
  
 
    </table>

    </td>
  </tr>
  
  </table>
  </form>