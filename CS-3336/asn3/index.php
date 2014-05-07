<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" dir="ltr" xml:lang="en-US" lang="en-US">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>Matthew Stokes | CS3336 HW3</title>

<link rel="stylesheet" href="./css/hw3.css" type="text/css" media="screen" />
<script src="//ajax.googleapis.com/ajax/libs/jquery/1.8.2/jquery.min.js" type="text/javascript"></script>

<script type="text/javascript">
	$(document).ready(function() {

	var xhr = null;
	$('#text_region').hide();
	
	/*
	Bind to click
	*/
	$('#left').bind('click', function(e){
	
	/*
	Hiding all responses/loading when editing the text field or button is again clicked
	*/
	$("#error").text("");
	$("#name").text("");
	$("#area").text("");
	$("#email").text("");
	$('#text_region').hide();
	
	/*
	First 3 lines below are code from Alex Babanski Assignment Standards Part 3.13
	*/
		var target = e.target; // e.target grabs the node that triggered the event.
		var $target = $(target); // wraps the node in a jQuery object
		if (target.nodeName === 'A') //must be either text field or checkbox
		{
			$("#gif").addClass("active"); //bring loading image in view
			var usertext = $('#left input').val(); //get the value in the textbox
			
			/*
			Sanatize
			*/
			var usertext = usertext.replace(/[^\w.@]/gi, '');
			var usertext = usertext.replace(/ /g, '');
						

			/*
			Running process check
			*/
			if(xhr !=null) //if currently getting JSON object  (back to back finds)
			{			
			xhr.abort();
			}
			
			/*
			Ajax/JSON Call
			*/
			xhr = $.getJSON("./process.php",{ email: usertext }, function(json){
			
			xhr = null; //reset to null
			$("#gif").removeClass("active"); //hide image
			
			if(json.error!=null)
			{
				$("#error").text(json.error);
			}
			
			else
			{
				$('#text_region').show();
				$("#name").text(json.name);
				$("#area").text(json.area);
				$("#email").text(json.email);
			}
			
			}); //End json
	
		} //end if
	}); //end bind
}); //end ready
</script>
</head>

<body>
<div id="mainbody">
	<div id="left">
		<h3>
		Email:
		<input type="text"  id="textfield"/>
		<a href="#" class="button" id="find">Find</a>
		</h3> 
	</div>
	
	<div id="right">
		<img id="gif" src="./images/loading.gif" width="227px" height="92px" alt=""></img>
		
		<div id="error">
		</div>
		
		<div id="text_region">
		Name:
		Area:
		Email:
		</div>
		
		<div id="return">
		<p id="name"></p>
		<p id="area"></p>
		<p id="email"></p>
		</div>
		
	</div>
		
</div>
</body>
</html>