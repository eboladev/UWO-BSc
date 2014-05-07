#include "assembler_plugin.h"
#include <stdio.h>
#include <stdlib.h>

char mnemonic[] = "jmp";

void handle(int pass, instruction_t i, void (*emit)(int, instruction_t)){
	
	if(i.operand_count != 1 || i.operands[0].type != OP_LABEL){
		printf("Error: invalid argument");
		exit(EXIT_FAILURE);
	}
	else{
		emit(pass, ins("mov", 2, i.operands[0], reg(REG_PC)));
	}
}
		
