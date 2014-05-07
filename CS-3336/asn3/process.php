<?php
require_once('./includes/config.inc.php');
require_once('./includes/functions.inc.php');

sleep(2); // Don't touch this!

/*
Lecture slide deck 8 Alex Babanski:
Use filter_var() function (still doesn't cover all RFC specs though!!)
*/
if(!filter_var($_GET["email"], FILTER_VALIDATE_EMAIL)) //not valid
{
  $arr = array('error' => "Invalid Email address!");
  echo json_encode($arr);
}
  
else //email valid
{	
	if(!file_exists(FACULTY_FILE)) //if file does not exist create file
	{
	create_file();	
	}
	
    $arr = get_info($_GET["email"]);
	
	if($arr ==null) //email not found
	{
		$arr = array('error' => "E-mail not found!");
		echo json_encode($arr);
	}
	else //email found
	{
		echo json_encode($arr);
	}
}
?>