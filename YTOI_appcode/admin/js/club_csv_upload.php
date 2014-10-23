<?php
session_start();
include("include/config.inc.php");
include("class/DB.class.php");
include('session_check.php');
$db = new DB();
$db->connect();
ini_set('max_execution_time',180);

$fname=$_FILES['bulk_upload']['name'];
$file_type=explode(".",$fname);
if($file_type[1]=="csv")
{
	$filename=$_FILES['bulk_upload']['tmp_name'];
	if($filename!='')
	{
		if (($handle = fopen($filename, "r")) !== FALSE)
		{
			$length = 1000;
			$delimiter = "|";
			//echo "<pre>";
			$flag=true;
			while (( $data = fgetcsv( $handle, $length, $delimiter ) ) !== FALSE )
			{
				if($flag){ $flag=false; continue; }
				$num = count($data);
				for ($c=0; $c <$num; $c++)
				{
					$v=$data[$c];
					$v=str_replace('"','',$v);
					$dx=explode(',',$v);
					//echo "<pre>";
					//print_r($dx);
					$club_name=trim($dx[1]);
					$club_street_address=trim($dx[2]);
					$club_state=trim($dx[3]);
					$club_city=trim($dx[4]);
					$club_postcode=trim($dx[5]);
					$club_phone=trim($dx[6]);
					$club_email=trim($dx[7]);
					$club_lat=trim($dx[8]);
					$club_long=trim($dx[9]);
					$club_pass=base64_encode('123456');
					
					if($club_email=='')
					{
						$rand=rand(1,9999999999);
						$club_email="defaule_".$rand."@club.com";
					}
					
						//$db->select_one("coz_club_mst","club_email='".$club_email."'");
						//$rows=mysql_affected_rows();
						//if($rows==0)
					//	{
							$data=array("club_name"=>$club_name,
										"club_address"=>$club_street_address,
										"club_area"=>$club_state,
										"club_city_id"=>$club_city,
										"club_zip"=>$club_postcode,
										"club_phone"=>$club_phone,
										"club_email"=>$club_email,
										"club_lat"=>$club_lat,
										"club_long"=>$club_long,
										"club_password"=>$club_pass);
							
							//if($club_name!='' && $club_street_address!='' && $club_state!='' && $club_city!='' && $club_postcode!='' && $club_phone!='' && $club_email!='' && $club_lat!='' && $club_long!='' && $club_pass!='')
							//{			
								$db->insert($data," coz_club_mst");			
						//	}
					//	}
					
				}
			}
	 
			fclose($handle);
		}
		$err="File has been uploaded successfully !!";
	}
	else
	{
		$err="Please enter file to upload";
	}
}
else
{
	$err="Invalid File Type";
}
?>
<script language="javascript">
window.location="<?php echo URL; ?>home.php?p=1&err=<?php echo $err; ?>";
</script>