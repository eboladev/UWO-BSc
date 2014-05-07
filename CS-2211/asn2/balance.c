#include <stdio.h>

int main (void)

{	/*Declarations*/
	float loan,interest,payment =-1; // for use in while loop
	int number =-1; //for use in while loop
	float balance, difference, lastpayment =0;
	int i = 1; //for use in for loop

	/* Setting up Inputs */
	while(loan<0)
	{
		printf("Enter amount of loan: \n");
		scanf("%f", &loan);
	}
	while(interest<0 || interest>1)
		{
		printf("Enter yearly interest rate (as a decimal number): \n");
		scanf("%f", &interest);
		}
	while(payment<0)
	{
		printf("Enter monthly payment: \n");
		scanf("%f", &payment);
	}
	while(number<0)
	{
		printf("Enter number of monthly payments: \n");
		scanf("%d", &number);
	}
	balance = loan;


	for(i; i<=number;i++)
	{
		difference = -payment + (balance * (interest/12));
		if(difference*-1 >= balance) //if you are going to pay off the loan
		{
			lastpayment = balance;
			balance = 0;
			printf("Your remaining balance on the loan after %d months is: %.2f \n" ,i,balance);
			break;
		}
		else
		{
			balance = balance + difference;
			printf("Your remaining balance on the loan after %d months is: %.2f \n" ,i,balance);
		}
	}
	if(lastpayment != 0)
	{
		printf("Your balance remaining is 0 and your last payment was %.2f in the %d month",lastpayment,i);
	}

	return 0;
}
