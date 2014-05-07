! *********************************************************************************************
!
! execute_codes.m
! Assignment #4
! Author: Matthew Stokes
!
! Description: This program implements a small subset of the instructions of the
! Java Virtual Machine (JVM). The instructions are read from the byte code array passed
! in as the first argument. Second argument is the number of bytes in the byte stream
!
! based on code written for CS208 by Chris Snow
! modified by A. Downing Nov. 2009
! modified by Jeff Shantz Mar. 2012
! 
!
! *********************************************************************************************
! Register Legend
!
!  stkptr_r             %l7     Points to the top element in the stack
!  op1_r                %l0     Stores the first operand for math operations
!  op2_r                %l1     Stores the second operand for math operations
!  pc_r			%l2	Stores the bytecode fetched
!  inst_r		%l3	Stores the instruction to be executed
!  bc_arg1_r		%l4	Stores arg 1 to be passed into the byte code
!  local_ptr_r		%l5	Stores pointer to local Java array
! *********************************************************************************************
include(macro_defs.m)
! *********************************************************************************************
! Register Definitions
! *********************************************************************************************
define(stkptr_r,l7)     ! points to current top of stack
define(op1_r,l0)        ! stores first operand for math operations
define(op2_r,l1)        ! stores second operand for math operations
define(pc_r,l2)		! program counter
define(inst_r,l3)	! instruction register
define(bc_arg1_r,l4) 	! arg 1 to be passed into the byte code
define(local_ptr_r,l5)  ! local pointer to the local Java array
! *********************************************************************************************
! Constants
! *********************************************************************************************
define(MAXSIZE,20)      ! max. number of stack elements
define(LEN,4)           ! size of each stack element
define(EOL,10)          ! newline character
define(JAVA_ARRAY,10)	! size of max local Java array

! Constants for bytecode operations

define(BIPUSH,16)       ! push (param 1) onto the operand stack
define(POP,87)          ! pop the top element from the operand stack
define(IADD,96)         ! (2nd from top) + (top), result onto stack
define(ISUB,100)        ! (2nd from top) - (top), result onto stack
define(IMUL,104)        ! (2nd from top) * (top), result onto stack
define(IDIV,108)        ! (2nd from top) / (top), result onto stack
define(RETURN,177)      ! return (exit from the program)
define(IPRINT,187)      ! print top element on operand stack
define(ICONST_0,3) 	! push constant 0 onto stack
define(ILOAD,21)	! push local variable at index n onto the stack
define(ISTORE,54)	! pop stack and store local variable at index n
define(DUP,89)		! duplicate the top item on the stack
define(IREM,112)	! remainder (2nd from the top) / (top), result onto stack
define(ISHR,122)	! arithmetic shift right
define(IINC,132)	! increment local variable at index n by d
define(IFEQ,153)	! pop top item from operand stack, branch to offset if equal zero
define(GOTO,167)	! Branch to offset
define(IREAD,186)	! read an interger from the keyboard
! *********************************************************************************************
! Main Program begins
! *********************************************************************************************

.data

! divide by zero error message

div_by_zero:    .asciz "ERROR: Divide by zero. Terminating\n"   
        
        .text

        local_var
        var(stack_s,LEN,LEN*MAXSIZE)            ! offset for the operand stack
	var(local_array_s,LEN,LEN*JAVA_ARRAY)	! local variable array
	
        begin_fn(execute_codes)

! set up the operand stack

init:   add     %fp, stack_s, %stkptr_r          ! point to start of stack memory
        add     %stkptr_r, LEN*MAXSIZE, %stkptr_r! move pointer to 'bottom' of stack

! set up the local array

	add	%fp, local_array_s, %local_ptr_r ! point to start of local array
	
! start processing bytecodes

	mov	%i0, %pc_r			! initalize program counter to the first byte
