#!/bin/sh

if [ $# -eq 2 ] ; then									

	if test -f "$2" ; then								
	
		if [ $1 -eq 0 ] ; then 							

			smallest=`head -1 $2`						
			collection=`cat $2`	
			for i in $collection
			do
				if [ $i -lt $smallest ] ; then				
				smallest=$i						
				fi

				if [ $i -eq $smallest ] ; then				
			    if [ `echo $i | tr -d "."` -lt `echo $smallest | tr -d "."` ]
				then
				smallest=$i								
				fi
				fi

			done
			echo $smallest			

		elif [ $1 -eq 1 ] ; then						

			largest=`head -1 $2`						
			collection=`cat $2`
			for i in $collection
			do
        			if [ $i -gt $largest ] ; then				
        			largest=$i						
				fi

				if [ $i -eq $largest ] ; then					
			     if [ `echo $i | tr -d "."` -gt `echo $largest | tr -d "."` ]				then
				largest=$i							
				fi
				fi

			done
			echo $largest

		
		else
		echo "Option must be 0 or 1"
		fi	
	else
	echo "$2 not found"
	fi		
else 
echo "Usage: nums option input-file"
fi
