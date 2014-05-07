#!/bin/sh

files=`ls -A *.c`							
# process every file with name ended by .c

for i in $files
do
	if test -f "$i"							
# ensuring in the current directory
	then
		if grep "\<printf\>" $i >/dev/null			
# searching for the string printf
		then

			if grep "#include <stdio.h>" $i >/dev/null	
# searching for the header
			then 
				echo "Header Already Present"
			else
				echo "#include <stdio.h>" > tempfile	
# creating a new file with the header at the top
				cat $i >> tempfile			
# copying all the text from previous file into this newly created file
				cat tempfile > $i			
# overwriting the previous file without the header
				rm tempfile				
# removing the tempfile
				echo "WORKED SUCESSFULLY"
			fi
		fi
		if grep "\<fprintf\>" $i >/dev/null			
# searching for the string fprintf   
        	then                          
                	if grep "#include <stdio.h>" $i >/dev/null	
# searching for the header
                	then
                        	echo "Header Already Present"
               		else
          	                echo "#include <stdio.h>" > tempfile    
# creating a new file with the header at the top
                                cat $i >> tempfile                     
# copying all the text from previous file into this newly created file
                                cat tempfile > $i                       
# overwriting the previous file without the header
                                rm tempfile                             
# removing the tempfile
                                echo "WORKED SUCESSFULLY"

                        fi
                fi
	fi
done
