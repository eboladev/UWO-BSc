#include "assembler.h"
#include "assembler_provided.h"
#include "hash_table.h"
#include "plugins.h"
#include <stdio.h>
#include <stdlib.h>

extern hash_table_t* user_symtab;	// User symbol table
extern hash_table_t* perm_symtab;	// Permanent symbol table
extern bool_t debug_mode;		// Whether or not we're in debug mode
extern segment_t current_segment;	// The current segment (DIR_TEXT or DIR_DATA)
extern int text_loc;			// Text segment location counter
extern int data_loc;			// Data segment location counter
extern int current_loc;			// Current location counter
extern FILE* output;			// Output file (the executable file)

void write_header()
{
	// Write the following (in this order) to the output file:
	// 	* The EF2208 magic number (there is a MAGIC_NUMBER macro in loader.h)
	// 	* The data segment location counter
	//	* The text segment location counter
	//
	// Lookup and use the fwrite() function to do this.  
	//
	// You've already got the 'output' variable available to you, which
	// is of type FILE* and is a handle to the open output file.  You do
	// not need to open the output file yourself -- it has already been
	// opened for you in the assemble() function, just before the start of
	// pass 2.

	fwrite(&MAGIC_NUMBER,1,sizeof(MAGIC_NUMBER)-1,output); //write the magic number
	fwrite(&data_loc,4,1,output); //write the data segment location counter
	fwrite(&text_loc,4,1,output); //write the text segment location counter
}

void write_instruction(opcode_t opcode, int rs1, bool_t imm, int rs2_or_imm, int rd)
{
	// Combine the given arguments into a 32-bit Western 2208 machine
	// language instruction and write the instruction to the output file
	
	unsigned int num_output=0;
	unsigned int shift=0;

	/*
	 * insert opcode into bits 27-31
	 */
	shift= opcode<<27;
	num_output = num_output | shift;

	/*
	 * insert rdest into bits 22-26
	 */
	shift = rd<<22;
	num_output = num_output | shift;
	
	/*
	 * insert first source register bits 17-21
	 */
	shift = rs1<<17;
	num_output = num_output | shift;

	if(imm==0) //padding with zeros
	{
		//bit 16 reserved for imm=0
	
		//11 bits of padding
	
		shift = rs2_or_imm;
		num_output = num_output | shift;
	}
	else{ //second source register

		shift = imm<<16;
		num_output = num_output | shift;
		
		shift = rs2_or_imm;
		num_output = num_output | shift;	
	}
	
	/*
	 * write instructions to the output file
	 */
	fwrite(&num_output,1,4,output);
	
}

void relocate_text_segment()
{
	// This function is called after the first pass.  We have two location
	// counters -- one for the data segment and one for the text segment.
	// Both counters start at 0.  However, in pass 2, we are going to be
	// writing to the executable file, so we need to relocate the text
	// segment so that it comes after the data segment in the executable
	// file.
	//
	// In our case, we simply need to adjust the addresses of all labels
	// in the text segment so that they have unique addresses occuring 
	// after the end of the data segment.
	//
	// So, you'll iterate through all entries in the user symbol table.  If
	// you find any labels in the text segment, you'll need to relocate them
	// (change their addresses) so that they are relative to the data 
	// segment.
	//
	// For example, suppose we have the following code:
	//
	// .data
	// _i: .word 0xAABBCCDD       # Bytes 0 - 3
	// _j: .half 0xAABB           # Bytes 4 - 5
	// _k: .half 0xCCDD           # Bytes 6 - 7
	//
	// .text
	// _main: mov 0, %r1          # Bytes 8 - 11
	//        mov 1, %r2          # Bytes 12 - 15
	// _loop: ...                 # Bytes 16 - ...
	//
	// Before relocation, our symbol table would look like the following
	// (remember: there are two separate location counters):
	//
	// Before relocation:
	// 
	// Segment         Label            Address
	// ========================================
	// DATA            _i                     0
	// DATA            _j                     4
	// DATA            _k                     6
	// TEXT            _main                  0
	// TEXT            _loop                  8
	// 
	// After relocation:
	// 
	// Segment         Label            Address
	// ========================================
	// DATA            _i                     0
	// DATA            _j                     4
	// DATA            _k                     6
	// TEXT            _main                  8
	// TEXT            _loop                 16  
	//
	// Hint: how could we use the data location counter to accomplish this
	//       task?
	//
	// To get an array of values in the user_symbol_table, see the 
	// description of the hash_table_values() function in hash_table.h.
	// Note that this function dynamically allocates memory for the array
	// it returns.  Thus, you will need to free this memory at the end of
	// this function when you are done with the array.

	/*
	 * get an array of values in the user_symbol_table
	 */
	user_symtab_entry_t** value_table = (user_symtab_entry_t**) hash_table_values(user_symtab);
	
	int i;
	/*
	 * Iterate through the table
	 */
	for(i=0;i<user_symtab->size;i++)
	{
		if(value_table[i]->segment!=DIR_DATA)//only add if its TEXT segment
		value_table[i]->address += data_loc; //add the data location counter to the address
	}
}

