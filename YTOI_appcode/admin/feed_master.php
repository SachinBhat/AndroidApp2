<?php
include("class/DB.class.php");
$db = new DB();
$db->connect();
$tbl_name="feed_master";
$default_url=URL."home.php?";
?>
<script language="javascript">
$(function() {
   $('#err').fadeOut(5000);	
  $('#chk_checkall').click(function() {
    var c = this.checked;
    $(':checkbox').prop('checked',c);
});
	$("#frm_club").submit(function(){
		var ct = $("[type='checkbox']:checked").length;
		if(ct==0)
		{
			alert('Please select atleast one checkbox to continue !!');
			return false;
		}
		else
		{
			return true;
		}
	})
	$('.dialog_link').click(function(){
					$('#dialog').dialog('open');
					var val=$(this).attr("id");
					$('#dialog').load("feed_view.php?id="+val);
					return false;
	});
	
});
</script>

<div id="dialog" title="Feed Master">
</div>
<div class="MiddleArea align">
  <div class="content-box">
    <div class="content-header-left"></div>
    <div class="content-header-midd"> <span>Feed  Master</span>
      <ul class="content-box-tabs">
        <li><span class="current"><a href="home.php?p=2" title="View Records">View</a></span></li>
        <li><span><a href="<?php echo $default_url."p=3" ?>" title="Add New Record">Add</a></span></li>
      </ul>
    </div>
    <div class="content-header-right"></div>
    <div class="content-box-content">
	
    <div id="searc_box" style="padding-bottom:5px;">
    <fieldset>
    <legend>Search By</legend>
    <form action="#" method="get" name="frm_search">
    <input type="hidden" name="p" value="2" />
    <table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td width="40%" align="right" valign="middle">Feed Name:</td>
    <td width="150" align="left" valign="middle"><label for="feed_name"></label>
    <input type="text" name="feed_name" id="feed_name" value="<?php echo(isset($_GET['feed_name'])?$_GET['feed_name']:'');?>" /></td>
    <td align="left" valign="middle" width=""><input type="submit" name="search" value="Search"  /></td>
    </tr>
 
    </table>
