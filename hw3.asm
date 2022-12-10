######### DANIEL KOGAN ##########

######## DO NOT ADD A DATA SECTION ##########
######## DO NOT ADD A DATA SECTION ##########
######## DO NOT ADD A DATA SECTION ##########

.text
.globl initialize
initialize:
 # TODO: HANDLE DOUBLE SPACES & CHECK IF COL=[1,10] AND ROW=[1,10]
 # inputs: a0=filename, a1=buffer
 # registers changed: a0, a1, a2, t0, t1, t2, t3
 li $v0, 13 # open file
 move $t0, $a1 # move buffer to t0
 li $a1, 0 # open file for reading
 li $a2, 0 # idk the thingy just says it should be 0
 syscall
 bltz $v0, end_error_init # error on open
 # file has been opened successfully lets goooo
 # read file
 move $a0, $v0 # no longer need file name - just pointer
 move $t9, $t0

 # IMPLEMENTATION GOALS
 # #1 READ 1 CHAR TO THE STACK
 # #2 CHECK IF NEWLINE OR INVALID
 # #3 CONVERT TO INT
 # #4 sw int 0(buffer)
 # COMPLETE

 li $t1, 0 # holder for sw buffer int
 move $t4, $t0 # hold buffer addy
 li $t5, 2 # counter to check if rows / cols bigger than 
 li $t6, 0 # count how many spaces
 # t7 track ints
 li $t7, 0
 while_loop_initialize:
 addi $sp, $sp, -4
 move $a1, $sp # buffer as stack pointer
 li $a2, 1 # 1 chars for ex
 li $v0, 14 # read a file
 syscall
 lbu $t3, 0($sp) # bestie reads by bytes
 addi $sp, $sp, 4
 bltz $v0, error_initialize # error on read
 beqz $v0, end_initialize # end loop when done reading
 
 # white space
 li $t2, '\r' # windows is dumb and lame 
 beq $t2, $t3, while_loop_initialize
 li $t2, 0x20 # space
 beq $t2, $t3, save_int_to_buffer
 li $t2, 10 # \n
 beq $t2, $t3, save_int_to_buffer
 
 # char validity
 li $t2, '0' # '0'
 blt $t3, $t2, error_initialize # invalid char - less than '0'
 li $t2, '9'
 bgt $t3, $t2, error_initialize # invalid char - greater than '9'
 j read_char_to_int

 # add char to t1
  save_int_to_buffer:
   addi $t7, $t7, 1
   bnez $t6, error_initialize
   addi $t6, $t6, 1
   beqz $t5, skip_handle_first_two
   addi $t5, $t5, -1
   li $t2, 10
   bgt $t1, $t2, error_initialize
   li $t2, 1
   blt $t1, $t2, error_initialize
   skip_handle_first_two:
   sw $t1, 0($t0)
   addi $t0, $t0, 4
   li $t1, 0 # reset buffer int
   j while_loop_initialize
  read_char_to_int:
   li $t6, 0
   li $t2, 10
   mul $t1, $t2, $t1 # *= 10
   addi $t3, $t3, -48 # char to int
   add $t1, $t3, $t1 # add char to saved int
   j while_loop_initialize

 error_initialize:
 # set buffer to 0
 error_init_loop_clear_buffer:
  bge $t4, $t0, end_error_init
  sw $0, 0($t4)
  addi $t4, $t4, 4
  j error_init_loop_clear_buffer
 end_error_init:
 li $v0, 16
 syscall
 li $v0, -1
 jr $ra
 end_initialize:
  lw $t1 0($t9)
  lw $t2, 4($t9)
  mul $t6, $t1, $t2
  addi $t6, $t6, 2
  bne $t7, $t6, error_initialize

 li $v0, 16
 syscall
 li $v0, 1
 jr $ra