int get_rs2_or_imm(operand_t operand)
{
	// Take the operand passed to the function (which should be the 
	// second operand in a 3-operand machine language instruction),
	// and return an integer value such that:
	//
	// * If the operand is a register, return its integer register number
	//
	// * If the operand is an immediate, return the immediate value
	//
	// * If the operand is a label, look up its address in the user symbol
	//   table and return it (see the hash_table_get() function in hash_table.h)
	//
	// See the description of the operand_t structure in assembler_provided.h
	// to determine how to check its type.
	//
	// Also see the description of the operand_type_t enumeration in
	// assembler_provided.h.
	//
	// You'll probably want to use a switch statement in this function.
	
	switch(operand.type)
	{
		case OP_REGISTER:

			return operand.value.integer; //integer register number

		case OP_IMMEDIATE:
		
			return operand.value.integer; //immediate value
		
		case OP_LABEL:
			{	
			user_symtab_entry_t* item = (user_symtab_entry_t*) hash_table_get(user_symtab,operand.value.string);

			if(item ==NULL) //if not found
			{
				printf("Error label %s found in symbol table",operand.value.string);
				exit(EXIT_FAILURE);
			}
			return item->address;
			}
			
	}
}

void do_align(int pass, int align)
{
	// This function should align the current location counter to the
	// specified boundary.  For example, suppose that:
	//
	// * The current location counter is 9
	// * This function receives the value 4 in its align parameter
	//
	// It should therefore increment the location counter to 12, so that
	// it is aligned to a 4-byte boundary.
	//
	// If we're in pass 1, then we should just align the location counter
	// and return.
	//
	// If we're in pass 2, then we should align the location counter and
	// write zeros to the output file for each byte that we're skipping over.
	// In the example above, in pass 2, three zero bytes would be written to
	// the file (since 12 - 9 = 3).  You can use the fputc() function 
	// (Google it) to write a byte to the output file.
	
	/*
	 * If we are in pass 1 just align the location counter and return
	 */
	if(pass==1)
	{
		if(current_loc%align != 0) //if not already aligned on boundary
			current_loc+=(align-(current_loc%align)); //align
	}
	else{
		int i;
		if(current_loc%align !=0) //if not already aligned on boundary
		{
			/*
		 	* Write zeros to the output file for each byte skipped over
		 	*/
			for(i=0;i<(align-(current_loc%align));i++)
			{
				fputc(0,output);		
			}

		current_loc+=(align-(current_loc%align));//align
		}
	}
}

