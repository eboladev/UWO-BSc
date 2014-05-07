! *********************************************************************************************
!
! get_b64_char.m
! Assignment #3
! Author: Matthew Stokes
!
! Description:
!
! Function will take a 6-bit index value and return the appropriate base 64 character according
! to Figure 4 from the assignment description.
!
! Reigster Legend:
!
!	index_r			user entered value			%l0
!
! *********************************************************************************************
!
! *********************************************************************************************
! Constants
! *********************************************************************************************

define(CAPCHECKEND,25)		! Z at the end of cap letters in Ascii
define(CAPCHECKSTART,0)		! A at the beginning of cap letters in Ascii
define(LOWERLETTEREND,51)	! End of lower case letters
define(UPPERCASEOFFSET,65)	! number to convert from Ascii to Base64 for capital letters
define(LOWERCASEOFFSET,71)	! number to convert from Ascii to Base64 for lower case letters
define(NUMBEROFFSET,4)		! number to convert from Ascii to Base64 for numbers
! *********************************************************************************************
! Registers
! *********************************************************************************************

define(index_r, l0)
	
! *********************************************************************************************
! Main program begins
! ********************************************************************************************

	.global get_b64_char
get_b64_char:
	save	%sp,-96,%sp			! main program starts here

	mov	%i0,%index_r			! store argument

	subcc	%l0, LOWERLETTEREND, %g0	! checking if character is a letter
	bg	not_letter
	nop

	subcc	%l0, CAPCHECKEND, %g0		! checking if character is a capital letter
	ble	upper_case_letters
	nop

	add	LOWERCASEOFFSET, %l0, %i0	! Converting Base64 to Ascii Value
	ba	end	
	nop

not_letter:
	cmp 	%l0, 62				! checking for '+'
	be	plus_sign
	nop

	cmp	%l0, 63				! checking for '/'
	be	slash
	nop

	sub 	%l0, NUMBEROFFSET, %l0		! Converting Base64 to Ascii Value
	mov	%l0, %i0
	ba	end
	nop

plus_sign:
	mov	'+', %i0
	ba	end
	nop

slash:
	mov	'/', %i0
	ba	end
	nop
	
upper_case_letters:
	add	%l0, UPPERCASEOFFSET, %i0	! Converting Base64 to Ascii Value
	ba 	end
	nop

end:						! Ending ret & restore
	ret
	restore