.globl write_file
write_file:
  # inputs: a0=filename, a1=buffer
  # registers changed: a0, a1, a2, t0, t1, t2, t3, t4, t5, t7, t8, t9
  li $v0, 13 # open file
  move $t0, $a1 # move buffer to t0
  li $a1, 1 # open file for writing
  li $a2, 0 # idk the thingy just says it should be 0
  syscall
  bltz $v0, error_write_file # error on open
  # file has been opened successfully lets goooo
  # write file
  move $a0, $v0 # no longer need file name - just pointer
  li $t7, 0 # char counter

  li $t9, 0 # row
  li $t8, 0 # col
  # rows / columns

  # row
  lw $t1, 0($t0)
  li $t2, 10 # temp var holds 10
  bgt $t1, $t2, error_write_file
  addi $sp, $sp, -4
  move $t9, $t1 # move to row
  beq $t1, $t2, is_10_row_write_file
  ##############################################
  addi $t1, $t1, 48
  sw $t1, 0($sp)
  move $a1, $sp # buffer as stack pointer
  li $a2, 1 # write 1 chars
  li $v0, 15
  syscall
  addi $t7, $t7, 1
  #################################################
  j row_skip_is_10_write_file
  is_10_row_write_file:
    #move $a1, $sp # buffer as stack pointer
    li $t1, '1'
    sw $t1, 0($sp)
    move $a1, $sp # buffer as stack pointer
    li $a2, 1 # write 1 chars
    li $v0, 15
    syscall
    li $t1, '0'
    sw $t1, 0($sp)
    move $a1, $sp # buffer as stack pointer
    li $a2, 1 # write 1 chars
    li $v0, 15
    syscall
    addi $t7, $t7, 2 # increment char written
  row_skip_is_10_write_file:
  # print newline
  li $t1, '\n'
  sw $t1, 0($sp)
  move $a1, $sp # buffer as stack pointer
  li $a2, 1 # write 1 chars
  li $v0, 15
  syscall
  addi $t7, $t7, 1
  addi $sp, $sp, 4
  addi $t0, $t0, 4

  # col
  lw $t1, 0($t0)
  move $t8, $t1 # move to col
  li $t2, 10 # temp var holds 10
  bgt $t1, $t2, error_write_file
  addi $sp, $sp, -4
  beq $t1, $t2, is_10_col_write_file
  addi $t1, $t1, 48
  sw $t1, 0($sp)
  move $a1, $sp # buffer as stack pointer
  li $a2, 1 # write 1 chars
  li $v0, 15
  syscall
  addi $t7, $t7, 1
  j col_skip_is_10_write_file
  is_10_col_write_file:
    li $t1, '1'
    sw $t1, 0($sp)
    move $a1, $sp # buffer as stack pointer
    li $a2, 1 # write 1 chars
    li $v0, 15
    syscall
    li $t1, '0'
    sw $t1, 0($sp)
    move $a1, $sp # buffer as stack pointer
    li $a2, 1 # write 1 chars
    li $v0, 15
    syscall
    addi $t7, $t7, 2 # increment char written
  col_skip_is_10_write_file:
  addi $t0, $t0, 4
  # print newline
  li $t1, '\n'
  sw $t1, 0($sp)
  move $a1, $sp # buffer as stack pointer
  li $a2, 1 # write 1 chars
  li $v0, 15
  syscall
  addi $sp, $sp, 4
  addi $t7, $t7, 1 # increment char written

  # start loop
  move $t4, $t8 # col loop
  write_file_loop:
    beqz $t4, write_start_new_line
    beqz $t9, complete_write_file
    addi $sp, $sp, -4
    lw $t1, 0($t0)
    # t2 will be checker
    li $t2, 10
    blt $t1, $t2, skip_handle_big_int
    # handle ints bigger than 9 right here
    # need temp int to hold value to write
    # track digits
    # t5, counter
    li $t5, 0
    write_file_big_int_loop:
    div $t1, $t2
    mfhi $t3
    addi $sp, $sp, -4
    addi $t3, $t3, 48
    addi $t5, $t5, 1
    sw $t3, 0($sp)
    mflo $t1
    beqz $t1, write_bigint_chars
    j write_file_big_int_loop
 
 write_bigint_chars:
  beqz $t5, add_space_for_write_loop
  addi $t5, $t5, -1
  move $a1, $sp # buffer as stack pointer
  li $a2, 1 # write 1 chars
  li $v0, 15
  syscall
  addi $t7, $t7, 1 # increment char written
  addi $sp, $sp, 4
  j write_bigint_chars
 
 skip_handle_big_int:
  addi $t1, $t1, 48
  sw $t1, 0($sp)
  move $a1, $sp # buffer as stack pointer
  li $a2, 1 # write 1 chars
  li $v0, 15
  syscall
  addi $t7, $t7, 1 # increment char written
  # space
 add_space_for_write_loop:
  li $t1, 1
  beq $t4, $t1, continue_write_loop
  li $t1, ' '
  sw $t1, 0($sp)
  move $a1, $sp # buffer as stack pointer
  li $a2, 1 # write 1 chars
  li $v0, 15
  syscall
  addi $t7, $t7, 1 # increment char written
 
 # loop
 continue_write_loop:
  addi $sp, $sp, 4
  addi $t0, $t0, 4
  addi $t4, $t4, -1
  j write_file_loop
 
 write_start_new_line:
  addi $t9, $t9, -1
  move $t4, $t8
  addi $sp, $sp, -4
  # print newline
  li $t1, '\n'
  sw $t1, 0($sp)
  move $a1, $sp # buffer as stack pointer
  li $a2, 1 # write 1 chars
  li $v0, 15
  syscall
  addi $t7, $t7, 1 # increment char written
  addi $sp, $sp, 4
  j write_file_loop

