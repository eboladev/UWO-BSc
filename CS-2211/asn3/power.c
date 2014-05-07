#include <stdio.h>


//Assumed that we are computing using integers

int power(int x,int n)
{
if (n==0)

	return 1;

else

	return x * power(x,n-1);

}


int main (void) {

	int x =0;
	int n =0;
	int value=0;

	printf("Enter a value for x: \n");
	scanf("%d", &x);
	printf("Enter a value for n: \n");
	scanf("%d", &n);

if(n%2==0)
{
	value = power(x,n/2);
	value *= value;
}

else
{
value = power(x,(n-1)/2);
value *= value * x;
}

printf("value is: %d \n",value);

return 0;
}
