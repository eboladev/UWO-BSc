#include <stdio.h>
#include <stdlib.h>
#include <time.h>
#include <math.h>


int main (void)
{
srand((unsigned) time(NULL)); //initialize random with current time

double x =0;
double y =0;
double pi = 0;
int in = 0;
int i = 0;
int n = 0;

printf("Enter Sample Size: \n");
scanf("%d",&n);

for(i;i<=n;i++)
{
x = (double)rand()/(double)RAND_MAX;
y = (double)rand()/(double)RAND_MAX;
if (sqrt((x*x)+(y*y))<1)
{
        in++;
}
}
pi = 4*((double)in/(double)n);
printf("value of pie is %e \n",pi);
}
