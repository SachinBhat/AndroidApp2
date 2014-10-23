<?php

	require_once ('pagination.php');

//connect to the mysql
$db = @mysql_connect('localhost', 'root', '') or die("Could not connect database");
@mysql_select_db('pagination', $db) or die("Could not select database");

?>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />
<title>How To Create A Pagination In PHP</title>

<!-- css skin -->
<link rel="stylesheet" type="text/css" href="style2.css" />
<style type="text/css">
a {
color:#333;
text-transform: capitalize;
}
a:hover{
color: #999;
text-decoration:underline
}
</style>
</head>
<body>
<?php

	$page = (int) (!isset($_GET["page"]) ? 1 : $_GET["page"]);
	$page = ($page == 0 ? 1 : $page);
	$perpage = 1;//limit in each page
	$startpoint = ($page * $perpage) - $perpage;

	$sql = @mysql_query("select * FROM `tutorials` order by id desc LIMIT $startpoint,$perpage");

while($Row = mysql_fetch_array($sql)) {

	echo '<p style="text-align: center;"><a target="_blank" href="'.$Row['url'].'">'.$Row['title'].'</a></p>';
	
}

	//show pages
	echo Pages("tutorials",$perpage,"index.php?");
	
?>
<p><a>Your limit per page is = '<?php echo $perpage;?>'</a></p>
<a href="#">Back to How To Create A Pagination In PHP</a>
</body>
</html>