complete_write_file:
    li $v0, 16
    syscall
    move $v0, $t7
    jr $ra
  error_write_file:
    li $v0, 16
    syscall
    li $v0, -1
    jr $ra

.globl rotate_clkws_90
rotate_clkws_90:
 # inputs: a0=buffer, a1=filename
 # registers changed: a0, a1, t0, t1, t2, t3, t4, t5, t6, t7, t8
  
 # for write file ##########
 addi $sp, $sp, -8
 sw $a0, 0($sp)
 sw $a1, 4($sp)
 ############################
 
 lw $t0, 0($a0)
 lw $t1, 4($a0)
 # number of elements in matrix
 mul $t2, $t0, $t1
 addi $t2, $t2, 2 # row/col
 li $t3, -4
 mul $t2, $t2, $t3 # word is 4
 add $sp, $sp, $t2 # make space in the stack for everyone :)
 sw $t1, 0($sp)
 sw $t0, 4($sp)
 move $t3, $t0 # t3 track cols
 move $t0, $sp
 addi $t0, $t0, 8 # t0 can jump around locations in the stack, sp will save location
 
 # loop to go thru rest of buffer
 # implementation:
 # - jump from current item location in buffer to expected in stack
 
 # t1, rows
 # t3, cols
 # t0, track sp, new buffer
 # t4, track a0, curr buffer
 move $t4, $a0
 # t2, size of sp, -(rows*cols + 2)*4
 # t5, beq counter
 # t6, running total, counter
 # t7, find location in t4
 # t8, temp
 # the rows go in order
 li $t6, 1
 flip_90_rows:
  # num cols * (num rows -1)
  addi $t7, $t3, -1 # rows - 1 | 2 | 1
    addi $t3, $t3, -1 # | 1 | 0
  mul $t7, $t7, $t1 # * col | 6 | 3
  add $t7, $t7, $t6  # + run_total | 7
  addi $t7, $t7, 1 # indexed at 0 | 6
  li $t8, 4 # temp var
  mul $t7, $t7, $t8 # location in memory is in units of 4
  add $t4, $t4, $t7 # a0
  lw $t8, 0($t4) # t8 | 7
  sub $t4, $t4, $t7
  sw $t8, 0($t0) # sp
  addi $t0, $t0, 4
  beqz $t3, rotate_90_reset_row_subtract_column # fix later
  j flip_90_rows
  rotate_90_reset_row_subtract_column:
   lw $t3, 4($sp)
   addi $t6, $t6, 1
   bgt $t6, $t1, write_file_rotate_90
   j flip_90_rows
 write_file_rotate_90:
  # write file
  li $t7, -1
  li $t6, 0 # counter
  mul $t7, $t2, $t7 # tracks all values
  move $t2, $a0
  write_file_loop_rotate_90:
   lw $t8, 0($sp)
   sw $t8, 0($t2)
   addi $sp, $sp, 4
   addi $t2, $t2, 4
   addi $t6, $t6, 4
   beq $t6, $t7, call_write_file_rotate_90
   j write_file_loop_rotate_90
   call_write_file_rotate_90:
   #move $t0, $a1
   #move $a1, $a0
   #move $a1, $t0
   lw $a0, 4($sp)
   lw $a1, 0($sp)
   addi $sp, $sp, 8
   # a0 = filename, a1 = buffer
   addi $sp, $sp, -4
   sw $ra, 0($sp)
   jal write_file
   lw $ra, 0($sp)
   addi $sp, $sp, 4
   bgtz $v0, rotate_90_set_v0_to_1
   li $v0, -1
   jr $ra
   rotate_90_set_v0_to_1:
   li $v0, 1
   jr $ra

