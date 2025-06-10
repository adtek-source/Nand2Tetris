// This file is part of www.nand2tetris.org
// and the book "The Elements of Computing Systems"
// by Nisan and Schocken, MIT Press.
// File name: projects/4/Mult.asm

// Multiplies R0 and R1 and stores the result in R2.
// (R0, R1, R2 refer to RAM[0], RAM[1], and RAM[2], respectively.)
// The algorithm is based on repetitive addition.

@R1
D = M // D = n2
@n2
M = D

@R2
M = 0 // R2 = 0

(LOOP)
@n2
D = M // D = n2
@END
D ; JLE

    @R0
    D = M // D = n1
    @R2
    M = D+M // R2 = R2+n1

    @n2
    M = M-1 // n2 = n2-1

@LOOP
0 ; JMP

(END)
@END
0 ; JMP