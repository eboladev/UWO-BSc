#include <stdio.h>

int main (void)
{
	int l=1; //factorial increase number
	float p=1;
	float number;
	float ee =1;
	float check=2; // greater than 1 to make it through first while loop

		while(number<=0) //continue looping till appropriate number is entered
		{
			printf("Enter a floating-point number: \n");
			scanf("%f", &number);
		}
		if(number>1) // greater than 1 the smallest term will be 1 + 1/1 = 2
		{
			printf("Value of e is 2 after 2 terms \n");
		}
		else
		{
			while(number<=check) // finding the term smaller than number
			{
				p *= l;
				l++;
				check = 1/p;
				ee += 1/p;
			}
			printf("Value of e is %.15f after %d terms \n",ee,l);
		}
	return 0;
}
