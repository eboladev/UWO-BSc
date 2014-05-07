/*******************************************************************************
 *
 * File         : lib_ins_not.c
 * Author       : Matthew Stokes
 * Last Updated : April 8th, 2012
 *
 * Implements the 'not' synthetic instruction. 
 * 
 ******************************************************************************/

#include "assembler_plugin.h"
#include <stdio.h>
#include <stdlib.h>

char mnemonic[] = "not";

void handle(int pass, instruction_t i, void (*emit)(int, instruction_t)) 
{
	if (i.operand_count > 2 || i.operand_count==0)
	{
		fprintf(stderr, "Invalid arguments for 'not'\n");
		exit(EXIT_FAILURE);
	}
	else
	{
		
		if(i.operand_count==1)
			emit(pass, ins("nor", 3,i.operands[0],i.operands[0],i.operands[0]));
		if(i.operand_count==2){
			if(i.operands[1].type!=OP_REGISTER)
			{
				fprintf(stderr, "Invalid, second operand is not a register");
				exit(EXIT_FAILURE);
			}
			emit(pass, ins("nor",3,i.operands[0],i.operands[0],i.operands[1]));
		}
	}
}
