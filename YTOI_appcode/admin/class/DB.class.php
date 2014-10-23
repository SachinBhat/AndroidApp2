<style type="text/css">
.img_disp
{
 	border:1px solid #ccc;
	border-radius:5px;
	margin:2px;
	cursor:pointer;
}
</style>
<?php
error_reporting(E_ERROR | E_WARNING | E_PARSE);
//include("my-resize-class.php");
function getExtension($str) 
{
     $i = strrpos($str,".");
     if (!$i) 
	 { return ""; }
	 $l = strlen($str) - $i;
	 $ext = substr($str,$i+1,$l);
	 return $ext;
}

class DB {

	protected $db_name = 'lakadia7_ytoi';
	protected $db_user = 'lakadia7_ytoi';
	protected $db_pass = 'ytoi#007';
	protected $db_host = 'localhost'; 
	
	
	/*protected $db_name = 'ytoicoin_ytoilive';
	protected $db_user = 'ytoicoin_amit';
	protected $db_pass = 'Mn=;cDKg2VPh';
	protected $db_host = '109.163.228.47';*/

	//open a connection to the database. Make sure this is called
	//on every page that needs to use the database.
	public function connect() {
		$connection = mysql_connect($this->db_host, $this->db_user, $this->db_pass);
		mysql_select_db($this->db_name);

		return true;
	}

	//takes a mysql row set and returns an associative array, where the keys
	//in the array are the column names in the row set. If singleRow is set to
	//true, then it will return a single row instead of an array of rows.
	public function processRowSet($rowSet)
	{
		$resultArray = array();
		while($row = mysql_fetch_assoc($rowSet))
		{
			array_push($resultArray, $row);
		}
		return $resultArray;
	}

	//Select rows from the database.
	//returns a full row or rows from $table using $where as the where clause.
	//return value is an associative array with column names as keys.
	public function select($table, $where) {
		  $sql = "SELECT * FROM $table WHERE $where";
		$result = mysql_query($sql) or die(mysql_error());
			return $this->processRowSet($result);
	}
	
	public function select_one($table,$where)
	{
		$sql = "SELECT * FROM $table WHERE $where";
		$result = mysql_query($sql);
		if(mysql_num_rows($result)==1)
		{
			return mysql_fetch_array($result);
		}
	}

	//Updates a current row in the database.
	//takes an array of data, where the keys in the array are the column names
	//and the values are the data that will be inserted into those columns.
	//$table is the name of the table and $where is the sql where clause.
	public function update($data, $table, $where) {
		foreach ($data as $column => $value) {
			$sql = "UPDATE $table SET $column = '".$value."' WHERE $where";
			mysql_query($sql) or die(mysql_error());
		}
		return true;
	}

	//Inserts a new row into the database.
	//takes an array of data, where the keys in the array are the column names
	//and the values are the data that will be inserted into those columns.
	//$table is the name of the table.
	public function insert($data, $table) {

		$columns = "";
		$values = "";

		foreach ($data as $column => $value) {
		
			$columns .= ($columns == "") ? "" : ", ";
			$columns .= $column;
			$values .= ($values == "") ? "" : ", ";
			$values .= '"'.$value.'"';
		}

		$sql = "insert into $table ($columns) values ($values)";

		mysql_query($sql) or die(mysql_error());

		//return the ID of the user in the database.
		return mysql_insert_id();

	}
	
	public function delete($table,$where)
	{
		$qry="DELETE FROM $table WHERE $where";
		return mysql_query($qry);
	}
	
	public function mylink($link,$property)
	{
		$final_link="<a href=$link";
		$final_link.="/>";
		
		return $final_link;
	}
	
	public function no_record($colspan)
	{
		return "<tr>
            <td colspan=$colspan align='center' valign='middle' style='color:#F00;font-size:10pt;'>* No Record Found *</td>
          </tr>";
	}
	
	public function order_type($order_type)
	{
		if($order_type==1) { $type="Food"; }
		if($order_type==2) { $type="Drink"; } 
		if($order_type==3) { $type="Merchandise"; }
		
		return $type;
	}
	
	public function order_status($order_st)
	{
		if($order_st==1) { $st="Pending"; }
		if($order_st==2) { $st="Completed"; } 
		if($order_st==3) { $st="Cancelled"; }
		
		return $st;
	}
	
	 public function image_upload($file,$filename,$path,$thumb_path='')
	 { 
			 $allowedExts = array("jpg", "jpeg", "gif", "png");
			 $extension = end(explode(".", $_FILES[$file]["name"]));
			 if ((($_FILES[$file]["type"] == "image/gif")
			 || ($_FILES[$file]["type"] == "image/jpeg")
			 || ($_FILES[$file]["type"] == "image/png")
			 || ($_FILES[$file]["type"] == "image/pjpeg"))
			 && ($_FILES[$file]["size"] <= 1024 * 50 )
			 && in_array($extension, $allowedExts))
			 {				
					$my= move_uploaded_file($_FILES[$file]['tmp_name'],$path."/".$filename);			
					
				if($thumb_path!='')
				{	
					$resizeObj = new resize($path."/".$filename);					
					// *** 2) Resize image (options: exact, portrait, landscape, auto, crop)	
					$resizeObj -> resizeImage(105, 105, "auto");
					// *** 3) Save image	
					$resizeObj -> saveImage($thumb_path."/".$filename,100);
				}
					return $my;
				}
	 }
	 
