#include "assembler_plugin.h"
#include <stdio.h>
#include <stdlib.h>

char mnemonic[] = "neg";

void handle(int pass, instruction_t i, void (*emit)(int, instruction_t)){

	if(i.operand_count < 1 || i.operand_count > 2){
		printf("Error: wrong number of arguments");
		exit(EXIT_FAILURE);
	}
	else if(i.operand_count == 1){
		emit(pass, ins("not", 1, i.operands[0]));
		emit(pass, ins("add", 3, i.operands[0], imm(1), i.operands[0]));
	}

	else{
		emit(pass, ins("not", 2, i.operands[0], reg(REG_TMP0)));
		emit(pass, ins("add", 3, reg(REG_TMP0), imm(1), i.operands[1]));
	}
}
		
