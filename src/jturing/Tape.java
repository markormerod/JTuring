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
 * Turing Tape that can expand virtually infinitely in two directions
 */
public class Tape {

    private char[] values;
    private final char blankChar = '-';

    public Tape(char[] values) {
        this.values = values;
    }

    public Tape(char[] values, boolean overwrite) {
        this.values = values;
        if (overwrite) {
            for (int i = 0; i < values.length; i++) {
                values[0] = blankChar;
            }
        }
    }

    public void write(int index, char val) {
        values[index] = val;
    }

    public int getLength() {
        return values.length;
    }

    public void expandOnLeft() {
        char[] newValues = new char[values.length + 1];

        // Expand array towards right
        System.arraycopy(values, 0, newValues, 1, values.length);

        // Overwrite first element with blank symbol
        newValues[0] = blankChar;

        values = newValues;
    }

    public void expandOnRight() {
        char[] newValues = new char[values.length + 1];

        // Expand array on right side
        System.arraycopy(values, 0, newValues, 0, values.length);

        // Overwrite last element with blank symbol
        newValues[newValues.length - 1] = blankChar;

        values = newValues;
    }

    public char getValue(int index) {
        return values[index];
    }

    public char[] getValues() {
        return values;
    }
}