void handle_ns_instruction(int pass, instruction_t i)
{
	// This function is called whenever a non-synthetic instruction is to
	// be handled.  It may be called as a result of the parser encountering
	// a non-synthetic instruction in the assembly code, or it may be called
	// as a result of one of your plugins emitting a non-synthetic
	// instruction.
	//
	// The instruction is passed to this function, along with the current
	// pass of the assembler (1 or 2).  This function should:
	//
	// * Look up the instruction mnemonic in the permanent symbol table
	//    (see the description of hash_table_get() in hash_table.h and the
	//     description of the instruction_t struct in assembler_provided.h)
	//
	// * If the mnemonic is not found, print an error message 
	//   ("Invalid instruction: 'XXX'" or something similarly descriptive)
	//   and exit the program (you can use exit(EXIT_FAILURE);)
	//
	// * Increment the current location counter by the appropriate amount
	//   (how many bytes is a machine language instruction in the Western
	//   2208?)
	//
	// * If we're in pass 1, stop processing.  There's nothing more to do
	//   right now
	//
	// * Otherwise, if we're in pass 2, you'll need to set the values of 
	//   
	//   rs1          (Source register 1)
	//   rs2_or_imm   (The second operand -- either an immediate or a register)
	//   rd           (Destination register)
	//   imm          (The 'imm' bit that determines if a machine language
	//                 instruction contains an immediate (1) or a register (0)
	//                 in its second operand field)
	//   
	//   These values will depend on the number of operands passed to the
	//   instruction:
	//
	//   Operands	Example		Interpretation
	//   =========================================
	// 
	//   1 operand   tst %r1             i.operands[0] contains register source 1
	//                	  	     Set everything else to zero
	//
	//   2 operands  stb %r1, [%r5]      i.operands[0] contains register source 1
	//                                   i.operands[1] contains destination register
	//                                   Set everything else to zero
	//
	//   3 operands  nor %r1, %r1, %r1    i.operands[0] contains register source 1
	//               nor %r1, 0xFF, %r1   i.operands[1] contains operand 2 
	//                                     (either a register or immediate -- use
	//                                      your get_rs2_or_imm() function to get
	//                                      this value)
	//                                    i.operands[2] contains destination register
	//                                    imm depends on the type of operand 2
	// 
	// Once you have all parts, call your write_instruction() function
	// to encode the machine language instruction and write it to the 
	// executable file (only in pass 2)	
	if(pass==1)
	{
		current_loc+=4; //machine language instruction is 4 bytes long
	}
	else
	{
		current_loc+=4;
		perm_symtab_entry_t* mnemonic_in_table = (perm_symtab_entry_t*)hash_table_get(perm_symtab,i.mnemonic); //instruction mnemonic in permanent symbol table
		
		if(mnemonic_in_table ==NULL) //instruction mnenonic not in symbol table
		{
			printf("Invalid instruction: %s",i.mnemonic);
			exit(EXIT_FAILURE);
		}

		/*
		 * See operand table in comments above by Jeff Shantz for description of
		 * what each case is writing out
		 */
       		switch(i.operand_count)
   		{
                case 1:
			write_instruction(mnemonic_in_table->opcode,i.operands[0].value.integer,0,0,0);
                        break;
                case 2:
			write_instruction(mnemonic_in_table->opcode,i.operands[0].value.integer,0,0,i.operands[1].value.integer);
                        break;
                case 3:
			if(i.operands[1].type==OP_REGISTER)
			write_instruction(mnemonic_in_table->opcode,i.operands[0].value.integer,0,get_rs2_or_imm(i.operands[1]),i.operands[2].value.integer);
			else{	
			write_instruction(mnemonic_in_table->opcode,i.operands[0].value.integer,1,get_rs2_or_imm(i.operands[1]),i.operands[2].value.integer);		
			}
                        break;
       		}
	}
}

void handle_directive(int pass, directive_t dir)
{
	// This function is called every time the parser encounters an assembler
	// directive (i.e. .byte, .half, .word, .str, .text, .data, .align).
	//
	// See the directive_t structure in assembler_provided.h, along with
	// the directive_type_t enum.  You can use a switch statemnt in this
	// function to switch on the type of the directive.
	//
	// The following describes the actions to take when each directive is
	// received:
	//
	// DIR_TEXT (.text)	
	//	* Align the current location counter to a 4-byte 
	//	  boundary (use your do_align() function)
	//
	//	* If we're currently in the data segment, then switch up the
	//	  location counters (that is, the current location counter
	//	  must be saved in the data location counter, and the current
	//	  location counter should then be set to the text location
	//	  counter)
	//    
	//      * Set the current segment to the text segment
	//
	// DIR_DATA (.data)
	//	* Switch up the location counters and set the current segment
	//	  to the data segment
	//
	// DIR_BYTE (.byte)
	//	* Increment the current location counter by the appropriate
	//	  amount.
	//	* If we're in pass 2, write the value of the operand to .byte
	//	  to the executable file (see the directive_t structure in
	//	  assembler_provided.h for details on how to get its operand).
	//	  You can use fwrite() to write the value.
	//
	// DIR_HALF (.half)
	//	* Same as DIR_BYTE, except now we're dealing with a halfword.
	//
	// DIR_WORD (.word)
	//	* Same as DIR_BYTE, except now we're dealing with a word.
	//
	// DIR_STR (.str)
	//	* Increment the current location counter by the length of the
	//	  string operand (you can use the strlen() function, but note
	//	  that the length that it returns does not include the
	//	  terminating null character in the string).
	//	* If we're in pass 2, write the string to the executable file
	//
	// DIR_ALIGN (.align)
	//	* Align the current location counter to the boundary specified
	//	  in the operand to the .align directive (use your do_align()
	//	  function)
	//
	// You are free (and encouraged) to break up this function into multiple
	// helper functions.
	
	switch(dir.type)
	{
		case DIR_TEXT:
			do_align(pass,4);
			data_loc = current_loc;
			current_loc = text_loc; 
			current_segment  = DIR_TEXT;
			break;
		
		case DIR_DATA:
				
			text_loc = current_loc;
			current_loc = data_loc;
			current_segment  = DIR_DATA;
			break;

		case DIR_ALIGN:
			do_align(pass,dir.operand.value.integer);
			break;
			
		case DIR_BYTE:
			if(pass ==1)
			{
				current_loc=current_loc + 1;
			}
			else{
				current_loc = current_loc +1;
				unsigned char temp = (unsigned char) dir.operand.value.integer;
				fwrite(&temp, 1,1, output); //write to output file		
			}
			break;		

		case DIR_HALF:
			if(pass ==1)
			{
				current_loc=current_loc + 2;
			}
			else{
				current_loc = current_loc +2;
				unsigned short temp = (unsigned short) dir.operand.value.integer;
				fwrite(&temp, 1, 2, output); //write to output file		
			}
			break;	
			
		case DIR_WORD:
			if(pass ==1)
			{
				current_loc=current_loc + 4;
			}
			else{
				current_loc = current_loc +4;
				fwrite(&dir.operand.value.integer, 1, 4, output); //write to output file		
			}
			break;	
			
		case DIR_STR:
			if(pass ==1)
			{
				current_loc=current_loc + strlen(dir.operand.value.string)+1;
			}
			else{
				current_loc=current_loc + strlen(dir.operand.value.string)+1;
				fwrite(dir.operand.value.string, 1, strlen(dir.operand.value.string)+1, output); //write to output file		
			}
			break;				
	}
}

