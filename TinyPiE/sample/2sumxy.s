    .section .data
    @ 大域変数の定義
_Pi_var_x:
    .word 1
_Pi_var_y:
    .word 10
_Pi_var_z:
    .word -1
    .section .text
    .global _start
_start:
    @ 式をコンパイルした命令列
    ldr r0, =#2
    str r1, [sp, #-4]!
    mov r1, r0
    ldr r0, =_Pi_var_x
    ldr r0, [r0, #0]
    str r1, [sp, #-4]!
    mov r1, r0
    ldr r0, =_Pi_var_y
    ldr r0, [r0, #0]
    add r0, r1, r0
    ldr r1, [sp], #4
    mul r0, r1, r0
    ldr r1, [sp], #4
    @ EXITシステムコールを発行
    mov r7, #1
    swi #0
