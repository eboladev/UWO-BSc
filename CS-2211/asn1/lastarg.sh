#!/bin/sh

counter=$#

if [ $counter -eq 0 ] ; then  
	echo "nothing"				# If no argument is provided, simply return nothing

else
	while [ $counter -gt 1 ]	# continue shifting until only a single number left
	do
		shift					# shift entries by one
		counter=`expr $counter - 1`	# decriment counter
	done
	echo $1					# print the last (rightmost) argument
fi
