<?php
require_once('./functions.inc.php');

	$path_to_charts = "../charts/";
	$url = 'https://chart.googleapis.com/chart?';	
	
	// Setup array to return
	$returned_array = array();
	
	// Check if valid date
	if($_GET['diff']<0)
	{
		$returned_array['error'] = true;
	}
	else
	{
		// Calculate, physical, and emotional biorhythm values for today
		$todays_bio_values = get_dates_biorhythms($_GET['date'], $_GET['birthday']);
		$returned_array['physical'] = $todays_bio_values['physical'];
		$returned_array['emotional'] = $todays_bio_values['emotional'];
		$returned_array['intellectual'] = $todays_bio_values['intellectual'];
		
		//date has to be in yyyy-mm-dd format
		$date_converted = substr($_GET['date'], 6, 4) . "-" . substr($_GET['date'], 0, 2) . "-" . substr($_GET['date'], 3, 2) ;
		$birthday_converted = substr($_GET['birthday'], 6, 4) . "-" . substr($_GET['birthday'], 0, 2) . "-" . substr($_GET['birthday'], 3, 2) ;

		//Get D (date difference)
		$interval = date_diff(date_create($birthday_converted), date_create($date_converted));
		$D = $interval->format('%a');

		//check if chart already exists
		$chart_name = $_GET['birthday'] . "---" . $D . ".png";
		if(file_exists($path_to_charts . $chart_name))
		{
			$returned_array['chart'] = "./charts/" . $chart_name;
		}
		else //generate chart
		{
			$date_string = generate_string($date_converted);
			sleep(1); //artificial delay
			$first = $D-10;
			$last = $D+10;
			$chart = array(
				'cht'=>'lc',
				'chs'=>'700x408',
				'chg'=>'5,10',
				'chxt'=>'x,y',
				'chd'=>'t:-1|-1|-1|-1',
				'chco'=>'FF00F0,00FF00,0000FF,FF0000',
				'chfd'=>"1,x,{$first},{$last},0.1,sin(x*2*3.14159265359/28)*100|2,x,{$first},{$last},0.1,sin(x*2*3.14159265359/33)*100|3,x,{$first},{$last},0.1,sin(x*2*3.14159265359/23)*100",
				'chxr'=>'0,-10,10,2|1,-100,100,20',
				'chds'=>'-10,10,-100,100',
				'chm'=>'h,FFBE00,1,0.5,1|V,FFBE00,1,100,1',
				'chxl'=>"0:|{$date_string}"
			);
			
			$url = $url . http_build_query($chart);
	
			//save chart	
			$saveto = $path_to_charts . $chart_name;						
			save_chart($saveto, $url);
			 
			$returned_array['chart'] = "./charts/" . $chart_name;
		}
	}
	//return
	echo json_encode($returned_array);
		
?>