! *********************************************************************************************
!
! asn4.m
! Assignment #4
! Author: Matthew Stokes
!
! Description:
! Bare bones of a main program for a Java Virtual Machine. Takes the filename of the .miniclass file as a command line parameter.
!
! Register Legend:
!
!	argc_r				number of arguments		%i0
!	argv_r				argument vector			%i1
!
! *********************************************************************************************
include(macro_defs.m)
! *********************************************************************************************
! Constants
! *********************************************************************************************
!
define(BYTE,1)                          ! size of a byte in bytes
define(SIZE,100)                        ! size of bytecode array (assumed)
define(NUMARG,2)			! size of arguments passed
define(ARGERROR,1)			! return value for argument error
define(READINGERROR,2)			! return value for reading error
define(DIVZEROERROR,3)			! return value for divided by zero error
! *********************************************************************************************
! Registers
! *********************************************************************************************

define(argc_r, i0)
define(arcv_r, i1)
define(load_r, l1)
! *********************************************************************************************
! Main program begins
! *********************************************************************************************

.data
errorArgs: .asciz "Incorrect number of arguments entererd"	! error message for too many or too few arguments
errorReading: .asciz "Cannot read input file"			! error message for reading input file

.text
local_var
var(byte_stream_s,BYTE,SIZE*BYTE)       ! array for storing bytecode stream

        begin_main

	cmp 	%i0, 2			! testing the number of arguments passed
	bne	error_message_1
	nop
	
	ld	[%i1+4], %o0
        add     %fp,byte_stream_s,%o1   ! pass address of bytecode array
	
	call    get_codes               ! read bytecodes from file into array
        nop

	tst	%o0			! testing for an error in reading the file
	bneg	error_message_2
	nop

	
        mov     %o0,%o1                 ! pass number of bytes in bytecode stream
        add     %fp,byte_stream_s,%o0   ! pass address of bytecode array
        call    execute_codes           ! execute instructions in bytecode
        nop

	tst	%o0			! testing for a division by 0 error
	bneg	error_message_3
	nop

	clr	%i0			! success!!!!!!
	ba	exit			
	nop

error_message_1:
	set 	errorArgs, %o0		! pass error message
	call	puts			! print error message
	nop
	mov	ARGERROR, %i0		! pass error return value
	ba	exit
	nop
	
error_message_2:
	set	errorReading, %o0	! pass error message
	call	puts			! print error message
	nop
	mov	READINGERROR, %i0	! pass error return value
	ba	exit
	nop
	
error_message_3:
	mov	DIVZEROERROR, %i0	! pass error return value

exit:	
        ret                             ! exit
        restore