.globl rotate_clkws_180
rotate_clkws_180:
 # inputs: a0=buffer, a1=filename
 # registers changed: a0, a1, t0, t1, t2, t3, t4, t5, t6, t7, t8
 # for write file ##########
 sw $a0, 0($sp)
 sw $a1, 4($sp)
 addi $sp, $sp, 8
 move $t5, $sp
 ############################
 _180_starting_line:
 lw $t1, 0($a0)
 lw $t0, 4($a0)
 # number of elements in matrix
 mul $t2, $t0, $t1
 # track half elements
 #li $t5, 2
 #div $t2, $t5
 #mflo $t5
 # track size
 #addi $t2, $t2, 2 # row/col
 li $t3, 4
 mul $t2, $t2, $t3 # word is 4
 # add $sp, $sp, $t2 # make space in the stack for everyone :)
 sw $t1, 0($sp)
 sw $t0, 4($sp)
 addi $sp, $sp, 4
 move $t3, $t0 # t3 track cols
 add $sp, $sp, $t2 # t0 can jump around locations in the stack, sp will save location
 move $t0, $sp

 # loop to go thru rest of buffer
 # implementation:
 # - jump from current item location in buffer to expected in stack
 
 # t1, rows
 # t3, cols
 # t0, track sp, new buffer
 # t4, track a0, curr buffer
 move $t4, $a0
 addi $t4, $t4, 8
 # t2, size of sp, -(rows*cols + 2)*4
 # t5, beq counter
 # t6, running total, counter
 # t7, find location in t4
 # t8, temp
 # the rows go in order
 li $t6, 0
 flip_180_rows:
  beq $t6, $t2, write_to_buffer_rotate_180
  lw $t7, 0($t4)
  sw $t7, 0($t0)
  addi $t0, $t0, -4
  addi $t4, $t4, 4
  addi $t6, $t6, 4
  j flip_180_rows
 write_to_buffer_rotate_180:
  # rewrite to buffer
  addi $t2, $t2, 4
  sub $sp, $sp, $t2
  move $a2, $t2
  move $a1, $sp
  move $t6, $a1
  move $t4, $a0
start_180_buffer_loop:
 beqz $a2, _180end_write_to_buffer
 lw $t9, 0($t6)
 sw $t9, 0($t4)
 addi $t6, $t6, 4
 addi $t4, $t4, 4
 addi $a2, $a2, -4
 j start_180_buffer_loop
 _180end_write_to_buffer:
  # addi $sp, $sp, -12
  # sw $ra, 0($sp)
  # jal write_to_buffer
  # lw $ra, 0($sp)
  # addi $sp, $sp, 12
  # back to normal
  li $t6, -1
  mul $t2, $t2, $t6
  addi $sp, $sp, -8 # make space in the stack for everyone :)
  # lw $a0, 0($sp)
  # lw $a1, 4($sp)
 
 write_file_rotate_180:
  # write file
  li $t7, -1
  li $t6, 0 # counter
  mul $t7, $t2, $t7 # tracks all values
  move $t2, $a0
  lw $t0, 0($sp)
  lw $t3, 4($sp)
  sw $t3, 0($sp)
  sw $t0, 4($sp)
  write_file_loop_rotate_180:
   lw $t8, 0($sp)
   sw $t8, 0($t2)
   addi $sp, $sp, 4
   addi $t2, $t2, 4
   addi $t6, $t6, 4
   beq $t6, $t7, call_write_file_rotate_180
   j write_file_loop_rotate_180
   call_write_file_rotate_180:
   move $sp, $t5
   addi $sp, $sp, -8
   lw $a0, 4($sp)
   lw $a1, 0($sp)
   addi $sp, $sp, 8
  #  addi $a0, $a0, 4
   # a0 = filename, a1 = buffer
   addi $sp, $sp, -4
   sw $ra, 0($sp)
   jal write_file
   lw $ra, 0($sp)
   addi $sp, $sp, 4
   bgtz $v0, rotate_180_set_v0_to_1
   li $v0, -1
   jr $ra
   rotate_180_set_v0_to_1:
   li $v0, 1
   jr $ra

