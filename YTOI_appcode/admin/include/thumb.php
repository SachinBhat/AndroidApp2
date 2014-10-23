<?php
function createthumb($name,$filename,$new_w,$new_h)
{
	if(exif_imagetype($name) == IMAGETYPE_JPEG){$src_img=imagecreatefromjpeg($name);}
	if(exif_imagetype($name) == IMAGETYPE_PNG){$src_img=imagecreatefrompng($name);}
	if(exif_imagetype($name) == IMAGETYPE_GIF){$src_img=imagecreatefromgif($name);}
	
	$old_x=imagesx($src_img);
	$old_y=imagesy($src_img);
	$thumb_w=$new_w;
	$thumb_h=$new_w;
	/*if($new_w >= $old_x)
	{
		$thumb_w=$old_x;
	}
	else if($new_w < $old_x)
	{
		$thumb_w=$new_w;
	}
	else
	{
		$thumb_w=$new_w;
	}
	
	if($new_h >= $old_x)
	{
		$thumb_h=$old_x;
	}
	else if($new_h < $old_x)
	{
		$thumb_h=$new_h;
	}
	else
	{
		$thumb_h=$new_w;
	}*/
	
	$dst_img=imagecreatetruecolor($thumb_w,$thumb_h);
	
	imagecopyresampled($dst_img,$src_img,0,0,0,0,$thumb_w,$thumb_h,$old_x,$old_y); 
	if(exif_imagetype($name) == IMAGETYPE_GIF)
	{
		imagegif($dst_img,$filename); 
	} 
	else if(exif_imagetype($name) == IMAGETYPE_PNG)
	{
		imagepng($dst_img,$filename); 
	}
	else
	{
		imagejpeg($dst_img,$filename); 
	}
	imagedestroy($dst_img); 
}
?>