<?php

/*
FunctionName:	get_http_request
Parameters:		$url - domain to get information from
Purpose:		saves the source of a given webpage
Returns:		returns the source of a webpage as a string
*/
function get_http_request($url,&$xml_post=null)
{
	if (!function_exists('curl_init'))
	{
		die('Curl library is not installed!');
	}

	$CURLOPT_HTTPHEADER_ARRAY= array();
	$CURLOPT_HTTPHEADER_ARRAY[]= 'Connection: close';
	$httpcode = '';
	
	$ch = @curl_init(); //initialize a new curl resource
	@curl_setopt($ch, CURLOPT_URL, $url); //set the url to fetch
	@curl_setopt($ch, CURLOPT_HEADER, false); // don't give me the headers just the content
	@curl_setopt($ch, CURLOPT_USERAGENT, 'Mozilla/5.0 (Windows; U; Windows NT 5.1; en-US; rv:1.7.5) Gecko/20041107 Firefox/1.0');
	@curl_setopt($ch, CURLOPT_RETURNTRANSFER, true); //return the value instead of printing the response to browser
	@curl_setopt($ch, CURLOPT_HTTPHEADER, $CURLOPT_HTTPHEADER_ARRAY);
	
	/*
	Additional Setting from Fridays class used by Alex Babanski
	*/
	@curl_setopt($ch, CURLOPT_FOLLOWLOCATION, true);
	@curl_setopt($ch, CURLOPT_CONNECTTIMEOUT, 30); //20 second timeout
	@curl_setopt($ch, CURLOPT_SSL_VERIFYPEER, false);
	@curl_setopt($ch, CURLOPT_SSL_VERIFYHOST, false);
	@curl_setopt($ch, CURLOPT_FORBID_REUSE, true);
	@curl_setopt($ch, CURLOPT_FRESH_CONNECT, true);
	if(!empty($xml_post))
	{
	@curl_setopt($ch, CURLOPT_POST, true);
	@curl_setopt($ch, CURLOPT_POSTFIELDS, http_build_query($xml_post));
	}
	
	$content = @curl_exec($ch);
	$httpcode = curl_getinfo($ch, CURLINFO_HTTP_CODE);
	@curl_close($ch);
	
	return ($httpcode==200) ? $content : false; //if successful return $content else false
	}

/*
FunctionName:	parse
Parameters:		$site string to website to parse information from
Purpose:		parse information from the webpage and isolate email, name and study for every proff 
Returns:		returns an organized array as such: array(array(email,name,study))
*/
function parse($site)
	{
	/*
	Get content from the $site
	*/
	$content = get_http_request($site); //stores all content into string

	/*
	Parse file contents
	*/
	$arr = array();
	$arr2 = array();
	$count = 0;

	preg_match("'<tbody(.*?)</tbody>'si", $content, $arr); //isolates between tbody
	preg_match_all("'<td(.*?)</td>'si",$arr[0],$arr2); //extract each td line in an array inside $arr2

	for($i=0;$i<count($arr2[0]);$i++)
	{
		if($count==3) //reset count
		{
			$count=0;
		}

		$arr2[0][$i] = preg_replace("'<(.*?)>'si",'',$arr2[0][$i]); //remove all tag information/properties
		$arr2[0][$i] = preg_replace("'\*'si",'@uwo.ca',$arr2[0][$i]); //adding @uwo.ca to emails
		$arr2[0][$i] = preg_replace("'\((.*?)\)'si",'',$arr2[0][$i]); //remove special designations
		$arr2[0][$i] = preg_replace("'\&\#160;'si",'',$arr2[0][$i]); //remove &# specialness
		$arr2[0][$i] = trim($arr2[0][$i]); //remove ending whitespaces

		if($count==0) //dealing with a name
		{
			$tmp = preg_split('/ Dr\./',$arr2[0][$i]); //switch last and first name
			$arr2[0][$i] = "Dr. " . $tmp[1] . " " . $tmp[0]; //add to spot in array
			$arr2[0][$i] = preg_replace("','si",'',$arr2[0][$i]); //remove ,
			$arr2[0][$i] = preg_replace('/\s{1,}/',' ', $arr2[0][$i]); //fixing occasional double spacing issue
			$arr2[0][$i] = trim($arr2[0][$i]); //remove ending whitespaces
		}
	$count++;
	}

	/*
	Create an array within array for fputcsv
	*/
	$outputArray = array();
	$i=0;
	$j=0;
	while($i<count($arr2[0]))
	{
		$outputArray[$j] = array($arr2[0][$i+2],$arr2[0][$i],$arr2[0][$i+1]); // email, name, study
		$i = $i +3;
		$j++; 
	}
	return $outputArray;
} //end function


/*
FunctionName:	create_file()
Parameters:		none
Purpose:		gets the information from a page, parses it into an array as such: array(array(email,name,study)) and writes to a csv file
Returns:		none
*/
function create_file()
{
	$outputArray = parse(FACULTY_WEB);

	/*
	Write out to .csv file
	*/
	$fp = fopen(FACULTY_FILE, 'w'); //open file
	if(@flock($fp, LOCK_EX))
	{
		foreach ($outputArray as $fields)
		{
			fputcsv($fp, $fields);//write array line to file
		}
	}
	flock($fp, LOCK_UN);
	fclose($fp); //close file
}//end create_file


/*
FunctionName:	get_info
Parameters:		$email - email address to look up
Purpose:		Searches a .csv file for the name and study of a professor based on an email provided. 
Returns:		null if email is not found, null if failed to open .csv file. else an array with the appropriate name, study, and email
*/
function get_info($email)
{
	$arr = array(); //initailize array
	
    $handle = @fopen(FACULTY_FILE, "r"); //create handle
	
    if ($handle) //while handle is open
	{
        while (($line = fgetcsv($handle, 4096)) !== false) //loop line by line storing each column into $line array
		{
			if(strcmp($line[0],$email)==0) //if email match
			{
				$arr['email'] = $line[0];
				$arr['name'] = $line[1];
				$arr['area'] = $line[2];
				fclose($handle); //close handle
				return $arr;
			}
        }
		return null; //if email is not found
    }
	return null; //if failed to open .csv file (could also be substituted to return an error message it was just never specified)
}
?>