top:
	ldub	[%pc_r], %inst_r		! load instruction pointed to by program counter into instruction register

        cmp     %inst_r, RETURN                     ! the 'return' command? (177)
        be      quit
        nop

        cmp     %inst_r, IPRINT                     ! the 'iprint' command? (187)
        be      iprint
        nop
        
        cmp     %inst_r, BIPUSH                     ! the 'bipush' command? (16)
        be      bipush
        nop

        cmp     %inst_r, POP                        ! the 'pop' command? (87)
        be      pop
        nop

        cmp     %inst_r, IADD                       ! the 'iadd' command? (96)
        be      iadd
        nop

        cmp     %inst_r, ISUB                       ! the 'isub' command? (100)
        be      isub
        nop

        cmp     %inst_r, IMUL                       ! the 'imul' command? (104)
        be      imul
        nop

        cmp     %inst_r, IDIV                       ! the 'idiv' command? (108)
        be      idiv
        nop

! Additional bytecodes for Assignment 4

	cmp	%inst_r, ICONST_0		! the 'iconst_0' command? (3)
	be	iconst_0
	nop

	cmp	%inst_r, ILOAD			! the 'iload' command? (21)
	be	iload
	nop

	cmp	%inst_r, ISTORE			! the 'istore' command? (54)
	be	istore
	nop

	cmp	%inst_r, DUP			! the 'dup' command? (89)
	be	dup
	nop

	cmp	%inst_r, IREM			! the 'irem' command? (112)
	be 	irem
	nop

	cmp	%inst_r, ISHR			! the 'ishr' command? (122)
	be	ishr
	nop

	cmp	%inst_r, IINC			! the 'iinc' command? (132)
	be	iinc
	nop

	cmp	%inst_r, IFEQ			! the 'ifeq' command? (153)
	be	ifeq
	nop

	cmp	%inst_r, GOTO			! the 'goto' command? (167)
	be	goto
	nop	

!***********************************************************************************
! Additional bytecodes for Assignment #4
!***********************************************************************************

	cmp	%inst_r, IREAD			! the 'iread' command? (186)
	be	iread
	nop
	
! assume that there are no illegal bytecodes in the bytecode stream,
! so should never get here by dropping through all the compares

! Execute bytecodes:
! push a value (specified as an extra parameter) onto the stack

bipush:	
	ldsb	[%pc_r+1], %bc_arg1_r		! get the extra parameter
        
        dec     LEN, %stkptr_r                  ! push the parameter onto the stack
        st      %bc_arg1_r, [%stkptr_r]

	inc	2,%pc_r				! incriment program counter

	ba      top                             ! back to read next command
        nop

! pop the top element off the operand stack

pop:    inc     LEN, %stkptr_r                  ! move stack ptr and go back
	inc 	%pc_r				! incriment the program counter
        ba      top                             
        nop
        
! add the top two stack elements and put the result back on the stack

