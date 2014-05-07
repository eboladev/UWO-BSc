<?php
/*
FunctionName:	mycount
Parameters:		$str - string containing strictly numbers
Purpose:		Takes numbers, breaks them up and adds them until a number between 1 and 9 is reached.
Returns:		number between 1-9
*/
function mycount ($str)
{
	$lucky = array_sum(str_split($str)); //splits the numbers in the string into an array, with one number in each spot, then sums the array
										 //If marks lost for this could instead use a for loop reading the character at position $i
										 //and adding it to a running total. My thought was this approach was less readable.

	if($lucky<10) //base case
	{
		return $lucky;
	}
	else
	{
		return mycount($lucky); //recursive loop
	}
}
$lucky=mycount(str_replace('-','', ($_GET["date"].$_GET["check"]))); //
$lucky = (empty($lucky)) ? 'Error: Invalid Number' : 'Your lucky number is '.$lucky;

echo $lucky;
?>