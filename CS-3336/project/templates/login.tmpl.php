<?php if (!defined('TMPL_DIR')) return; ?>


<div id="mainBody">
    <form id="inputArea" action="index.php?action=login" method="post">
    
        <label for="email">Email:</label>
        <input type="text" id="email" name="email" value="<?php echo $HTML['email'];?>" />
    
        <label for="date_of_birth">Date of Birth (mm-dd-yyyy):</label>
        <input type="text" id="date_of_birth" name="date_of_birth" value="<?php echo $HTML['date_of_birth'];?>" />
    
        <span><?php echo $HTML['login_error'];?></span>
    
        <input class="submit" type="submit" value="Login" />
        <input type="hidden" name="submitted" value="yes" />
    </form>
</div>