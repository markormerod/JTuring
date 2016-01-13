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


public class TuringMachineTest {

    public static void main(String[] args) {

        // Create the input tape
//        Tape input = new Tape(new char[] {
//            '*', '1', '0', '1', '0', '1', '0', '*'
//        });
//
//        TuringMachinePresets.BitFlipper bitFlipper = new TuringMachinePresets.BitFlipper(input);
//        bitFlipper.runPrint();

//        Tape input = new Tape(new char[] {
//            '*', '1', '1', '+', '1', '1', '1', '1', '=',
//        });
//
//        TuringMachinePresets.UnaryAdder unaryAdder = new TuringMachinePresets.UnaryAdder(input);
//        unaryAdder.runPrint();

        // Expansion test machine

        Tape input = new Tape(new char[] {
                '1', '0', '1', '1', '0', '2'
        });

        State initial = new State("Initial");
        State findTwo = new State("FindTwo");
        State overwrite = new State("Overwrite");
        State halt = new State("Halt");
        State writeOneRight = new State("WriteOneRight ");

        TuringMachine leftExpandTest = new TuringMachine(initial, input);

        leftExpandTest.setInstructions(new Instruction[]{
                new Instruction(initial, '1', findTwo, '1', HeadDirection.R, false),

                new Instruction(findTwo, '0', findTwo, '0', HeadDirection.R, false),
                new Instruction(findTwo, '1', findTwo, '1', HeadDirection.R, false),
                new Instruction(findTwo, '2', overwrite, '2', HeadDirection.L, false),

                new Instruction(overwrite, '1', overwrite, '2', HeadDirection.L, false),
                new Instruction(overwrite, '0', overwrite, '2', HeadDirection.L, false),
                new Instruction(overwrite, '-', writeOneRight, '2', HeadDirection.R, false),

                new Instruction(writeOneRight, '2', writeOneRight, '2', HeadDirection.R, false),
                new Instruction(writeOneRight, '-', halt, 'x', HeadDirection.C, true),

        });

        leftExpandTest.runPrint();
    }
}