</form>
    </fieldset>
    </div>
 <table>
	  <tr>
	  <td>
	<?php if($_REQUEST['msg']=='add') { ?>

                <center><div id="err" class="err"><span style="text-align:center;">Feed information has been added successfully !!</span></div></center>

    <?php } ?>
	<?php if($_REQUEST['msg']=='update') { ?>

                <center><div id="err" class="err"><span style="text-align:center;">Feed information has been updated successfully !!</span></div></center>

    <?php } ?>
	<?php if($_REQUEST['msg']=='delete') { ?>

                <center><div id="err" class="err"><span style="text-align:center;">Feed  has been deleted successfully !!</span></div></center>

    <?php } ?>
	<?php if($_REQUEST['msg']=='active') { ?>

                <center><div id="err" class="err"><span style="text-align:center;">Feed has been activated successfully !!</span></div></center>

    <?php } ?>
	<?php if($_REQUEST['msg']=='deactive') { ?>

                <center><div id="err" class="err"><span style="text-align:center;">Feed has been deactivated successfully !!</span></div></center>

    <?php } ?>

	</td>
	</tr>
	</table>
    <div id="ajax_content"> 
      <form method="post" name="frm_club" id="frm_club" action="<?php echo URL; ?>feed_delete_process.php">
      <table width="100%" border="0" align="center" cellpadding="0" cellspacing="0" class="Listing">
        <thead>
          <tr>
            <th width="20" align="center" valign="middle"><input class="checkall" type="checkbox" name="chk_checkall" id="chk_checkall" /></th>
            <th align="left" valign="middle">Feed Name</th>
			<th align="left" valign="middle">Category</th>
            <th align="left" valign="middle">User Name</th>
			<th align="center" valign="middle">Date & Time</th>
			<th align="center" valign="middle">Comments</th>
			 <th align="center" valign="middle">Status</th>
            <th align="left" valign="middle">Action</th>
          </tr>
        </thead>
        <tbody>
        <?php
		$where=" ";
		if(isset($_REQUEST['search']))
		{
			if($_REQUEST['feed_name']!='')
			{
				$where.="feed_name like '".trim($_REQUEST['feed_name'])."%' AND ";
			}
				
		}
		
		if(isset($_REQUEST['id'])!='')
		{
				$where.="user_id='".$_REQUEST['id']."' AND ";
		}
		
		$where.="feed_id!='' ORDER BY feed_id DESC LIMIT $startpoint,$perpage";
		$query=$db->select($tbl_name,$where);
		$rows=mysql_affected_rows();
		if($rows>=1)
		{
			$i=1;
			foreach($query as $val)
			{
				if($i%2==0)
				{
					$tr_cls="alt-row";
				}
				else
				{
					$tr_cls="";
				}
				$cat=$db->select_one("category_master","cat_id='".$val["category_id"]."'");
				$place=$db->select_one("place_master","place_id='".$val["place_id"]."'");
				$user=$db->select_one("user_master","user_id='".$val["user_id"]."'");
				
				$comm = "SELECT *  FROM comments_master c WHERE c.feed_id=".$val['feed_id'];
				$comm1 = mysql_query($comm);
				$cmt = mysql_num_rows($comm1);
		 ?>
          <tr class="<?php echo $tr_cls; ?>">
            <td align="center" valign="middle"><input class="check-all" type="checkbox" name="chk[]" id="chk_all" value="<?php echo $val["feed_id"]; ?>" />
			</td>
			
            <td align="left" valign="middle"><a href="#" class="dialog_link" title="View Record" id="<?php echo $val['feed_id']; ?>"><?php echo $val['feed_name']; ?><!--</a>-->
			</td>
			
            <td align="left" valign="middle"><?php echo $cat['name']; ?></td>
			<td align="left" valign="middle"><?php echo $user['first_name']." ".$user['last_name']; ?></td>
			<td align="center" valign="middle"><?php echo $val['created_date']; ?></td>
			<td align="center" valign="middle"><a href="home.php?p=19&id=<?=$val["feed_id"]?>" title="View Comments"><?=($cmt==0)?'No Comment':$cmt." Comment"?></a></td>
			<td align="center" valign="middle">
            <?php if($val["status"]==1) { $img_name="active.png"; } else { $img_name="inactive.png"; } ?>
            <img src="images/<?php echo $img_name;  ?>" alt="Activate" />
            </td>
			
            <td align="left" valign="middle"><a href="home.php?p=3&id=<?php echo $val["feed_id"]; ?>&action=edit" title="Edit Record"><img src="images/edit.png" width="16" height="16" border="0" /></a>&nbsp;&nbsp;<a href="#" class="dialog_link" title="View Record" id="<?php echo $val['feed_id']; ?>"><img src="images/search.png" width="16" height="16" border="0" />
			</a>
			</td>
			
          </tr>
          <?php  $i++; } } else { ?>
          		<?php echo $db->no_record("8"); ?>
          <?php } ?>
        </tbody>
        <?php if($rows!=0) { ?>
        <tfoot>
          <tr align="right">
            <td colspan="8">
            <div class="with_selected">
            	<div class="select_class">With Selected</div> 
            </div>
            <div class="delete_btn"><input type="submit" name="delete" id="delete" value="Delete" title="Delete" onclick="return confirm('Do you want to delete ?');" />&nbsp;&nbsp;<input type="submit" name="active" id="active" value="Active" title="Active" />&nbsp;&nbsp;<input type="submit" name="inactive" id="inactive" value="Inactive" title="Inactive" /></div> 
            <div class="pagination">
            <?php
			  $query="SELECT count(*) as num FROM ".$tbl_name." WHERE ".$where;
			  $dx=explode("ORDER",$query);
			  $query=$dx[0];
			  $path=$default_url."p=2&id=".$_GET['id'];
			  $path.="&";
			  echo pages_wherequery($query,$perpage,$path); ?>
             </div>
              <!-- End .pagination -->
              
              <div class="clear"></div></td>
          </tr>
        </tfoot>
        <?php } ?>
      </table>
      </form>
      </div>
    </div>
  </div>
</div>