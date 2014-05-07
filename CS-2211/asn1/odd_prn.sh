#!/bin/sh

number=1
echo $0						# echoes its shell script file name

for i in $*					      # from first argument to last argument
do
	if [ `expr $number % 2` -ne 0 ]	# ignoring even arguments
	then
		echo $i
	fi

	number=`expr $number + 1`		# increment number
done
