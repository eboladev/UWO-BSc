<?php

function process_script()
{	
	$action=strtolower(getRequest('action',true,'get'));
	$accepted_actions='login,account,logout';	
	$accepted_actions_array = explode(",", $accepted_actions);
	
	//ensuring we cant enter manually something unexpected
	if(!(in_array($action, $accepted_actions_array)))
	{
		$action = '';
	}
	
	//limit only to login!
	if (empty($action))
	{
		$action='login';
	}
	
	//don't allow access if not in session
	if(get_SESSION('id') === NULL && ($action === 'account')) //no session started
	{	
		$action = 'login';
	}
	
	//if session already present jump right into session
	if(get_SESSION('id') !== NULL && ($action === 'login')) // session started
	{	
		$action = 'account';
	}
		
	//return template
	return get_template($action, $action()); 
}


function &logout()
{
	//end session
	session_destroy(); 
	
	 //goto login page
	set_header('login');
	
	exit();
}

function set_header($action=null)
{
	$url = (empty($action)) ? urlhost() : urlhost().'?action='.$action;
	header('Location: '. $url );
	exit();
}

function &account()
{
	$HTML=array();	
	return $HTML;
}


function &login()
{	
	//reset
	$HTML=array();
	$HTML['email']='';
	$HTML['date_of_birth']='';	
	$HTML['login_error']='';

	if (getRequest('submitted',true,'post') !=='yes') {return $HTML;} //first time through

	//getting values from form
	foreach($HTML as $key=> &$value)
	{
		$value=getRequest($key, true, 'post');
	}

	//email check
	if (empty($HTML['email']))
	{
		$HTML['login_error'] = 'Email cannot be empty';
	}
	elseif (filter_var($HTML['email'],FILTER_VALIDATE_EMAIL) ===false)
	{
		$HTML['login_error'] ='Invalid Email Address';
	}

	//date of birth checks
	elseif (empty($HTML['date_of_birth']))
	{ 
		$HTML['login_error'] ='Date of birth cannot be empty';
	}

	elseif (!empty($HTML['date_of_birth']))
	{ 
		//check for a valid date string XX-YY-ZZZZ
		if(preg_match('/\d{2}-\d{2}-\d{1,}/',$HTML['date_of_birth']))
		{

			//dob[0] == month
			//dob[1] == day
			//dob[2] == year
			$dob = explode("-",$HTML['date_of_birth']);

			$today = date("m-d-Y"); //get current date
				
			$newdate = strtotime("-1 week");
			$newdate = date('m-d-Y', $newdate);

			$newdate = str_replace("-", "/", $newdate); // has to be in the format mm/dd/yy to use strtotime
			$HTML['date_of_birth'] = str_replace("-", "/", $HTML['date_of_birth']); // has to be in the format mm/dd/yy to use strtotime
			
			//FAQ section said to have the upper limit at 1 week old so parents can check their newborns
			if(strtotime($newdate)<strtotime($HTML['date_of_birth'])) //FIX
			{
				$HTML['login_error'] ='Cannot be less than 1 week old';
			}

			//proper date
			if(!(checkdate($dob[0], $dob[1], $dob[2])))
			{
				$HTML['login_error'] ='Date of birth never existed';
			}

		}
		else
		{
			$HTML['login_error'] ='Date of birth is not valid';
		}
		
		if($HTML['login_error'] == NULL)
		{
			//store in database
			$date_of_birth = $dob[2] . "-" . $dob[0] . "-" . $dob[1];
			$today = getdate();
			$current_datetime = $today['year']."-".$today['mon']."-".$today['mday']." ".$today['hours'].":".$today['minutes'].":".$today['seconds'];  //"2010-11-30 23:59:59";

			$query = sprintf("INSERT INTO project (email, birthday, userIP, loginDate) VALUES('%s','%s','%s','%s')",
					mysql_real_escape_string($HTML['email']),
					mysql_real_escape_string($date_of_birth),
					mysql_real_escape_string(util_getenv('REMOTE_ADDR')),
					mysql_real_escape_string($current_datetime));			
			mysql_query($query);		

			//set session data		
			set_SESSION('id', get_id($HTML['email'], $date_of_birth, util_getenv('REMOTE_ADDR'), $current_datetime));		
			set_header('account'); //goto account
			exit();
		}
	}
	return $HTML;
}

/*
	Increased security...for whatever reason to secure a DOB
*/
function get_id($email, $birthday, $userIP, $loginDate)
{
	if(empty($GLOBALS['DB'])) {die ('Database Link is not set'); }

	 $query = sprintf("SELECT * FROM project WHERE email='%s' AND birthday='%s' AND userIP='%s' AND loginDate='%s'",
							mysql_real_escape_string($email),
							mysql_real_escape_string($birthday),
							mysql_real_escape_string($userIP),
							mysql_real_escape_string($loginDate));
		
	$result = mysql_query($query);
	 
	if(mysql_num_rows($result) === 0) // no record found (database failure)
	{
		return 0;
	}
	else
	{
		$extract = mysql_fetch_assoc($result);
		return $extract['userID'];
	}
}

/*
	Increased security...for whatever reason to secure a DOB
*/
function get_birthday_from_id($id)
{
	if(empty($GLOBALS['DB'])) {die ('Database Link is not set'); }

	 $query = sprintf("SELECT * FROM project WHERE userID='%s'",
							mysql_real_escape_string($id));
		
	$result = mysql_query($query);
	 
	if(mysql_num_rows($result) === 0) // no record found (database failure)
	{
		return 0;
	}
	else
	{
		$extract = mysql_fetch_assoc($result);
		
		//birthday[0] == year
		//birthday[1] == month
		//birthday[2] == day
		$birthday = explode("-", $extract['birthday']);
		
		return ($birthday[1]."-".$birthday[2]."-".$birthday[0]);
	}
}

