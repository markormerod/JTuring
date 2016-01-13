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
 * The instruction class provides all of the variables of Turing's instruction quintuples as well a boolean halt indicator
 */
public class Instruction {
    private State oldState; // The state that was previously read
    private char givenSymbol; // The symbol that was read
    private State newState; // The state to move in to
    private char writeSymbol; // The symbol to write
    private HeadDirection direction; // The direction to travel in
    private boolean halt; // Whether or not the instruction causes the machine to halt

    public Instruction(State oldState, char givenSymbol, State newState, char writeSymbol, HeadDirection direction,
                       boolean halt) {
        this.oldState = oldState;
        this.givenSymbol = givenSymbol;
        this.newState = newState;
        this.writeSymbol = writeSymbol;
        this.direction = direction;
        this.halt = halt;
    }

    // Exception thrown when a given state-value combination has no pre-programmed instruction association
    public static class InstructionNotFoundException extends Exception {
        public InstructionNotFoundException(State state, char input) {
            super("Instruction not found (State: " + state.getName() + ", Symbol: " + input + ')');
        }
    }

    // Getters and setters

    public boolean isHalt() {
        return halt;
    }

    public void setHalt(boolean halt) {
        this.halt = halt;
    }

    public State getOldState() {
        return oldState;
    }

    public void setOldState(State oldState) {
        this.oldState = oldState;
    }

    public char getGivenSymbol() {
        return givenSymbol;
    }

    public void setGivenSymbol(char givenSymbol) {
        this.givenSymbol = givenSymbol;
    }

    public State getNewState() {
        return newState;
    }

    public void setNewState(State newState) {
        this.newState = newState;
    }

    public char getWriteSymbol() {
        return writeSymbol;
    }

    public void setWriteSymbol(char writeSymbol) {
        this.writeSymbol = writeSymbol;
    }

    public HeadDirection getDirection() {
        return direction;
    }

    public void setDirection(HeadDirection direction) {
        this.direction = direction;
    }

}


