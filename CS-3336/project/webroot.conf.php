<?php
/* Same in frm assignment 4 (why reinvent the wheel) */

set_time_limit(0); 
error_reporting(E_ALL);
ini_set('session.use_cookies',1);
ini_set('session.use_only_cookies',1);
ini_set('session.use_trans_sid',0);
ini_set('session.gc_maxlifetime',7200); // 2 hour session!
ini_set('default_charset', 'UTF-8');

mb_internal_encoding('UTF-8');
mb_http_output('UTF-8');

@set_magic_quotes_runtime(0);

// Session should start here!
session_start();

// So templates can be included
define ('TMPL_DIR', './templates');

//Secure_Key == 'Matthew Stokes' through MD5 hash
define ('SECURE_KEY', '1a6e487833e2d451d1df83ef0584c37a'); //Used in encrypt/decrypt functions!
define('MYSQL_SERVER', 'localhost:3306');
define('MYSQL_USER', 'mstokes55798_db');
define('MYSQL_DB', 'mstokes55798_db');
define('MYSQL_PASSWORD', 'qB31dG7u');

//connect to the database
 $GLOBALS['DB']= @mysql_connect(MYSQL_SERVER, MYSQL_USER, MYSQL_PASSWORD) or die ('Cannot connect to the MySQL server');
 mysql_select_db(MYSQL_DB, $GLOBALS['DB']) or die ('Cannot select MySQL database');

require_once('./includes/functions.inc.php');
?>