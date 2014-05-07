<?php
function process_script()
{	
	$action=strtolower(getRequest('action',true,'get'));
	$accepted_actions='login,signup,account,edit,logout,cancel';	
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
	
	//goto account if already in session
	if(get_SESSION('id') !== NULL && ($action === 'login' || $action === 'signup')) //no session started
	{	
		$action = 'account';
	}
	
	//don't allow access if not in session
	if(get_SESSION('id') === NULL && ($action === 'account' || $action === 'edit' || $action === 'logout' || $action === 'cancel')) //no session started
	{	
		$action = 'login';
	}
	
	return get_template($action, $action()); 
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


function &cancel()
{
	//get id
	$id = get_SESSION('id');

	//remove user based on email
	$query = sprintf("DELETE FROM users WHERE id = '%d'",
            mysql_real_escape_string($id));			
			mysql_query($query);

	//end session			
	session_destroy();

	set_header('login'); //goto login page
	exit();
}


function &logout()
{
	//end session
	session_destroy(); 
	
	set_header('login'); //goto login page
	exit();
}


function &login()
{	
	//reset
	$HTML=array();
	$HTML['email']='';
	$HTML['password']='';	
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
		$HTML['login_error'] = 'Email Cannot be empty';
		$HTML['password']='';
	}
	elseif (filter_var($HTML['email'],FILTER_VALIDATE_EMAIL) ===false)
	{
		$HTML['login_error'] ='Invalid Email Address';
		$HTML['password']='';
	}
	
	//password check
	elseif (empty($HTML['password']))
	{ 
		$HTML['login_error'] ='Password cannot be empty'; //Security measure!
	}

	else //valid password and email
	{
		//check if user is in database
		$id = validate_record($HTML['email'],md5($HTML['password']));
		
		if($id<1)
		{
			$HTML['login_error'] ='Account not found or invalid Email/Password';
			$HTML['password']='';
		}
		else
		{
			set_SESSION('id', $id);		
			set_header('account'); //goto account
			exit();
		}
	}
	return $HTML;
}


function &edit()
 {
 	// get all the info based on session['id'] from the database
	$id = get_SESSION('id');
	$query = "SELECT * FROM users WHERE id = '{$id}'";
	$results = mysql_query($query);
	$database = mysql_fetch_assoc($results);
	
	//fill in HTML array
	$HTML=array();
 	$HTML['email']= $database['email'];
	$HTML['city'] = $database['city'];	
	$HTML['password'] = '';
	$HTML['confirm_password'] = '';
	$HTML['password_encrypted'] = '';
	$HTML['country_options_escape'] = "<option value=> Please Select </option>" ;	
	$countryQuery = "SELECT * FROM countries WHERE active = \"yes\"";
	$countryResults = mysql_query($countryQuery);
	while($row = mysql_fetch_assoc($countryResults))
	{
		if($row['countryID'] === $database['country']) //select country of user to show
		{
			$HTML['country_options_escape'] .= "<option selected=\"selected\" value= {$row['countryID']} > {$row['country']} </option>" ;
		}
		else
		{
			$HTML['country_options_escape'] .= "<option value= {$row['countryID']} > {$row['country']} </option>" ;
		}
	}
	
	if (getRequest('submitted',true,'post') !=='yes') {return $HTML;} //first time through
	
	//get values for $HTML array elements
	foreach($HTML as $key=> &$value)
	{
		if($key !== 'country_options_escape') //populate countries each time don't take values from form
		{
		$value=getRequest($key, true, 'post');
		}
	}
	
	//email checks
	if (empty($HTML['email']))
	{
		$HTML['email_error'] = 'Email Cannot be empty';
	}
	elseif (filter_var($HTML['email'],FILTER_VALIDATE_EMAIL) ===false)
	{
		$HTML['email_error'] ='Invalid Email Address';
	}

	//password checks
	if (!(preg_match('/((?=.*\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%]).{6,20})/', $HTML['password'])) && ($HTML['password'] !== 'do_not_change' && $HTML['password_encrypted'] !== '') && $HTML['password'] != '') //from lecture notes
	{
		$HTML['confirm_password_error'] ='Password has to be 6-20 chars and more secure!'; //Security measure!
		$HTML['password'] = ""; //reset
		$HTML['confirm_password'] = ""; //reset
	}
	elseif ($HTML['password'] !== $HTML['confirm_password'])
	{
	$HTML['confirm_password_error'] ='Password and confirm password do not match!'; 
	$HTML['password'] = ""; //reset
	$HTML['confirm_password'] = ""; //reset
	}
	elseif($HTML['password'] !== 'do_not_change' && $HTML['password'] != '')
	{
		$HTML['password_encrypted'] = md5($HTML['password']);
		$HTML['password'] = 'do_not_change';
		$HTML['confirm_password'] = 'do_not_change';
	}
	
	//city checks
	$HTML['city'] = removeSpacing($HTML['city']);
	if (empty($HTML['city']))
	{ 
		$HTML['city_error'] ='City name cannot be empty!';
		$HTML['city'] = utf8HTML($HTML['city']);
	}
	elseif ($HTML['city'] !== utf8HTML($HTML['city']))
	{
		$HTML['city_error'] ='Special Symbols are not allowed!';
		$HTML['city'] = utf8HTML($HTML['city']);
	}
	
	//country checks	
	$HTML['countryID'] = getRequest('countryID', true, 'post');	
	if (empty($HTML['countryID']))
	{ 
		$HTML['countryID_error'] ='Please select the country!';
	}
	else
	{
		//check country ID against all options in database (double check against scripts to ensure storing proper data)
		$countryQuery = sprintf("SELECT * FROM countries WHERE countryID='%s' AND active = \"yes\"",
										mysql_real_escape_string($HTML['countryID']));
		$countryResults = mysql_query($countryQuery);		
		if($rows = mysql_num_rows($countryResults) === 0) // no record found
		{
			$HTML['countryID_error'] ='Please select the country!';
		}
	}
	
	//if no errors
	if(!(isset($HTML['countryID_error']) || isset($HTML['city_error']) || isset($HTML['confirm_password_error']) || isset($HTML['email_error'])))
	{
		//check if email is available
		if(validate_record($HTML['email']) > 1 && $HTML['email'] !== $database['email']) //email already in database
		{
		$HTML['signup_error'] = "email address already exists";
		}
		
		else //all good to change database
		{
			//setting new country
			$query = sprintf("UPDATE users SET country='%s' WHERE id='%d'",
								mysql_real_escape_string($HTML['countryID']),
								mysql_real_escape_string($id));			
			mysql_query($query);
		
			//setting new email
			$query = sprintf("UPDATE users SET city='%s' WHERE id='%d'",
								mysql_real_escape_string($HTML['city']),
								mysql_real_escape_string($id));			
			mysql_query($query);
			
			//setting password
			if($HTML['password'] !== '') //if password isn't blank
			{
				$query = sprintf("UPDATE users SET password='%s' WHERE id='%d'",
									mysql_real_escape_string($HTML['password_encrypted']),
									mysql_real_escape_string($id));			
				mysql_query($query);
			}
		
			//setting email
			$query = sprintf("UPDATE users SET email='%s' WHERE id='%d'",
								mysql_real_escape_string($HTML['email']),
								mysql_real_escape_string($id));			
			mysql_query($query);
		
			//set session data
			set_SESSION('id', $id);

			set_header('account'); //goto account
			exit();
		}
	}	
	return $HTML;
} 

