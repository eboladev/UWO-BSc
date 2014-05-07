<?php if (!defined('TMPL_DIR')) return; ?>

	<div id="mainPage">
        <div id="header">
            <a id="logout" href="index.php?action=logout" target="_self" title="Logout">Logout</a>
        </div>
        
        <div id="body">
            <div id="left">
                <p class="dob">Day of Birth: <strong><span id="birthday"><?php echo get_birthday_from_id(get_SESSION('id')); ?></strong></span></strong></p>
                
                <label for="currentdate">Select a Date:</label><br />
                <input id="dob_field" name="currentdate" type="text" />
                
                <p id="error">Error: Cannot Generate Graph</p>
            </div>
            
            <div id="right">
                <div id="forward">
                    <a id="prev" href="#" target="_self"></a>
                </div>
                
                <div id="biorhythm">
                    <div id="image">
                    	<img id="chart" src="" width="475" height="450" />
                        <img id="loading_gif" src="./images/loading.gif" alt="" />
                    </div>
                    
                    <div id="legend">
                    	<ul>
                        	<li><img src="./images/red.gif" />Physical: <span id="physical"></span></li>
                        	<li><img src="./images/green.gif" />Emotional: <span id="emotional"></span></li>
                        	<li><img src="./images/blue.gif" />Intellectual: <span id="intellectual"></span></li>
                        </ul>
                    </div>

                </div>
                
                <div id="backward">
                    <a id="next" href="#" target="_self"></a>
                </div>
            </div>
        </div>
	</div>