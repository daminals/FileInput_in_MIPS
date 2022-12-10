# This is a test file. Use this file to run the functions in hw3.asm
#
# Change data section as you deem fit.
# Change filepath if necessary.
.data
Filename: .asciiz "inputs/input1.txt"
OutFile: .asciiz "outputs/out.txt"
Buffer:
    .word 10	# num rows
    .word 10	# num columns
    # matrix
    .word 12 3 24 45 6 17 7 8 9 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0

.text
main:
 la $a0, Filename
 la $a1, Buffer
 jal initialize
 # write additional test code as you deem fit.
 #la $a1, Buffer
 la $a0, OutFile
 la $a0, Buffer
 #jal write_file
 jal rotate_clkws_180

 li $v0, 10
 syscall

.include "hw3.asm"
