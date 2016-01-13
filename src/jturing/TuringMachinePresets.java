/**The MIT License (MIT)

Copyright (c) 2016 Mark Ormerod

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.**/

package jturing;

/**
 * Basic Turing Machine extensions for testing and demonstration purposes
 */
public class TuringMachinePresets {

    // A parity machine counts the number of 1s on the tape of format ('0' + '1')*'*' and marks the
    // right-most cell as 'e' or 'o', denoting whether the count is even or odd
    public static class ParityMachine extends TuringMachine {
        public ParityMachine(Tape tape) {
            super(new State("even"), tape);

            State even = new State("even");
            State odd = new State("odd");
            State halt = new State("halt");

            setInstructions(new Instruction[]{
                    new Instruction(even, '0', even, '0', HeadDirection.R, false),
                    new Instruction(even, '1', odd, '1', HeadDirection.R, false),
                    new Instruction(even, '*', halt, 'e', HeadDirection.C, true),
                    new Instruction(odd, '0', odd, '0', HeadDirection.R, false),
                    new Instruction(odd, '1', even, '1', HeadDirection.R, false),
                    new Instruction(odd, '*', halt, 'o', HeadDirection.C, true)
            });
        }
    }

    // Traverses a tape of format '*'('0' + '1')*'*' from left to right then travels back to the left, flipping every
    // bit it encounters
    public static class BitFlipper extends TuringMachine {
        public BitFlipper(Tape tape) {
            super(new State("Initial"), tape);

            State initial = new State("Initial");
            State right = new State("GoRight");
            State left = new State("GoLeft");
            State halt = new State("Halt");

            setInstructions(new Instruction[]{
                    new Instruction(initial, '0', right, '0', HeadDirection.R, false),
                    new Instruction(initial, '1', right, '1', HeadDirection.R, false),
                    new Instruction(initial, '*', right, '*', HeadDirection.R, false),

                    new Instruction(right, '0', right, '0', HeadDirection.R, false),
                    new Instruction(right, '1', right, '1', HeadDirection.R, false),
                    new Instruction(right, '*', left, '*', HeadDirection.L, false),

                    new Instruction(left, '0', left, '1', HeadDirection.L, false),
                    new Instruction(left, '1', left, '0', HeadDirection.L, false),
                    new Instruction(left, '*', halt, '*', HeadDirection.C, true)
            });
        }
    }

    // Traverses tape of format '*''1'*'+''1'*'=' and writes the result of the unary addition to the left of the equals
    public static class UnaryAdder extends TuringMachine {
        public UnaryAdder(Tape tape) {
            super(new State("Initial"), tape);

            State initial = new State("Initial");
            State findOnes = new State("FindOnes");
            State writeOnes = new State("WriteOnes");
            State findEquals = new State("FindEquals");
            State halt = new State("Halt");
            State replaceZeroesLeft = new State("ReplaceZeroesLeft");
            State findOnesLeft = new State("FindOnesLeft");
            State replaceZeroes = new State("ReplaceZeroes");

            setInstructions(new Instruction[]{
                    new Instruction(initial, '*', findOnes, '*', HeadDirection.R, false),

                    new Instruction(findOnes, '1', writeOnes, '0', HeadDirection.R, false),
                    new Instruction(findOnes, '+', findOnes, '+', HeadDirection.R, false),
                    new Instruction(findOnes, '0', findOnes, '0', HeadDirection.R, false),
                    new Instruction(findOnes, '=', halt, '=', HeadDirection.C, true),
                    new Instruction(findOnes, '-', replaceZeroesLeft, '-', HeadDirection.L, false),

                    new Instruction(writeOnes, '1', writeOnes, '1', HeadDirection.R, false),
                    new Instruction(writeOnes, '+', writeOnes, '+', HeadDirection.R, false),
                    new Instruction(writeOnes, '=', writeOnes, '=', HeadDirection.R, false),
                    new Instruction(writeOnes, '0', writeOnes, '0', HeadDirection.R, false),
                    new Instruction(writeOnes, '-', findEquals, '1', HeadDirection.L, false),

                    new Instruction(findEquals, '1', findEquals, '1', HeadDirection.L, false),
                    new Instruction(findEquals , '=', findOnesLeft, '=', HeadDirection.L , false),

                    new Instruction(findOnesLeft, '*', replaceZeroes, '*', HeadDirection.R, false),
                    new Instruction(findOnesLeft, '0', findOnesLeft, '0', HeadDirection.L, false),
                    new Instruction(findOnesLeft, '=', findOnesLeft, '=', HeadDirection.L, false),
                    new Instruction(findOnesLeft, '1', writeOnes, '0', HeadDirection.R, false),
                    new Instruction(findOnesLeft, '+', findOnesLeft, '+', HeadDirection.L , false),

                    new Instruction(replaceZeroesLeft, '*', halt, '*', HeadDirection.C, true),
                    new Instruction(replaceZeroesLeft, '0', replaceZeroesLeft, '1', HeadDirection.L, false),
                    new Instruction(replaceZeroesLeft, '=', replaceZeroesLeft, '=', HeadDirection.L, false),
                    new Instruction(replaceZeroesLeft, '1', replaceZeroesLeft, '1', HeadDirection.L, false),
                    new Instruction(replaceZeroesLeft, '+', replaceZeroesLeft, '+', HeadDirection.L, false),

                    new Instruction(replaceZeroes, '-', halt, '-', HeadDirection.C, true),
                    new Instruction(replaceZeroes, '0', replaceZeroes, '1', HeadDirection.R, false),
                    new Instruction(replaceZeroes, '=', halt, '=', HeadDirection.C, true),
                    new Instruction(replaceZeroes, '1', replaceZeroes, '1', HeadDirection.R, false),
                    new Instruction(replaceZeroes, '+', replaceZeroes, '+', HeadDirection.R, false),

            });
        }
    }
}