.globl rotate_clkws_270
rotate_clkws_270:
 # inputs: a0=buffer, a1=filename
 # registers changed: a0, a1, t0, t1, t2, t3, t4, t5, t6, t7, t8
 
 # for write file ##########
 addi $sp, $sp, -8
 sw $a0, 0($sp)
 sw $a1, 4($sp)
 ############################
 li $t5, 3
 _270_starting_line:
 addi $t5,$t5, -1
 lw $t0, 0($a0)
 lw $t1, 4($a0)
 # number of elements in matrix
 mul $t2, $t0, $t1
 # track half elements
 #li $t5, 2
 #div $t2, $t5
 #mflo $t5
 # track size
 addi $t2, $t2, 2 # row/col
 li $t3, -4
 mul $t2, $t2, $t3 # word is 4
 add $sp, $sp, $t2 # make space in the stack for everyone :)
 sw $t1, 0($sp)
 sw $t0, 4($sp)
 move $t3, $t0 # t3 track cols
 move $t0, $sp
 addi $t0, $t0, 8 # t0 can jump around locations in the stack, sp will save location
 
 # loop to go thru rest of buffer
 # implementation:
 # - jump from current item location in buffer to expected in stack
 
 # t1, rows
 # t3, cols
 # t0, track sp, new buffer
 # t4, track a0, curr buffer
 move $t4, $a0
 # t2, size of sp, -(rows*cols + 2)*4
 # t5, beq counter
 # t6, running total, counter
 # t7, find location in t4
 # t8, temp
 # the rows go in order
 li $t6, 1
 bltz $t5, write_file_rotate_270
 flip_270_rows:
  # num cols * (num rows -1)
  addi $t7, $t3, -1 # rows - 1 | 2 | 1
    addi $t3, $t3, -1 # | 1 | 0
  mul $t7, $t7, $t1 # * col | 6 | 3
  add $t7, $t7, $t6  # + run_total | 7
  addi $t7, $t7, 1 # indexed at 0 | 6
  li $t8, 4 # temp var
  mul $t7, $t7, $t8 # location in memory is in units of 4
  add $t4, $t4, $t7 # a0
  lw $t8, 0($t4) # t8 | 7
  sub $t4, $t4, $t7
  sw $t8, 0($t0) # sp
  addi $t0, $t0, 4
  beqz $t3, rotate_270_reset_row_subtract_column # fix later
  j flip_270_rows
  rotate_270_reset_row_subtract_column:
   lw $t3, 4($sp)
   addi $t6, $t6, 1
   bgt $t6, $t1, before_270_starting_line
   j flip_270_rows
 before_270_starting_line:
  # rewrite to buffer
  move $a2, $t2
  move $a1, $sp
  addi $sp, $sp, -4
  sw $ra, 0($sp)
  jal write_to_buffer
  lw $ra, 0($sp)
  addi $sp, $sp, 4
  # back to normal
  li $t6, -1
  mul $t2, $t2, $t6
  add $sp, $sp, $t2 # make space in the stack for everyone :)
  lw $a0, 0($sp)
  lw $a1, 4($sp)
  j _270_starting_line
 
 write_file_rotate_270:
  # write file
  li $t7, -1
  li $t6, 0 # counter
  mul $t7, $t2, $t7 # tracks all values
  move $t2, $a0
  lw $t0, 0($sp)
  lw $t3, 4($sp)
  sw $t3, 0($sp)
  sw $t0, 4($sp)
  write_file_loop_rotate_270:
   lw $t8, 0($sp)
   sw $t8, 0($t2)
   addi $sp, $sp, 4
   addi $t2, $t2, 4
   addi $t6, $t6, 4
   beq $t6, $t7, call_write_file_rotate_270
   j write_file_loop_rotate_270
   call_write_file_rotate_270:
   lw $a0, 4($sp)
   lw $a1, 0($sp)
   addi $sp, $sp, 8
   # a0 = filename, a1 = buffer
   addi $sp, $sp, -4
   sw $ra, 0($sp)
   jal write_file
   lw $ra, 0($sp)
   addi $sp, $sp, 4
   bgtz $v0, rotate_270_set_v0_to_1
   li $v0, -1
   jr $ra
   rotate_270_set_v0_to_1:
   li $v0, 1
   jr $ra

