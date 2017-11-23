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
	ldr r0, =#2
	str r1, [sp, #-4]!
	mov r1, r0
	ldr r0, =#3
	str r1, [sp, #-4]!
	mov r1, r0
	ldr r0, =#4
	add r0, r1, r0
	ldr r1, [sp], #4
	mul r0, r1, r0
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