iadd:   ld      [%stkptr_r], %op2_r             ! get first operand
        inc     LEN, %stkptr_r
        ld      [%stkptr_r], %op1_r             ! get second operand
                                                ! (NOTE: don't shift the pointer)
        add     %op1_r, %op2_r, %op1_r          ! do the addition
        st      %op1_r, [%stkptr_r]             ! put the result back on the stack

	inc	%pc_r				! incriment the program counter
        ba      top                             ! back to the start
        nop

! subtraction: (2nd from the top) - (top), put the result back on the stack

isub:   ld      [%stkptr_r], %op2_r             ! load first operand
        inc     LEN, %stkptr_r
        ld      [%stkptr_r], %op1_r             ! load second operand
                                                ! (NOTE: don't shift the pointer)
        sub     %op1_r, %op2_r, %op1_r          ! do the subtraction
        st      %op1_r, [%stkptr_r]             ! put the result back on the stack

	inc 	%pc_r				! incriment the program counter
        ba      top                             ! back to the start
        nop

! multiply the top two stack elements, and put the result back on the stack

imul:   ld      [%stkptr_r], %o1                ! load first operand
        inc     LEN, %stkptr_r
        ld      [%stkptr_r], %o0                ! load second operand
                                                ! (NOTE: don't shift the pointer)
        call    .mul                            ! do the multiplication
        nop
        st      %o0, [%stkptr_r]                ! put the result back on the stack

	inc	%pc_r				! incriment the program counter
        ba      top                             ! back to the start
        nop

! (2nd from the top) / (top), result back on the stack

idiv:   ld      [%stkptr_r], %o1                ! load first operand
        inc     LEN, %stkptr_r

        tst     %o1                             ! make sure it's not divide by zero
        be      div_error
        nop

        ld      [%stkptr_r], %o0                 ! load second operand
                                                ! (NOTE: don't shift the pointer)
        call    .div                            ! do the division
        nop
        st      %o0, [%stkptr_r]                 ! put the result back on the stack

	inc	%pc_r				! incriment the program counter
        ba      top                             ! back to the start
        nop

! print the number at the top of the operand stack

iprint: call    writeInt                        ! print value at top of stack
        ld      [%stkptr_r], %o0                ! [DELAY SLOT FILLED]

        call    writeChar                       ! print blank line
        mov     EOL, %o0                        ! [DELAY SLOT FILLED]

	inc	%pc_r				! incriment the program counter
        ba      top                             ! get next command
        nop

!***********************************************************************************
! Additional Methods for Assignment #4
!***********************************************************************************
!Push constant 0 onto the stack

iconst_0:
        dec     LEN, %stkptr_r                  ! push 0 onto the stack
        st      %g0, [%stkptr_r]

        ba      top                             ! back to the start
        inc	%pc_r				! [DELAY SLOT FILLED]

! the iload bytecode is immediately followed by a single byte argument n which specifies the
! offset into the local variable array (starting from index 0):
!   Read the byte argument n.
!   Copy the value from element n of the array and push it onto the operand stack.
	
iload:
	ldub	[%pc_r+1], %bc_arg1_r		! get the extra parameter
	sll	%bc_arg1_r, 2, %bc_arg1_r
	
	ld	[%local_ptr_r + %bc_arg1_r], %o0 ! push element n onto the operand stack
        dec     LEN, %stkptr_r
	st	%o0, [%stkptr_r]
	
	
	ba	top				! back to the start
	inc	2,%pc_r				! [DELAY SLOT FILLED]

! the istore bytecode is immediately followed by a single byte argument n which specifies the
! offset n into the local variable array (starting from index 0):
!    Read the byte argument n.
!    Pop the top value off the operand stack and store it into the element with index n in the local
!    variable array.

istore:

	ldub	[%pc_r+1], %bc_arg1_r		! get the extra parameter
	sll	%bc_arg1_r, 2, %bc_arg1_r
	
	ld	[%stkptr_r],%o0			! pop top value off operand stack
        inc     LEN, %stkptr_r
	st	%o0,[%local_ptr_r + %bc_arg1_r]	! store into element with index n in local variable array
	
	
	ba	top				! back to start
	inc	2,%pc_r				! [DELAY SLOT FILLED]

! Duplicate the top item on the stack	
	
dup:
	ld	[%stkptr_r],%bc_arg1_r		! retrieve top item on stack
        dec     LEN, %stkptr_r                  ! push the parameter onto the stack
        st      %bc_arg1_r, [%stkptr_r]
	
	ba	top				! back to the start
	inc	%pc_r				! [DELAY SLOT FILLED]

! Find the remainder on integer division:
! (second from top) / (top)
! push result back on stack

irem:
	ld      [%stkptr_r], %o1                ! load first operand
        inc     LEN, %stkptr_r

        tst     %o1                             ! make sure it's not divide by zero
        be      div_error
        nop

        ld      [%stkptr_r], %o0                ! load second operand
                                                ! (NOTE: don't shift the pointer)
        call    .rem                            ! do the mod
        nop
        st      %o0, [%stkptr_r]                ! put the result back on the stack

        ba      top                             ! back to the start
	inc	%pc_r				! [DELAY SLOT FILLED]
	
! Arithmetic shift right: pop and shift the top item on the stack n bit
! positions to the right with an arithmetic shift, where n is the value in
! the low 5 bits of the next item on the stack. It too is popped, and the
! shifted result is pushed back on the stack.

ishr:	
	ld	[%stkptr_r],%o0
        inc     LEN, %stkptr_r
	ld	[%stkptr_r],%o1

	
	and	0x001F,%o1,%o1			! mask
	sra	%o0,%o1, %o0			! shift by n bits

	st	%o0, [%stkptr_r]	

	ba	top				! back to the start
	inc	%pc_r				! [DELAY SLOT FILLED]
	
! the iinc bytecode is immediately followed by two byte arguments n and d, which specify:
! 1. the offset n into the local variable array (starting from index 0).
! 2. the value d by which to increment that variable.
!      Read the byte argument n.
!      Read the byte argument d.
!      Add the value d to the value in the element with index n in the local variable array.

iinc:

	ldub	[%pc_r+1], %bc_arg1_r		! get the 1st parameter
	sll	%bc_arg1_r, 2, %bc_arg1_r
	
	ldsb	[%pc_r+2], %o0			! get the 2nd parameter
	
	ld	[%local_ptr_r + %bc_arg1_r], %o1	! offset n into the local variable array
	add	%o0, %o1, %o1
	st	%o1, [%local_ptr_r+%bc_arg1_r]

	ba	top				! back to the start
	inc	3, %pc_r			! [DELAY SLOT FILLED]
	
! the ifeq bytecode is immediately followed by a single signed 16-bit argument n which specifies
! the offset for the branch which should be taken if the comparison to zero succeeds:
!    Read the signed 16-bit argument n.
!    Pop the top element off the operand stack.
!    Branch to the offset if it is equal to zero, i.e. branch to the bytecode instruction that is at the
!    current instruction (the ifeq) plus the offset. Otherwise, drop through to the next bytecode
!    instruction in the bytecode stream.

ifeq:	
	ld	[%stkptr_r],%o0			! pop the top element off the operand stack
        inc     LEN, %stkptr_r

	tst	%o0				! branch to the offset if it is equal to zero
	be	ifeq_offset
	nop
	
	ba 	top				! back to the start
	inc	3,%pc_r				! [DELAY SLOT FILLED]
	
ifeq_offset:					! branching to the offset	
	ldsb	[%pc_r+1], %bc_arg1_r		! get the higher bits
	ldub	[%pc_r+2], %o0			! get the lower bits
	sll	%bc_arg1_r, 8, %bc_arg1_r
	or	%bc_arg1_r, %o0, %bc_arg1_r	! get the signed 16-bit argument

	ba	top				! back to the start
	add	%bc_arg1_r, %pc_r, %pc_r	! [DELAY SLOT FILLED]
	
! Branch to offset. offset is a signed 16-bit value.
goto:
	
	ldsb	[%pc_r+1], %bc_arg1_r		! get the higher bits
	ldub	[%pc_r+2], %o0			! get the lower bits
	sll	%bc_arg1_r, 8, %bc_arg1_r	
	or	%bc_arg1_r, %o0, %bc_arg1_r	! get the signed 16-bit argument
	
	ba	top
	add	%bc_arg1_r, %pc_r, %pc_r
!******************************************************************************************************
! Bonus Part 1
!******************************************************************************************************
! For our purposes, the iread instruction should read an integer from the keyboard using readInt, and
! should then push it onto the operand stack.

iread: 
	call	 readInt			! readInt from keyboard
	nop

        dec     LEN, %stkptr_r                   ! push the parameter onto the stack
        st      %o0, [%stkptr_r]


	ba	top				! back to the start
	inc	%pc_r				! [DELAY SLOT FILLED]

! Error: division by zero
 
div_error:
        set     div_by_zero, %o0
        call    printf                          ! print an error msg and exit
        nop
	mov	-1, %i0				! -1 if the byte code program resulted in a div by zero error
	ret
	restore

! exit the program

quit:
	mov	0, %i0				! to return 0 if the program executed successfully
	ret
        restore

