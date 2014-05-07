#include <stdio.h>

int main (void)
{
	int time_of_day =-1; //value to enter the while loop
	int time_duration = 70; //value to enter the while loop
	int time;

	/*Time of day loop */
	while(time_of_day<0 || time_of_day>2359 || (time_of_day % 100)>= 60)
	{
		printf("Enter Time of Day: \n");
		scanf("%d", &time_of_day);
	}

	/*Time duration loop */
	while((time_duration%100)>= 60 || (time_duration%100)*-1 >= 60 )
	{
		printf("Enter Time Duration: \n");
		scanf("%d", &time_duration);
	}

	if(time_duration>0) //positive time increase
	{
		time = time_of_day + time_duration;
	}

	else //negative time increase
	{
		if((time_duration % 60)*-1 == 0) //hour decrements
		{
			time = time_of_day + time_duration;
		}
		else //dealing with cases with minute decrement
		{
			time = time_of_day + ((time_duration + (time_duration%60))+( 60 -time_duration%60)) -100;
		}
	}
	if(time % 100 >= 60) //dealing with positive minutes
	{
		time += 40;
	}

	while(time>2360) //2400 hour clock
	{
		time = time - 2400;
	}

	while(time<0) //cannot be negative time
	{
		time = time + 2400;
	}

	printf("Time is: %d \n",time);

return 0;
}
