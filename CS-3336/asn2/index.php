<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" dir="ltr" xml:lang="en-US" lang="en-US">

<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>Matthew Stokes | CS3336 HW2</title>

<link rel="stylesheet" href="./css/hw2.css" type="text/css" media="screen" />
<link rel="stylesheet" href="./themes/smoothness/jquery-ui-1.9.0.custom.min.css" type="text/css" media="screen" />
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.8.2/jquery.min.js" type="text/javascript"></script>
<script src="./js/jQuery/jquery-ui-1.9.0.custom.min.js" type="text/javascript"></script>

<script type="text/javascript">

	//<![CDATA[
	$(document).ready(function() {

	/*
	Calendar Setup
	*/
	        $("#datepicker").datepicker({ 
            showOn: "button", //shows calendar when user clicks
            buttonImage: "./images/calendar.gif", //set image for button
            buttonImageOnly: true, //set that only button click on image will show calendar
			buttonText: "Please select a date",
			dateFormat: "dd-mm-yy" 
			}).datepicker("setDate", new Date()); //have date field populated

	/*
	Tooltip Setup
	*/
	$("#questionMark").tooltip();
	$("#datepicker").next().tooltip(); //next item that is populated should be the button image
	
	
	/*
	Bind to change and keyup
	*/
	$('#mainbody input').bind('change keyup', function(e){
	
	/*
	First 3 lines below are code from Alex Babanski Assignment Standards Part 3.13
	*/
		var target = e.target; // e.target grabs the node that triggered the event.
		var $target = $(target); // wraps the node in a jQuery object
		if (target.nodeName === 'INPUT') //must be either text field or checkbox
		{
			var date = $('#date_div input').val(); //get the value in the textbox
			var check = "";
			
			/*
			Get checked boxes and save to string
			*/
			$("#left input:checked").each(
				function()
				{
					if(check==="") //first number doesn't need a '-' delimiter
					{
						check+=$(this).attr("value"); 
					}
					else
					{
						check+= ("-") + $(this).attr("value");
					}						
				} 
			);

			/*
			Ajax Call
			*/
			$.get("./process.php", { date: date, check: check },
			function(data){
				$('#display_div').text(data); //show lucky number to user
			});
	
		} //end if
	}); //end bind
	
	/*
	Trigger to load off start
	*/
	$("#mainbody input").trigger('change');

	}); //end ready
//]]>
</script>

</head>

<body>
<div id="mainbody">

	<div id="left">
		Pick Numbers
		<?php
			for($i=1;$i<10;$i++) //populate checkboxes 1-9
			{
				echo '<br/>'.'<input type="checkbox" value="'. $i .'" ></input>'.$i; //need "" for w3c validation. ' ' cuts string allowing $i to return
																					 //a value and the . . appends text before and after. Another solution
																					 //would be to execute the value for $i within the string; my solution
																					 //breaks the echo into 5 different parts. 
			}
		?>
	</div>
	
	<div id="right">
	
		<div id="date_div">
			Pick a Date
			<input type="text"  id="datepicker"/>
		</div>
		
		<div id="display_div">
		</div>
		
		<div id="tooltip_div">
		This script calculates your lucky number
		<img src="./images/info.jpg" id="questionMark" title="Based on single-digit summation" height="16px" width="16px" alt=""></img>
		</div>
		
	</div>
	
</div>

</body>

</html>
