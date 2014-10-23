<?php
//include('db.php');
//session_start();
/*
if($user_event_image=='')
{
	$user_event_image='';
}
else
{
	move_uploaded_file($_FILES['user_event_image']['tmp_name'],"event_photo/".$user_event_image);					
	include("my-resize-class.php");
	$path="event_photo/";
	$image = new SimpleImage();
	$image->load($path.$user_event_image);
	$image->resize(50,50);
	$image->save($path.'thumb/'.$user_event_image);
}*/
//ini_set('display_errors',1); 
//error_reporting(E_ALL);
global $imginfo;
 
class SimpleImage
{
   
   var $image;
   var $image_type;
	
	 function checkdimension($filename)
	 {
	  $image_info = getimagesize($filename);
	  $imginfo = explode('"',$image_info[3]);	// W=1 , H=3 // Getting Dimension of image
	  if($imginfo[1]!="768" || $imginfo[3]!="70")
	  {
		echo 'SNT';
		return false;
	  }
	  else
	  { return true; }
	 }
 
   function load($filename) {
      $image_info = getimagesize($filename);
	  
      $this->image_type = $image_info[2];
      if( $this->image_type == IMAGETYPE_JPEG ) {
         $this->image = imagecreatefromjpeg($filename);
      } elseif( $this->image_type == IMAGETYPE_GIF ) {
         $this->image = imagecreatefromgif($filename);
      } elseif( $this->image_type == IMAGETYPE_PNG ) {
         $this->image = imagecreatefrompng($filename);
      }
   }
   function save($filename, $image_type=IMAGETYPE_JPEG, $compression=75, $permissions=null) {
      if( $image_type == IMAGETYPE_JPEG ) {
         imagejpeg($this->image,$filename);
      } elseif( $image_type == IMAGETYPE_GIF ) {
         imagegif($this->image,$filename);         
      } elseif( $image_type == IMAGETYPE_PNG ) {
         imagepng($this->image,$filename);
      }   
      if( $permissions != null) {
         chmod($filename,$permissions);
      }
   }
   function output($image_type=IMAGETYPE_JPEG) {
      if( $image_type == IMAGETYPE_JPEG ) {
         imagejpeg($this->image);
      } elseif( $image_type == IMAGETYPE_GIF ) {
         imagegif($this->image);         
      } elseif( $image_type == IMAGETYPE_PNG ) {
         imagepng($this->image);
      }   
   }
   function getWidth() {
      return imagesx($this->image);
   }
   function getHeight() {
      return imagesy($this->image);
   }
   function resizeToHeight($height) {
      $ratio = $height / $this->getHeight();
      $width = $this->getWidth() * $ratio;
      $this->resize($width,$height);
   }
   function resizeToWidth($width) {
      $ratio = $width / $this->getWidth();
      $height = $this->getheight() * $ratio;
      $this->resize($width,$height);
   }
   function scale($scale) {
      $width = $this->getWidth() * $scale/100;
      $height = $this->getheight() * $scale/100; 
      $this->resize($width,$height);
   }
   function resize($width,$height) {
      $new_image = imagecreatetruecolor($width, $height);
      imagecopyresampled($new_image, $this->image, 0, 0, 0, 0, $width, $height, $this->getWidth(), $this->getHeight());
      $this->image = $new_image;   
   }      
}
?>