function get_SESSION($key)
{
	return ( !isset($_SESSION[$key]) ) ? NULL : decrypt($_SESSION[$key]);
}


function set_SESSION($key, $value='')
{
	if (!empty($key))
	{
		$_SESSION[$key]=encrypt($value);
		return true;
	}
	return false;
}


function util_getenv($key)
{
	return ( isset($_SERVER[$key])? $_SERVER[$key]:(isset($_ENV[$key])? $_ENV[$key]:getenv($key)) );
}


function host ($protocol=null)
{
	$host = util_getenv('SERVER_NAME');
	if (empty($host)) {	$host = util_getenv('HTTP_HOST'); }
	return (!empty($protocol)) ? $protocol.'//'.$host  : 'http://'.$host;
}


function urlhost ($protocol=null)
{
	return host($protocol).$_SERVER['SCRIPT_NAME'];
}


function get_template($file, &$HTML=null)
{
	//loop through array and sanatize elements 
	foreach($HTML as $key=> &$value)
	{
			if($value != '')
			$value=utf8HTML($value);
	}
	
	$content='';
	ob_start();
	if (@include(TMPL_DIR . '/' .$file .'.tmpl.php')):$content=ob_get_contents();
	endif;
	ob_end_clean();
	return $content;
}

	
function getRequest($str='', $removespace=false, $method=null)
{
	if (empty($str)) {return '';}
  	switch ($method)
	{
		case 'get':
			$data=(isset($_GET[$str])) ? $_GET[$str] : '' ;
			break;
		case 'post':
			$data=(isset($_POST[$str])) ? $_POST[$str] : '';
			break;				
		default:
			$data=(isset($_REQUEST[$str])) ? $_REQUEST[$str] : '';
	}		
	if (empty($data)) {return $data;}				
	if (get_magic_quotes_gpc())
	{
		$data= (is_array($data)) ? array_map('stripslashes',$data) : stripslashes($data);	
	}
	if (!empty($removespace))
	{
		$data=(is_array($data)) ? array_map('removeSpacing',$data) : removeSpacing($data);
	}
	return $data;
}

	
function removeSpacing($str)
{
	return trim(preg_replace('/\s\s+/', ' ', $str));
}
	
	
function utf8HTML ($str='')
{
  	return htmlentities($str, ENT_QUOTES, 'UTF-8', false); 
}


function encrypt($text, $SALT=null)
{

	if($SALT === NULL)
	{
		$SALT = SECURE_KEY;
	}

	$encrypted = base64_encode(mcrypt_encrypt(MCRYPT_RIJNDAEL_256, $SALT, $text, MCRYPT_MODE_CBC, $SALT));
	return $encrypted;
} 
    
function decrypt($text, $SALT=null)
{ 
	if($SALT === NULL)
	{
		$SALT = SECURE_KEY;
	}
	
	$decrypted = rtrim(mcrypt_decrypt(MCRYPT_RIJNDAEL_256, $SALT, base64_decode($text), MCRYPT_MODE_CBC, $SALT), "\0");
	return $decrypted;
}  


//0123456789
//mm-dd-yyyy
function get_dates_biorhythms($date, $birthday)
{
	// T Value - 23 physical cycle
	$t_physical = 23;
	// T Value - 28 emotional cycle
	$t_emotional = 28;
	// T Value - 33 intellectual cycle
	$t_intellectual = 33;
	
	//date has to be in yyyy-mm-dd format
	$date_converted = substr($date, 6, 4) . "-" . substr($date, 0, 2) . "-" . substr($date, 3, 2) ;
	$birthday_converted = substr($birthday, 6, 4) . "-" . substr($birthday, 0, 2) . "-" . substr($birthday, 3, 2) ;
	
	//get date difference
	$interval = date_diff(date_create($birthday_converted), date_create($date_converted));
	$D = $interval->format('%a');
	
	//get starting values
	$arr = array();
	$arr['physical'] = round(sin(2*pi()*$D/$t_physical)*100);
	$arr['emotional'] = round(sin(2*pi()*$D/$t_emotional)*100);
	$arr['intellectual'] = round(sin(2*pi()*$D/$t_intellectual)*100);
	return $arr;
}


function generate_string($date)
{
	$str="";

	for($i=-10;$i<=10;$i=$i+2)
	{
		$date_2 = date_create($date);
		$add = date_interval_create_from_date_string("{$i} day");
		date_add($date_2,$add);
		$str .= date_format($date_2, 'M d');
		if($i!=10)
		{
			$str .= '|';
		}
	}
	return $str;
}


function save_chart($saveto, $url)
{

	if (!function_exists('curl_init'))
	{
		die('Curl library is not installed!');
	}
	
	$ch = curl_init();
	curl_setopt($ch, CURLOPT_URL, $url); //set the url to fetch
	curl_setopt($ch, CURLOPT_USERAGENT, 'Mozilla/5.0 (Windows; U; Windows NT 5.1; en-US; rv:1.7.5) Gecko/20041107 Firefox/1.0');
    curl_setopt($ch, CURLOPT_RETURNTRANSFER, 1);
    curl_setopt($ch, CURLOPT_BINARYTRANSFER,1);
	curl_setopt($ch, CURLOPT_HEADER, false); // don't give me the headers just the content

    $raw=curl_exec($ch);
	$httpcode = curl_getinfo($ch, CURLINFO_HTTP_CODE); //if curl executed
    curl_close ($ch);

    $fp = fopen($saveto,'x');
	if(@flock($fp, LOCK_EX))
	{
		fwrite($fp, $raw);
	}
	flock($fp, LOCK_UN);
	fclose($fp);
	
}

?>