function &signup($edit=false){

	//reset HTML array
	$HTML=array();
	$HTML['password']='';
	$HTML['confirm_password']='';
	$HTML['email'] = '';
	$HTML['city'] = '';
	$HTML['password_encrypted'] = '';
	$HTML['countryID'] = '';
	
	//fill in countries
	$HTML['country_options_escape'] = "<option value=> Please Select </option>" ;
	$countryQuery = "SELECT * FROM countries WHERE active = \"yes\"";
	$countryResults = mysql_query($countryQuery, $GLOBALS['DB']);	
	$HTML['countryID'] = getRequest('countryID', true, 'post');		
	
	if (getRequest('submitted',true,'post') !=='yes') //first time through
	{
		while($row = mysql_fetch_assoc($countryResults))
		{
			$HTML['country_options_escape'] .= "<option value= {$row['countryID']} > {$row['country']} </option>" ;
		}		
	}
	else //selection in spin box
	{
		$HTML['countryID'] = getRequest('countryID', true, 'post');
		while($row = mysql_fetch_assoc($countryResults))
		{
			if($row['countryID'] === $HTML['countryID']) //select country selected by user to show
				{
					$HTML['country_options_escape'] .= "<option selected=\"selected\" value= {$row['countryID']} > {$row['country']} </option>" ;
				}
			else
			{
				$HTML['country_options_escape'] .= "<option value= {$row['countryID']} > {$row['country']} </option>" ;
			}
		}			
	}
	
	if (getRequest('submitted',true,'post') !=='yes') {return $HTML;} //first time through
	
	//get values for $HTML array elements
	foreach($HTML as $key=> &$value)
	{
		if($key !== 'country_options_escape') //populate countries each time
		{
			$value=getRequest($key, true, 'post');
		}
	}
	
	//email checks
	if (empty($HTML['email']))
	{
		$HTML['email_error'] = 'Email Cannot be empty';
	}
	elseif (filter_var($HTML['email'],FILTER_VALIDATE_EMAIL) ===false)
	{
		$HTML['email_error'] ='Invalid Email Address';
	}
	
	//password checks	
	if (empty($HTML['password']))
	{ 
		$HTML['confirm_password_error'] ='Password cannot be empty'; //Security measure!
		$HTML['password'] = ""; //reset
		$HTML['confirm_password'] = ""; //reset
	}	
	elseif (!(preg_match('/((?=.*\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%]).{6,20})/', $HTML['password'])) && $HTML['password'] !== 'do_not_change' && $HTML['password_encrypted'] !== '') //from lecture notes
	{
		$HTML['confirm_password_error'] ='Password has to be 6-20 chars and more secure!'; //Security measure!
		$HTML['password'] = ""; //reset
		$HTML['confirm_password'] = ""; //reset
	}
	elseif ($HTML['password'] !== $HTML['confirm_password'])
	{
	$HTML['confirm_password_error'] ='Password and confirm password do not match!'; 
	$HTML['password'] = ""; //reset
	$HTML['confirm_password'] = ""; //reset
	}	
	elseif($HTML['password'] !== 'do_not_change')
	{
		$HTML['password_encrypted'] = md5($HTML['password']);
		$HTML['password'] = 'do_not_change';
		$HTML['confirm_password'] = 'do_not_change';
	}

	//city checks
	$HTML['city'] = removeSpacing($HTML['city']);
	if (empty($HTML['city']))
	{ 
		$HTML['city_error'] ='City name cannot be empty!';
		$HTML['city'] = utf8HTML($HTML['city']);
	}
	elseif ($HTML['city'] !== utf8HTML($HTML['city']))
	{
		$HTML['city_error'] ='Special Symbols are not allowed!';
		$HTML['city'] = utf8HTML($HTML['city']);
	}
	
	//country checks
	if (empty($HTML['countryID']))
	{ 
		$HTML['countryID_error'] ='Please select the country!';
	}
	else
	{
		//check country ID against all options in database (double check against scripts to ensure storing proper data)
		$countryQuery = sprintf("SELECT * FROM countries WHERE countryID='%s' AND active = \"yes\"",
										mysql_real_escape_string($HTML['countryID']));
		$countryResults = mysql_query($countryQuery);		
		if($rows = mysql_num_rows($countryResults) === 0) // no record found
		{
			$HTML['countryID_error'] ='Please select the country!';
		}
	}
	
	//if no errors
	if(!(isset($HTML['countryID_error']) || isset($HTML['city_error']) || isset($HTML['confirm_password_error']) || isset($HTML['email_error'])))
	{
	
		//check if in database
		$id = validate_record($HTML['email']);
		if($id > 1) //email already in database
		{
			$HTML['signup_error'] = "email address already exists";
		}
		else //available!
		{	
			//add to database
			$query = sprintf("INSERT INTO users (email, password, city, country) VALUES('%s','%s','%s','%s')",
										mysql_real_escape_string($HTML['email']),
										mysql_real_escape_string($HTML['password_encrypted']),
										mysql_real_escape_string($HTML['city']),
										mysql_real_escape_string($HTML['countryID']));			
			mysql_query($query);
		
			//set session data
			$query = sprintf("SELECT * FROM users WHERE email = '%s'",
							mysql_real_escape_string($HTML['email']));
			$results = mysql_query($query);
			$database = mysql_fetch_assoc($results);
			set_SESSION('id', $database['id']);

			set_header('account'); //If no errors -> go to account
			exit();
		}
	}
	return $HTML;
}


