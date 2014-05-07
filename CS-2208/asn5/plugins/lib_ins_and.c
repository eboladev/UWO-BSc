#include "assembler_plugin.h"
#include <stdio.h>
#include <stdlib.h>

char mnemonic[] = "and";

void handle(int pass, instruction_t i, void (*emit)(int, instruction_t)){
	
	if(i.operand_count != 3){
		printf("Error: wrong number of arguments");
		exit(EXIT_FAILURE);
	}
	else{
		emit(pass, ins("mov", 2, i.operands[1], reg(REG_TMP2)));
		emit(pass, ins("not", 2, i.operands[0], reg(REG_TMP1)));
		emit(pass, ins("not", 1, reg(REG_TMP2)));
		emit(pass, ins("or", 3, reg(REG_TMP1), reg(REG_TMP2), i.operands[2]));
		emit(pass, ins("not", 1, i.operands[2]));
	}
}
		