void handle_label(int pass, char* label)
{
	// This function is called by the parser each time a new label is 
	// encountered in the assembly source.
	//
	// If we're in pass 2, we do nothing since the user symbol table is
	// built in pass 1 (you remember that from Topic 8, don't you?).
	//
	// If we're in pass 1, then first look up the label in the user 
	// symbol table (see hash_table_get() in hash_table.h).  If it already
	// exists, the print an error message (e.g. "Duplicate label 'XXX' already exists")
	// and exit the program (exit(EXIT_FAILURE)).
	//
	// Otherwise, if the label does not already exist, allocate a new user
	// symbol table entry for it (see the user_symtab_entry_t structure in
	// assembler_provided.h and look up the malloc() function on Google to
	// determine how to dynamically allocate memory).
	//
	// Next, set the label, segment, and address of the entry (the address
	// will be the value of the current location counter) and store it in
	// the user symbol table (see the hash_table_set() function in
	// hash_table.h).

	if(pass!=2) //do nothing if in pass 2
	{
	
		/*
		 * look up the label in the user symbol table
		 */
		user_symtab_entry_t* the_label = (user_symtab_entry_t*)	hash_table_get(user_symtab,label);
	
		/*
		 * If label already exists print error message and exit
		 */
		if(the_label !=NULL)
		{
			printf("Duplicate label %s already exists",the_label->label);
			exit(EXIT_FAILURE);
		}

		/*
		 * allocate a new user symbol table entry. Set the label, segment and address of
		 * the entry and store it in the user symbol table
		 */
		else{
			the_label =(user_symtab_entry_t*) malloc(sizeof(user_symtab_entry_t));
			the_label->label = label;
			the_label->segment = current_segment;
			the_label->address = current_loc;

			hash_table_set(user_symtab, label, the_label);	
		}
	}
}

void load_plugins()
{
	// This function is called when the assembler first starts up 
	// (before it begins parsing).  At this point, the permanent symbol
	// table will already have been built, but this function will load
	// any plugins present and store them in the permanent symbol table
	// as well, adding new synthetic instructions to the assembler.
	//
	// Have a look at plugins.h.  You'll first need to get an array 
	// containing the names of the plugins in the ./plugins directory.
	// Once you have this, you'll need to iterate through each filename
	// and attempt to load it (see plugins.h).
	//
	// If the plugin was loaded successfully (a non-NULL value is returned),
	// then:
	//
	// * Check if we're in debug mode (the static variable 'debug_mode')
	//	* If so, print a message indicating that the plugin was loaded
	//	  and include its mnemonic  (e.g. "Loaded plugin 'and'").
	// 
	// * Add the plugin entry to the permanent symbol table
	//   (see hash_table_set() in hash_table.h)
	
	char** plugin_list;
	int count=0;
	perm_symtab_entry_t* entry;
	int hashtable_insert;
	
	/*
	 * get an array containing the names of the plugins in teh .plugins directory
	 */
	plugin_list = get_plugin_names("./plugins",&count);
	
	/*
	 * Iterate through each filename and attempt to load the plugin
	 */
	int i;
	for(i=0;i<count;i++)
	{
		entry = load_plugin(plugin_list[i]); //load plugin
		
		if(entry == NULL)
		{
			printf("Didn't load plug in properly");	
		}
		else{
			if(debug_mode==TRUE) //print message for debug mode
			{
				printf("Loaded plugin %s\n", entry->mnemonic);
			}
			
			/*
			 * insert into perm_symtab table
			 */
			hashtable_insert = hash_table_set(perm_symtab, entry->mnemonic, entry);
			
			if(hashtable_insert !=0)
			{
				printf("Error inserting into hash table");
			}
		}
	}	
}
