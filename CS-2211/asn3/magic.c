#include <stdio.h>



int main (void) {

	int size =0;
	int column =0;
	int row =0;

	while(size<1||size>99||size%2==0)
	{
	printf("Enter magic square size: (Odd, between 1-99) \n");
	scanf("%d", &size);
	}
/*Initialize Array */
	int m[size][size];
	int i = 0;
	for(i;i<size;i++){
		int j =0;
		for(j;j<size;j++){
			m[i][j] = 0;
	}}

/*First Case */
	row = 0;
	column = (size/2);
	m[row][column] = 1;
	row = size-1;
	column =(size/2) +1;


	for(i=2;i<=size*size;i++)
	{
		if(row==-1)
		{
			row=size-1;
		}
		if(column==size)
		{
			column=0;
		}
		if(m[row][column]!=0)
		{
			column =column -1;
			row = row+2;
			if(row==size+1)
			{
				row = 1;
			}
			if(column==-1)
			{
				column = size-1;
			}
		}

		m[row][column] =i;
		column++;
		row--;
	}
	printf("Magic Square \n");
	/*Print Array */
	i=0;
		for(i;i<size;i++){
			int j =0;
			for(j;j<size;j++){
				int a = m[i][j];
				printf("%d \t",a);
		}
	printf("\n");
		}
return 0;
}