	 public function background($file,$filename,$path,$thumb_path='')
	 {
			 $allowedExts = array("jpg");
			 $extension = end(explode(".", $_FILES[$file]["name"]));
			 if (($_FILES[$file]["type"] == "image/jpeg")
			 && ($_FILES[$file]["size"] <= 1024 * 1000)
			 && in_array($extension, $allowedExts))
			 {
				$filename="background.jpg"; 
				$my= move_uploaded_file($_FILES[$file]['tmp_name'],$path."/".$filename);
				  /* if($thumb_path!='')
				  {	
					  $resizeObj = new resize($path."/".$filename);					
					  // *** 2) Resize image (options: exact, portrait, landscape, auto, crop)	
					  $resizeObj -> resizeImage(105, 105, "auto");
					  // *** 3) Save image	
					  $resizeObj -> saveImage($thumb_path."/".$filename,100);
				  }*/
				/*$image = new SimpleImage();
				$path=$path."/";
				$image->load($path.$filename);
				$image->resize(320,480);
				$image->save($path.'thumb/'.$filename);*/

				return $my;
			}
			else
			{
				return false;
			}
	 }
	 
	 public function cat_background($file,$filename,$path,$thumb_path='')
	 {
			$allowedExts = array("jpg");
			$extension = end(explode(".", $_FILES[$file]["name"]));
			if (($_FILES[$file]["type"] == "image/jpeg")
			&& ($_FILES[$file]["size"] <= 1024 * 1000)
			&& in_array($extension, $allowedExts))
			{
				$filename="cat_background.jpg";
				$my= move_uploaded_file($_FILES[$file]['tmp_name'],$path."/".$filename);

				/*$image = new SimpleImage();
				$path=$path."/";
				$image->load($path.$filename);
				$image->resize(320,480);
				$image->save($path.'thumb/'.$filename);*/
				return $my;
			}
			else
			{
				return false;
			}
	 }
	 
	 public function image_disp($image_title,$path,$height="100",$width="100")
	{ 
		if($height!='') { $height=$height; } else { $height='100px'; }
		if($width!='') { $width=$width; } else { $width='100px'; }
		return "<img src='$path' alt='$image_title' border='0' height='$height' title='$image_title' width='$width' style='cursor:pointer;' id='$path' class='img_disp'/>";
	 }
	
	
	function backup_tables($host,$user,$pass,$name,$tables = '*')
	{
		
	   if($tables == '*')
	   { 
		$tables = array();
		$result = mysql_query('SHOW TABLES');
		while($row = mysql_fetch_row($result))
		{
		  $tables[] = $row[0];
		}
	  }
	  else
	  {
		$tables = is_array($tables) ? $tables : explode(',',$tables);
	  }
	  
	  //cycle through
	  foreach($tables as $table)
	  {
		$result = mysql_query('SELECT * FROM '.$table);
		$num_fields = mysql_num_fields($result);
		
		$return.= 'DROP TABLE '.$table.';';
		$row2 = mysql_fetch_row(mysql_query('SHOW CREATE TABLE '.$table));
		$return.= "\n\n".$row2[1].";\n\n";
		
		for ($i = 0; $i < $num_fields; $i++) 
		{
		  while($row = mysql_fetch_row($result))
		  {
			$return.= 'INSERT INTO '.$table.' VALUES(';
			for($j=0; $j<$num_fields; $j++) 
			{
			  $row[$j] = addslashes($row[$j]);
			  $row[$j] = ereg_replace("\n","\\n",$row[$j]);
			  if (isset($row[$j])) { $return.= '"'.$row[$j].'"' ; } else { $return.= '""'; }
			  if ($j<($num_fields-1)) { $return.= ','; }
			}
			$return.= ");\n";
		  }
		}
		$return.="\n\n\n";
	  }
	  
	  //save file
	  $handle = fopen('db-backup-'.time().'-'.(md5(implode(',',$tables))).'.sql','w+');
	  fwrite($handle,$return);
	  fclose($handle);
	  
	  //$file_name='db-backup-'.date().'-'.(md5(implode(',',$tables))).'.sql';
	 // header("Content-type: text/plain");
	 // header("Content-Disposition: attachment; filename=db-backup-".date('Y-m-d H:i:s').".sql");
     // header("Pragma: no-cache");
     // header("Expires: 0");
	 // print $return;
	}	

}

?>