# Nand2Tetris
My implementation of the Nand-2-Tetris projects. These are a series of 12 projects regarding computer architecture, beginning from using a NAND gate to design Boolean Logic gates such as the NOT, AND as well as OR gates and ending with designing a basic OS. Here is the link: [Nand2Tetris](https://www.nand2tetris.org/).

Here is an explanation of what each project revolves on:

**Project 1(Boolean Logic)** - Starting from a NAND gate, which is a universal gate along with the NOR gate, the basic logic gates such as the NOT gate, AND gate and OR gate can be designed from this one gate. Then, we can also design a MUX gate, which selects one output from several inputs based on a control bit, and a DMUX gate, whose objective is vice versa. 16-bit variants have also been created in addition to 4-way and 8-way MUX and DMUX gates.

**Project 2(Boolean Arithmetic)** - Using our logic gates, we can then combine them to form adding gates, which are capable of adding and subtracting values in several bits. The most basic of these is the Half-Adder, which adds two bits and returns the sum and a carry but, and this project culminates with designing an ALU that can perform addition, subtraction and logical operations.

**Project 3(Memory)** - From here, we focus on designing memory units, starting with a Bit that is capable of storing a single bit value based on a control value and ending with designing RAM for providing instructions to a CPU and a PC(Program Counter), whose role is to determine the next instruction to be executed.

Note that the design of gates is possible by using a *Hardware Descriptive Language(HDL)*. A *Hardware Simulator* is needed to test your design. If you plan to download the software, you will require the *Java Runtime Environment(JRE)*. 

**Project 4(Machine Language)** - After designing gates, this project shifts towards the machine language used by the model CPU for executing instructions. To gain an understanding, we are required to write two programs in assembly language designed for this course. More details are provided in the slides. Unlike previous projects, a *CPU Emulator* is required to test our programs. 

**Project 5(Computer Architecture)** - In this project, we combine the ALU from *Project 2* and the PC from *Project 3* to create a CPU capable of performing instructions written in the assembly language from *Project 4*. We also use RAM from *Project 3* to create a Memory for the CPU and combine them to form a computer. Once again, the *Hardware Simulator* is required.

**Project 6(Assembler)** - Having learnt the assembly language that the CPU operates on, we proceed to create an assembler in *any programming language* that will convert assembly language into the Hack machine language. This assembler takes an input file of the form File.asm and return an output file of the form File.hack. If you download the package an assembler has already been provided, but the purpose of this project is to design one yourself. The provided assembler, however, does allow for comparisons.

Afterwards, more focus is given to the software component of the computer. This begins with the virtual machine, since the high-level language Jack undergoes a two-stage compilation, with the intermediate called *Virtual Machine(VM) code*. The next two projects revolve on the virtual machine and its operations, since we're to build a *Virtual Machine Translator* that converts the VM code to assembly language.

**Project 7(Stack Arithmetic)** - As the VM is stack based, we proceed to build a basic VM Translator in *any programming language* that can generate assembly code to push and pop values based on the provided segments. Arithmetic operations can also be converted. However, there are additional commmands that the translator I had provided in the Project 7 directory cannot handle.

**Project 8(Program Control)** - This brings us here, as we're introducted to the other commands, which are labels, gotos, if-gotos, functions, calls and returns. We should update our existing VM translator to handle these commands. Once done, our translator should also be capable of processing files from the previous project.

A *VM Emulator* has been provided in the download to understand the workings of VM code. Be aware that the VM Translator is more complex than the assembler, since we need to write several assembly language commands for every VM command.

**Projects 9-12(TO BE ADDED LATER)**
