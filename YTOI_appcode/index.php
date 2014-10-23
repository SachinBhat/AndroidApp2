<?php
if(!isset($_REQUEST['page']))
{
	header('Location: admin/index.php?page=login');
	exit;
}
if(isset($_REQUEST['page']))
{
    
	include('controller/' . $_REQUEST['page'] . '.php');
	include('view/' . $_REQUEST['page'] . '.php');
}
?>