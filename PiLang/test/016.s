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
z:
	@ prologue
	str r11, [sp, #-4]!
	mov r11, sp
	str r14, [sp, #-4]!
	str r1, [sp, #-4]!
	sub sp, sp, #0
	ldr r0, [r11, #4]
	cmp r0, #0
	beq L1
	ldr r0, [r11, #4]
	str r1, [sp, #-4]!
	mov r1, r0
	ldr r0, [r11, #4]
	str r1, [sp, #-4]!
	mov r1, r0
	ldr r0, =#1
	sub r0, r1, r0
	ldr r1, [sp], #4
	str r0, [sp, #-4]!
	bl z
	add sp, sp, #4
	add r0, r1, r0
	ldr r1, [sp], #4
	b L0
	b L2
L1:
	ldr r0, =#0
	b L0
L2:
	mov r0, #0
L0:
	@ epilogue
	add sp, sp, #0
	ldr r1, [sp], #4
	ldr r14, [sp], #4
	ldr r11, [sp], #4
	bx r14
main:
	@ prologue
	str r11, [sp, #-4]!
	mov r11, sp
	str r14, [sp, #-4]!
	str r1, [sp, #-4]!
	sub sp, sp, #0
	ldr r0, =#10
	str r0, [sp, #-4]!
	bl z
	add sp, sp, #4
	b L3
	mov r0, #0
L3:
	@ epilogue
	add sp, sp, #0
	ldr r1, [sp], #4
	ldr r14, [sp], #4
	ldr r11, [sp], #4
	bx r14
