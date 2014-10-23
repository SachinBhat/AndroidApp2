<?php
include("class/DB.class.php");
$db = new DB();
$db->connect();
$tbl_name="rate_master";
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
					$('#dialog').load("rate_view.php?id="+val);
					return false;
	});
	
});
</script>

<div id="dialog" title="Rate Master">
</div>
<div class="MiddleArea align">
  <div class="content-box">
    <div class="content-header-left"></div>
    <div class="content-header-midd"> <span>Rate  Master</span>
      <ul class="content-box-tabs">
        <li><span class="current"><a href="<?php echo $default_url."p=29" ?>" title="View Records">Rate View</a></span></li>
        <li><span><a href="<?php echo $default_url."p=30" ?>" title="Add New Record">Rate Add</a></span></li>
      </ul>
    </div>
    <div class="content-header-right"></div>
    <div class="content-box-content">
	
    <div id="searc_box" style="padding-bottom:5px;">
    <fieldset>
    <legend>Search By</legend>
    <form action="#" method="get" name="frm_search">
    <input type="hidden" name="p" value="29" />
    <table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td width="100" align="right" valign="middle">User Id:</td>
    <td width="150" align="left" valign="middle"><label for="user_id"></label>
    <input type="text" name="user_id" id="user_id" value="<?php echo(isset($_GET['user_id'])?$_GET['user_id']:'');?>" /></td>
    <td align="right" valign="middle" width="15px;">
	<input type="submit" name="search" value="Search"  /></td>
    </tr>
    </table>
</form>
    </fieldset>
    </div>
 <table>
	  <tr>
	  <td>
	<?php if($_REQUEST['msg']=='add') { ?>

                <center><div id="err" class="err"><span style="text-align:center;">Rate information has been added successfully !!</span></div></center>

    <?php } ?>
	<?php if($_REQUEST['msg']=='update') { ?>

                <center><div id="err" class="err"><span style="text-align:center;">Rate information has been updated successfully !!</span></div></center>

    <?php } ?>
	<?php if($_REQUEST['msg']=='delete') { ?>

                <center><div id="err" class="err"><span style="text-align:center;">Rate information has been deleted successfully !!</span></div></center>

    <?php } ?>
	<?php if($_REQUEST['msg']=='active') { ?>

                <center><div id="err" class="err"><span style="text-align:center;">Rate information has been activated successfully !!</span></div></center>

    <?php } ?>
	<?php if($_REQUEST['msg']=='deactive') { ?>

                <center><div id="err" class="err"><span style="text-align:center;">Rate information has been deactivated successfully !!</span></div></center>

    <?php } ?>

	</td>
	</tr>
	</table>
    <div id="ajax_content"> 
      <form method="post" name="frm_club" id="frm_club" action="<?php echo URL; ?>rate_delete_process.php">
      <table width="100%" border="0" align="center" cellpadding="0" cellspacing="0" class="Listing">
        <thead>
          <tr>
            <th width="20" align="center" valign="middle"><input class="checkall" type="checkbox" name="chk_checkall" id="chk_checkall" /></th>
            <th align="left" valign="middle">User Id</th>
			<th align="left" valign="middle">Rated User Id</th>
            <th align="center" valign="middle">Status</th>
            <th align="center" valign="middle">Action</th>
          </tr>
        </thead>
        <tbody>
        <?php
		$where=" ";
		if(isset($_REQUEST['search']))
		{
			if($_REQUEST['user_id']!='')
			{
				$where.="user_id like '".trim($_REQUEST['user_id'])."%' AND ";
			}	
		}
		$where.="user_id!='' ORDER BY user_id ASC LIMIT $startpoint,$perpage";
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
		 ?>
          <tr class="<?php echo $tr_cls; ?>">
            <td align="center" valign="middle"><input class="check-all" type="checkbox" name="chk[]" id="chk_all" value="<?php echo $val["id"]; ?>" /></td>
            <td align="left" valign="middle"><a href="#" class="dialog_link" title="View Record" id="<?php echo $val['id']; ?>"><?php echo $val['user_id']; ?></a></td>
			
			<td align="left" valign="middle"><?php echo $val['rated_user_id']; ?></td>
			<td align="center" valign="middle">
            <?php if($val["status"]==1) { $img_name="active.png"; } else { $img_name="inactive.png"; } ?>
            <img src="images/<?php echo $img_name;  ?>" alt="Activate" />
            </td>
            <td align="center" valign="middle"><a href="home.php?p=30&id=<?php echo $val["id"]; ?>&action=edit" title="Edit Record"><img src="images/edit.png" width="16" height="16" border="0" /></a>&nbsp;&nbsp;
			
			<a href="#" class="dialog_link" title="View Record" id="<?php echo $val['id']; ?>"><img src="images/search.png" width="16" height="16" border="0" /></a></td>
          </tr>
          <?php  $i++; } } else { ?>
          		<?php echo $db->no_record("6"); ?>
          <?php } ?>
        </tbody>
        <?php if($rows!=0) { ?>
        <tfoot>
          <tr align="right">
            <td colspan="6">
            <div class="with_selected">
            	<div class="select_class">With Selected</div> 
            </div>
            <div class="delete_btn"><input type="submit" name="delete" id="delete" value="Delete" title="Delete" onclick="return confirm('Do you want to delete ?');"/>&nbsp;&nbsp;<input type="submit" name="active" id="active" value="Active" title="Active" />&nbsp;&nbsp;<input type="submit" name="inactive" id="inactive" value="Inactive" title="Inactive" /></div> 
            <div class="pagination">
            <?php
			  $query="SELECT count(*) as num FROM ".$tbl_name." WHERE ".$where;
			  $dx=explode("ORDER",$query);
			  $query=$dx[0];
			  $path=$default_url."p=29";
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