.globl mirror
mirror:
 # inputs: a0=buffer, a1=filename
 # registers changed: a0, a1, t0, t1, t2, t3, t4, t5, t6, t7, t8
 
 # for write file ##########
 addi $sp, $sp, -8
 sw $a0, 0($sp)
 sw $a1, 4($sp)
 ############################
 
 lw $t0, 4($a0)
 lw $t1, 0($a0)
 # number of elements in matrix
 mul $t2, $t0, $t1
 addi $t2, $t2, 2 # row/col
 li $t3, -8
 mul $t2, $t2, $t3 # word is 4
 add $sp, $sp, $t2 # make space in the stack for everyone :)
 sw $t1, 0($sp)
 sw $t0, 4($sp)
 move $t3, $t0 # t3 track cols
 move $t0, $sp
 addi $t0, $t0, 8 # t0 can jump around locations in the stack, sp will save location
 
 # loop to go thru rest of buffer
 # implementation:
 # - jump from current item location in buffer to expected in stack
 
 # t1, rows
 # t3, cols
 # t0, track sp, new buffer
 # t4, track a0, curr buffer
 addi $t4, $a0, 4
 # t2, size of sp, -(rows*cols + 2)*4
 # t5, track total cols
 # t6, running total, counter
 # t7, find location in t4
 # t8, temp
 # the rows go in order
 li $t6, 0
 move $t5, $t3
 loop_mirror_row:
  # num cols * (num rows -1)
  # business logic: determine where to find next elem
  #beq $t6, $t1, end_mirror_reset_row
  beqz $t3, end_mirror_reset_row
  mul $t7, $t6, $t5 # current row * total cols | 1 * 3 + curr col
  add $t7, $t7, $t3 # row# + col#
  addi $t3, $t3, -1
  li $t8, 4 # temp var
  mul $t7, $t7, $t8 # location in memory is in units of 4
  add $t4, $t4, $t7 # a0
  lw $t8, 0($t4) # t8 | 7
  sub $t4, $t4, $t7
  sw $t8, 0($t0) # sp
  addi $t0, $t0, 4
  j loop_mirror_row
  end_mirror_reset_row:
   lw $t3, 4($sp)
   addi $t6, $t6, 1
   beq $t6, $t1, mirror_buffer_write_file # if curr_row = rows end
   j loop_mirror_row
 
  mirror_buffer_write_file:
   move $a2, $t2
   move $a1, $sp
   #addi $sp, $sp, -12 # save stuff to stack before ra
   sw $ra, 400($sp)
    # doneskis
    sub $sp, $sp, $t2 # undo restore stack from function
   jal write_to_buffer
       add $sp, $sp, $t2 # undo restore stack from function
   lw $ra, 400($sp)
       sub $sp, $sp, $t2 # undo restore stack from function
   #addi $sp, $sp, 12 # restore stack from write to buffer
  call_write_file_mirror_buffer:
   # get args from stack
   lw $a0, 4($sp)
   lw $a1, 0($sp)
   addi $sp, $sp, 8 # back to starting value before mirror loop
   # reset stack
   # a0 = filename, a1 = buffer
   
   #addi $sp, $sp, -12
   sw $ra, 400($sp)
   #addi $s0, $sp, 12
   jal write_file
   #addi $s0, $sp, -12
   lw $ra, 400($sp)
   #addi $s0, $sp, 12
   
   bgtz $v0, mirror_buffer_set_v0_to_1
   li $v0, -1
   jr $ra
   mirror_buffer_set_v0_to_1:
   li $v0, 1
   jr $ra

