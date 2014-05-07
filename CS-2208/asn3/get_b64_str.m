! *********************************************************************************************
!
! get_b64_str.m
! Assignment #3
! Author: Matthew Stokes
!
! Description:
!
! 
!
! Reigster Legend:
!	out_str_r		5 byte static variable 4 encoded characters & 1 null	%l0
!	n0_r			bits 23 to 18 of the passed value			%l2
!	n1_r			bits 17 to 12 of the passed value			%l3
!	n2_r			bits 11 to 6 of the passed value			%l4
!	n3_r			bits 5 to 0 of the passed value				%l5
!	
!       
!
! *********************************************************************************************

! *********************************************************************************************
! Constants
! *********************************************************************************************

                
! *********************************************************************************************
! Registers
! *********************************************************************************************

define(out_str_r, l0)
define(n0_r, l2)
define(n1_r, l3)
define(n2_r, l4)
define(n3_r, l5)

! *********************************************************************************************
! Main program begins
! ********************************************************************************************

		.data
out_str:	.byte 0, 0, 0, 0, 0		! 5 byte static variable

		.text
		.global get_b64_str
get_b64_str:	 
		save %sp , -96, %sp

		set 	out_str, %out_str_r	! set address of out_str into register

		mov	'=', %l1		! Store '=' to bytes 2 and 3 of out_str_r
		stb	%l1, [%l0+2]
		stb	%l1, [%l0+3]
		
		srl	%i0, 18, %n0_r		! n0 = bits 23 to 18 of the passed value

		sll	%i0, 14, %n1_r		! n1 = bits 17 to 12 of the passed value
		srl	%n1_r, 26, %n1_r

		sll	%i0, 20, %n2_r		! n2 = bits 11 to 6 of the passed value
		srl	%n2_r, 26, %n2_r

		sll	%i0, 26, %n3_r		! n3 = bits 5 to 0 of the passed value
		srl	%n3_r, 26, %n3_r

		mov	%n0_r, %o0		
		call 	get_b64_char		! c0
		nop
		stb	%o0, [%l0]		! store c0 at byte 0
		
		mov 	%n1_r, %o0	
		call	get_b64_char		! c1
		nop
		stb	%o0, [%l0+1]		! store c1 at byte 1

		cmp	%i1, 2			! check if 2 or 3 characters are encoded
		bl	end
		nop

		mov	%n2_r, %o0		
		call	get_b64_char		! c2
		nop
		stb 	%o0, [%l0+2]		! store c2 at byte 2
		
		cmp	%i1, 3			! check if 3 characters are encoded
		bl 	end
		nop

		mov	%n3_r, %o0
		call 	get_b64_char		! c3
		nop
		stb 	%o0, [%l0+3]		! store c3 at byte 3

end:
		mov	%out_str_r, %i0		! return the address of out_str
		ret
		restore

