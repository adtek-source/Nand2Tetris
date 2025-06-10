// This file is part of www.nand2tetris.org
// and the book "The Elements of Computing Systems"
// by Nisan and Schocken, MIT Press.
// File name: projects/4/Fill.asm

// Runs an infinite loop that listens to the keyboard input. 
// When a key is pressed (any key), the program blackens the screen,
// i.e. writes "black" in every pixel. When no key is pressed, 
// the screen should be cleared.

(END)
@row
M = 0 // row = 0

    (LOOP)
    @row
    D = M // D = row
    @8192
    D = D-A // D = row-8192
    @END
    D ; JGE // D >= 0 --> break

        @KBD
        D = M // D = KBD
        @WHITE
        D ; JEQ // KBD == 0 --> break
        @row
        D = M // D = row
        @SCREEN
        A = D+A // A = row+SCREEN
        M = -1 // RAM[row+SCREEN] = -1
        @INCREMENT
        0 ; JMP

        (WHITE)
        @row
        D = M // D = row
        @SCREEN
        A = D+A // A = row+SCREEN
        M = 0 // RAM[row+SCREEN] = 0

        (INCREMENT)
        @row
        M = M+1 // row = row+1
        @LOOP
        0 ; JMP