.globl duplicate
duplicate:
 # inputs: a0=buffer
 # implementation details
 # 1. read file to buffer // does not take in file //
 # 2. loop thru buffer. sll every value in one row and save it to the stack
 # 3. loop thru all in stack. compare to every other elem in stack
 # 4. ignore current, beq if two different registers are same
 move $t0, $a0 # keep original buffer val
 lw $t1, 0($t0) # row
 lw $t2, 4($t0) # col
 addi $t0, $t0, 8
 move $t9, $sp
 li $t4, 4
 mul $t4, $t1, $t4
 sub $sp, $sp $t4
 li $t4, 0
 duplicate_stack_sll_loop:
  beqz $t2, reset_col_stack_duplicate_loop
  lw $t3, 0($t0)
  add $t4, $t4, $t3
  sll $t4, $t4, 1
  addi $t2, $t2, -1
  addi $t0, $t0, 4
  j duplicate_stack_sll_loop
 reset_col_stack_duplicate_loop:
  addi $t1, $t1, -1
  lw $t2, 4($a0) # col
  addi $sp, $sp, 4
  sw $t4, 0($sp)
  li $t4, 0
  beqz $t1, end_duplicate_sll_loop
  j duplicate_stack_sll_loop
 end_duplicate_sll_loop:
  lw $t1, 0($a0) # i=row
  lw $t6, 0($a0) # hold row #
  li $t4, 4
  mul $t4, $t6, $t4
  sub $t7, $sp $t4
  addi $t7, $t7, 4
  move $t8, $t7
  #move $t7, $sp
  li $t3, -1 # counter
  # current first dup
  for_loop_for_checking_items_against:
   # pick item
   lw $t4 0($t8)
   #move $t3, $sp # track location
   beqz $t1, end_return_minus_one
   addi $t1, $t1, -1
   # for every other item in stack
   lw $t2, 0($a0) # j=row
   for_loop_items_checked_against:
    addi $t2, $t2, -1 
    lw $t5 0($t7)
    blt $t7, $t8, skip_item_is_equal # cannot check previous rows, only rows after this
    beq $t5, $t4, check_item_equality
    j skip_item_is_equal
      check_item_equality:
      beq $t8, $t7, skip_item_is_equal
      sub $v0, $t6, $t2 # first dup row
      bltz $t3,if_t3_is_still_neg
      bgt $v0, $t3, skip_item_is_equal
      if_t3_is_still_neg:
      move $t3, $v0
    skip_item_is_equal:
    addi $t7, $t7, 4
    beqz $t2,end_for_checked_against
    j for_loop_items_checked_against
   end_for_checked_against:
    addi $t8, $t8, 4
    li $t4, 4
    mul $t4, $t6, $t4
    sub $t7, $sp $t4
    addi $t7, $t7, 4
    j for_loop_for_checking_items_against
    end_return_minus_one:
     move $sp, $t9
     bltz $t3, skip_return_minus_one
      move $v1, $t3
      li $v0, 1 # found a dupl
      jr $ra
    skip_return_minus_one:
     li $v0, -1
     li $v1, 0
     jr $ra

.globl write_to_buffer
write_to_buffer:
 # inputs a0=buffer_to_write a1=buffer_to_read a2=how_much_to_read
 move $t0, $a2
 add $sp, $sp, $t0
 writing_to_buffer:
 beqz $t0, end_write_to_buffer
 lw $t9, 0($a1)
 sw $t9, 0($a0)
 addi $a1, $a1, 4
 addi $a0, $a0, 4
 addi $t0, $t0, 4
 j writing_to_buffer
 end_write_to_buffer:
 li $t0, -1
 mul $t0, $a2, $t0
 add $sp, $sp, $t0
 jr $ra
