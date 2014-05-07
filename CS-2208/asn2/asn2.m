! *********************************************************************************************
!
! asn2.m
! Assignment #2
! Author: Matthew Stokes
!
! Description:
! Wage calculator in its upcoming model of watch. This feature will aloow the wearer to enter the
! number of hours he/she has worked, along with his/her hourly wage, and will display the total
! gross earnings that the wearer can expect.
!
! Reigster Legend:
!
!	t_r					number of minutes worked					%l0
!	m_r					cents earned per hour						%l1
!	conv_r					6000, conversion factor						%l5
!	value_r					minutes*dollars/hour						%l2
!
! *********************************************************************************************

! *********************************************************************************************
! Constants
! *********************************************************************************************

define(EOL, 10)				! ASCII code for newline character
define(TIME_PROMPT, 62)			! ASCII code for >
define(MONEY_PROMPT, 36)		! ASCII code for $
define(INTO_MINUTES, 60)		! 60 minutes in 1 hour
define(INTO_CENTS, 100)			! 100 cents in 1 dollars

! *********************************************************************************************
! Registers
! *********************************************************************************************

define(t_r, l0)
define(m_r, l1)
define(value_r, l2)
define(conv_r, l5)
	
! *********************************************************************************************
! Main program begins
! *********************************************************************************************
	
	.global main
main:
	save	%sp,-96,%sp									! main program starts here

	mov	600, %o0
	mov 	10, %o1
	call	.mul										! conversion factor
	nop
	mov	%o0, %conv_r									! store conversion factor
	
	call 	writeChar									! display promt for user
	mov 	TIME_PROMPT, %o0								! [DELAY SLOT]
	
	call	readInt										! read the hour number from user
	nop											! [DELAY SLOT]
	mov	INTO_MINUTES, %o1								! conversion value 
	call	.mul										! converted hours to minutes
	nop
	mov	%o0, %t_r									! store minutes
	
	call 	readChar									! reads :
	nop											! [DELAY SLOT]
	
	call	readInt										! read the minute number from user	
	nop											! [DELAY SLOT]
	add	%o0, %t_r, %t_r									! total minutes
	

	call	writeChar									! display promt for user
	mov 	MONEY_PROMPT, %o0								! [DELAY SLOT]
		
	call	readInt										! read dollars from user
	nop											! [DELAY SLOT]
	mov	INTO_CENTS, %o1									! conversion value 
	call	.mul										! converted dollars to cents
	nop
	mov	%o0, %m_r									! store cents
	
	
	call 	readChar									! reads .
	nop											! [DELAY SLOT]
	
	call	readInt										! read the cents number from user	
	nop											! [DELAY SLOT]
	add	%o0, %m_r, %m_r									! total cents/hour

	mov 	MONEY_PROMPT, %o0								! display prompt for user
	call	writeChar
	nop
	
	mov	%m_r, %o0									! get cents/hr
	mov	%t_r, %o1									! get minutes
	call	.mul
	nop
	mov	%o0, %value_r									! dollars*minute/hour


	mov	%conv_r, %o1									! get conversion factor
	call	.div										
	nop
	call	writeInt									! write dollar value for user
	nop

	mov	'.', %o0
	call	writeChar									! write decimal for user
	nop

	mov	%value_r, %o0									! get minutes*dollars/hour
	mov	%conv_r, %o1									! get conversion value
	call	.rem
	nop

	mov	600, %o1									! first decimal conversion digit
	call	.div
	nop
	call	writeInt									! write first number after decimal for user
	nop

	mov	%value_r, %o0
	mov	600, %o1									! first decimal conversion digit
	call	.rem
	nop
	mov	60, %o1										! second decimal conversion digit
	call	.div
	nop
	call	writeInt									! write second number after decimal for user
	nop

	mov 	EOL, %o0									
	call	writeChar									! write new line
	nop
	
	ret
	restore
