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

import java.util.ArrayList;

/**
 * Standard specification Turing machine with an instruction table, executing functions and a  time counter measure
 */
public class TuringMachine {

    private State currentState;
    private int currentPosition;
    private int displacementCounter;
    private int time;
    private ArrayList<char[]> history;
    private Tape tape;
    private Instruction[] instructions;
    private boolean halt;
    private Instruction currentInstruction;

    // Constructors

    public TuringMachine(State currentState, Instruction[] instructions, Tape tape) {
        this.currentState = currentState;
        this.instructions = instructions;
        this.tape = tape;
        this.currentPosition = 0;
        this.time = 0;
        this.history = new ArrayList<>();
        this.halt = false;
        this.displacementCounter = 0;
    }

    public TuringMachine(State currentState, int currentPosition, Tape tape, Instruction[] instructions) {
        this.currentState = currentState;
        this.currentPosition = currentPosition;
        this.tape = tape;
        this.instructions = instructions;
        this.time = 0;
        this.history = new ArrayList<>();
        this.halt = false;
        this.displacementCounter = 0;
    }

    public TuringMachine(State currentState, int currentPosition, Tape tape) {
        this.currentState = currentState;
        this.history = new ArrayList<>();
        this.time = 0;
        this.currentPosition = currentPosition;
        this.tape = tape;
        this.instructions = new Instruction[0];
        this.displacementCounter = 0;
        this.halt = false;
    }

    public TuringMachine(State currentState, Tape tape) {
        this.currentState = currentState;
        this.history = new ArrayList<>();
        this.tape = tape;
        this.instructions = new Instruction[0];
        this.currentPosition = 0;
        this.time = 0;
        this.displacementCounter = 0;
        this.halt = false;
    }

    public TuringMachine(State currentState) {
        this.currentState = currentState;
        this.instructions = new Instruction[0];
        this.currentPosition = 0;
        this.time = 0;
        this.history = new ArrayList<>();
        this.tape = new Tape(new char[0]);
        this.displacementCounter = 0;
        this.halt = false;
    }

    // Tries to find a corresponding instruction in the quintuple set for a given state and value
    public Instruction getInstruction(State state, char val) throws Instruction.InstructionNotFoundException {
        Instruction inst = null;
        for (int i = 0; i < instructions.length; i++) {
            if (instructions[i].getOldState().equals(state) && instructions[i].getGivenSymbol() == val) {
                inst = instructions[i];
            }
        }
        if (inst == null)  {
            halt = true;
            throw new Instruction.InstructionNotFoundException(state, val);
        }
        return inst;
    }

    // Executes a given instruction via the Turing Machine
    public void executeInstruction(Instruction instruction) {

        this.currentInstruction = instruction;

        // Write appropriate symbol to current position
        tape.write(currentPosition + displacementCounter, instruction.getWriteSymbol());

        // Move the reader head
        if (instruction.getDirection() == HeadDirection.L && !halt) {

            // Check if position is in bounds and expand if necessary
            if (currentPosition + displacementCounter == 0) {
                tape.expandOnLeft();

                // Add one to displacementCounter to keep positional values relative despite leftward expansion
                displacementCounter++;
            }
            currentPosition--;
        }
        else if (instruction.getDirection() == HeadDirection.R && !halt) {
            // Check if position is in bounds and expand if necessary
            if (currentPosition + displacementCounter == tape.getLength() - 1) {
                tape.expandOnRight();
            }
            currentPosition++;
        }

        // Change state
        currentState = instruction.getNewState();

        time++;

        if (instruction.isHalt()) {
            halt = true;
        }

        // Add the current tape to the history
        history.add(tape.getValues());
    }

    public void step() {
        try {
            executeInstruction(getInstruction(currentState, tape.getValue(currentPosition + displacementCounter)));
        } catch (Instruction.InstructionNotFoundException e) {
            e.printStackTrace();
        }    }

    public void stepBack() {
        this.setTape(new Tape(getHistory().get(time)));
        history.remove(history.size());
        time--;
    }


    // Loops through the tape executing the appropriate instructions until the machine halts
    public void run() {
        while (!halt) {
            try {
                executeInstruction(getInstruction(currentState, tape.getValue(currentPosition + displacementCounter)));
            } catch (Instruction.InstructionNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    public void printTape() {
        System.out.print("\n|");

        // Print out tape values between vertical bars
        for (int i = 0; i < tape.getLength(); i++) {
            System.out.print(tape.getValue(i));
            System.out.print('|');
        }
        System.out.println();

        // Align and print position indicator
        for (int i = 0; i < ((currentPosition + displacementCounter) * 2) + 1; i++) {
            System.out.print(' ');
        }
        System.out.print('^');

        // Print information about the state of the Turing Machine
        System.out.println("\ncurrentState = " + currentState.getName());
        if (!halt) {
            System.out.println("direction = " + currentInstruction.getDirection());
        }
        System.out.println("time = " + time);
        // System.out.println("currentPosition = " + (currentPosition + displacementCounter));

        System.out.print("=============================================");
    }

    // Executes the instruction set and prints the tape between steps
    public void runPrint() {
        while (!halt) {
            try {
                executeInstruction(getInstruction(currentState, tape.getValue(currentPosition + displacementCounter)));
                printTape();
            } catch (Instruction.InstructionNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    // Getters and setters

    public Instruction[] getInstructions() {
        return instructions;
    }

    public void setInstructions(Instruction[] instructions) {
        this.instructions = instructions;
    }

    public int getCurrentPosition() {
        return currentPosition;
    }

    public void setCurrentPosition(int currentPosition) {
        this.currentPosition = currentPosition;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public ArrayList<char[]> getHistory() {
        return history;
    }

    public void setHistory(ArrayList<char[]> history) {
        this.history = history;
    }

    public Tape getTape() {
        return tape;
    }

    public void setTape(Tape tape) {
        this.tape = tape;
    }

    public State getCurrentState() {
        return currentState;
    }

    public void setCurrentState(State currentState) {
        this.currentState = currentState;
    }

    public boolean isHalt() {
        return halt;
    }

    public void setHalt(boolean halt) {
        this.halt = halt;
    }
}

