#include "assembler_plugin.h"
#include <stdio.h>
#include <stdlib.h>

char mnemonic[] = "jz";

void handle(int pass, instruction_t i, void (*emit)(int, instruction_t)){
	
	if(i.operand_count != 1 || i.operands[0].type != OP_LABEL){
		printf("Error: invalid argument");
		exit(EXIT_FAILURE);
	}
	else{
		emit(pass, ins("not", 2, reg(REG_FZ), reg(REG_TMP3)));
		emit(pass, ins("not", 2, reg(REG_R0), reg(REG_TMP4)));
		emit(pass, ins("and", 3, reg(REG_TMP3), reg(REG_TMP4), reg(REG_TMP3)));
		emit(pass, ins("and", 3, reg(REG_FZ), i.operands[0], reg(REG_TMP4)));
		emit(pass, ins("or", 3, reg(REG_TMP3), reg(REG_TMP4), reg(REG_TMP3)));
		emit(pass, ins("mov", 2, reg(REG_TMP3), reg(REG_PC)));
	}
}
		
