<?php 
require_once('./webroot.conf.php');
$page=process_script();
?>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" dir="ltr" xml:lang="en-US" lang="en-US">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>Matthew Stokes | CS3336 Final Project</title>
<link rel="stylesheet" href="./css/project.css" type="text/css" media="screen" />
<link rel="stylesheet" href="./themes/smoothness/jquery-ui-1.9.0.custom.min.css" type="text/css" media="screen" />
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.8.2/jquery.min.js" type="text/javascript"></script>
<script src="./js/jQuery/jquery-ui-1.9.0.custom.min.js" type="text/javascript"></script>

<script type="text/javascript">
//<![CDATA[
$(document).ready(function() {
	
	$("#error").hide(); //hide error message
	var xhr = null; //reset possibility to stop/abort ajax call
	
	//get current date and find a date value for a minimum age of 1 week old
	var oneWeekAgo = new Date();
	oneWeekAgo.setDate(oneWeekAgo.getDate() - 7);
	
	/* Calendar Setup Main Page */
	$("#date_of_birth").datepicker({ 
		changeMonth: true,
		changeYear: true,
		yearRange: "c-200Y",
		maxDate: oneWeekAgo,
		dateFormat: "mm-dd-yy" 
	});
		
	/* Calendar Setup Accounts Page */
	$('#dob_field').attr("disabled",true); 
	$("#dob_field").datepicker({ 
		changeMonth: true,
		changeYear: true,
		yearRange: "c-200Y:c+200Y",
		showOn: "button", //shows calendar when user clicks
		buttonImage: "./images/button.png", //set image for button
        buttonImageOnly: true, //set that only button click on image will show calendar
		buttonText: "Please select a date",
		dateFormat: "mm-dd-yy" 
	}).datepicker("setDate", new Date("mm-dd-yy"));

	/*
	Tooltip Setup
	*/
	$("#dob_field").next().tooltip(); //next item that is populated should be the button image
	
	/*
	Connecting forward and backwards options to +-10 days
	*/
	$('a#next').click(function () {
    var $picker = $("#dob_field");
    var date=new Date($picker.datepicker('getDate'));
    date.setDate(date.getDate()+10);
    $picker.datepicker('setDate', date);
	$("#body input").trigger('change'); //trigger change event
	});
	
	$('a#prev').click(function () {
    var $picker = $("#dob_field");
    var date=new Date($picker.datepicker('getDate'));
    date.setDate(date.getDate()-10);
    $picker.datepicker('setDate', date);
	$("#body input").trigger('change'); //trigger change event
	});
	
	
	/*
	Bind to change and keyup
	*/	
	$('#body input').bind('change', function(e){

		$("#error").hide(); //hide error message
		$('#loading_gif').hide(); //hide gif
		
		/*
		First 3 lines below are code from Alex Babanski Assignment Standards Part 3.13
		*/
		var target = e.target; // e.target grabs the node that triggered the event.
		var $target = $(target); // wraps the node in a jQuery object
		if (target.nodeName === 'INPUT') //must be either text field or checkbox
		{
			var $date_entered = $('#dob_field').val(); //get the entered date
			var $birthday = $('#birthday').text();
			
			/*
			Running process check
			*/
			if(xhr !=null) //if currently getting JSON object  (back to back functions)
			{			
			xhr.abort();
			}
			
			//calculate the difference between the dates
			var $date_format_date_entered = new Date($date_entered);
			var $date_format_birthday = new Date($birthday);			
			$diff = $date_format_date_entered-$date_format_birthday;
			
			/*
			Ajax/JSON Call
			*/
			$('#loading_gif').show(); //show gif
			xhr = $.getJSON("./includes/process.php",{ date: $date_entered, birthday: $birthday, diff: $diff }, function(json){
			xhr = null; //reset to null
			$('#chart').show();
			$('#loading_gif').hide(); //hide gif
			
			if(json.error!==true)
			{				
				// fill in legend
				$('#physical').text(json.physical);
				$('#emotional').text(json.emotional);
				$('#intellectual').text(json.intellectual);
				$('#chart').attr("src", json.chart);
							
			}
			else //error loading
			{
				$("#error").show();
				$('#chart').hide();
			}
			}); //End json
	
		} //end if
	}); //end bind
	
	$("#body input").trigger('change'); //trigger event to load for the first time
	
}); //end ready
//]]>
</script>

</head>

<body>
	<?php echo $page; ?>
</body>
</html>
