! *********************************************************************************************
!
! get_b64_char.m
! Assignment #3
! Author: Matthew Stokes
!
! Description:
!
! Reigster Legend:
!
!
! *********************************************************************************************

! *********************************************************************************************
! Constants
! *********************************************************************************************

define(EOL, 10) 			! Ascii value for end of line character

! *********************************************************************************************
! Registers
! *********************************************************************************************
define(combined_r, l0)
define(shift_char_counter_r, l1)
define(char_r, l2)


! *********************************************************************************************
! Main program begins
! ********************************************************************************************

         .global main
main:
        save    %sp,-96,%sp                     	! main program starts here

	mov 	0, %combined_r				! initialize combined
	mov	16, %shift_char_counter_r		! shift character counter
	mov	0, %char_r				! initializes characters_r

loop:
	call	getchar				
	nop

	cmp	%o0, -1					! check if letters remaining
	be	no_more_char
	
	sll	%o0, %shift_char_counter_r, %o0		! shift the character read to its in proper position
	or	%char_r, %o0, %char_r			! combine the shifted character with char_r
	sub	%shift_char_counter_r, 8, %shift_char_counter_r	! decriment shift character by 8 bits
	inc	%combined_r				! incriment combined
	
	cmp	%combined_r, 3				! if less then 3 characters stored in combined
	bl	loop					! jump to jop of loop
	nop
	
	mov	%char_r, %o0				! pass paramaters to get_b64_str
	mov	%combined_r, %o1
	call	get_b64_str
	nop
	
	call	printf					! displays to screen
	nop
	mov 	0, %combined_r				! set back to initialized value
	mov 	16, %shift_char_counter_r		! set back to initialized value
	mov	0, %char_r				! set back to initialized value
	ba	loop
	nop

no_more_char:
	cmp	%combined_r, 0				! if exactly a multiple of 3 ie no extra characters
	be	end
	nop

	mov	%char_r, %o0				! pass parameters to get_b64_str
	mov	%combined_r, %o1
	call 	get_b64_str
	nop

	call printf					! displays to screen
	nop	
end:
	mov	EOL, %o0
	call 	putchar					! print new line character
	nop

	ret
	restore
