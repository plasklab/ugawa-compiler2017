	.section .data
	@ 大域変数の定義
	.section .text
	.global _start
_start:
	@ main関数を呼出す．戻り値は r0 に入る
	bl main
	@ EXITシステムコール
	mov r7, #1
	swi #0
main:
	@ prologue
	str r11, [sp, #-4]!
	mov r11, sp
	str r14, [sp, #-4]!
	str r1, [sp, #-4]!
	sub sp, sp, #0
	ldr r0, =#5
	str r1, [sp, #-4]!
	mov r1, r0
	ldr r0, =#7
	and r0, r1, r0
	ldr r1, [sp], #4
	b L0
	mov r0, #0
L0:
	@ epilogue
	add sp, sp, #0
	ldr r1, [sp], #4
	ldr r14, [sp], #4
	ldr r11, [sp], #4
	bx r14
_print_r0:
	str r0, [sp, #-4]!
	str r1, [sp, #-4]!
	str r2, [sp, #-4]!
	str r3, [sp, #-4]!
	str r4, [sp, #-4]!
	str r5, [sp, #-4]!
	str r6, [sp, #-4]!
	str r7, [sp, #-4]!
	ldr r1, =_print_buf
	
	add r1, #_print_buflen
	sub r1, #1
	mov r5, #2
_print_loop:
	sub r1, r1, #1
	add r5, r5, #1
	mov r2, #10
	sdiv r3, r0, r2
	mul r4, r2, r3
	sub r2, r0, r4
	@ r0 / 10 = r3 ... r2
	add r4, r2, #'0'
	strb r4, [r1]
	mov r0, r3
	cmp r0, #0
	bne _print_loop
	mov   r7, #4
	mov   r0, #1
	mov   r2, r5
	swi   #0
	ldr r7, [sp], #4
	ldr r6, [sp], #4
	ldr r5, [sp], #4
	ldr r4, [sp], #4
	ldr r3, [sp], #4
	ldr r2, [sp], #4
	ldr r1, [sp], #4
	ldr r0, [sp], #4
	bx r14
	.section .data
_print_buf:
	.space 255
	.equ _print_buflen, . - _print_buf
	.ascii "\n"
