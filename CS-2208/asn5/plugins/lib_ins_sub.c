#include "assembler_plugin.h"
#include <stdio.h>
#include <stdlib.h>

char mnemonic[] = "sub";

void handle(int pass, instruction_t i, void (*emit)(int, instruction_t)){
	
	if(i.operand_count != 3){
		printf("Error: wrong number of arguments");
		exit(EXIT_FAILURE);
	}
	else{
		emit(pass, ins("mov", 2, i.operands[1], reg(REG_TMP0)));
		emit(pass, ins("neg", 1, reg(REG_TMP0)));
		emit(pass, ins("add", 3, i.operands[0], reg(REG_TMP0), i.operands[2]));
	}
}
		