function validate_record($email, $password=NULL)
{
	if(empty($GLOBALS['DB'])) {die ('Database Link is not set'); }
	
	if($password === NULL)
	{
		$query = sprintf("SELECT * FROM users WHERE email='%s'",
							mysql_real_escape_string($email));
	}
	else
	{
	 $query = sprintf("SELECT * FROM users WHERE email='%s' AND password='%s'",
							mysql_real_escape_string($email),
							mysql_real_escape_string($password));
	}
	
	$result = mysql_query($query);
	 
	if(mysql_num_rows($result) === 0) // no record found
	{
		return 0;
	}
	else
	{
	$extract = mysql_fetch_assoc($result);
	// user currently in database	
	 return $extract['id'];
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


function util_getenv ($key)
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
	foreach($HTML as $key=> &$value) // We don't display password
	{
		if($key !== 'country_options_escape') //ignore country 
		{
			if($value != '')
			$value=utf8HTML($value);
		}
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

	// my encryption function
	$encrypted = mcrypt_encrypt(MCRYPT_RIJNDAEL_256, $SALT, $text, MCRYPT_MODE_CBC, $SALT);
	return $encrypted;
} 
    
function decrypt($text, $SALT=null)
{ 
	if($SALT === NULL)
	{
	$SALT = SECURE_KEY;
	}
	
	// my decryption function
	$decrypted = rtrim(mcrypt_decrypt(MCRYPT_RIJNDAEL_256, $SALT, $text, MCRYPT_MODE_CBC, $SALT), "\0");
	return $decrypted;
}  
?>