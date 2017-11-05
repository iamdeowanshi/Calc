package com.example.aaditya.integercalculator;

/**
 * Created by aaditya on 9/27/17.
 */

public enum Operation {
    PLUS("+"){
        @Override
        int apply(int operand1, int operand2) {
            return operand1 + operand2;
        }
    },
    MINUS("-") {
        @Override
        int apply(int operand1, int operand2) {
            return operand1 - operand2;
        }
    },
    MULTIPLY("*") {
        @Override
        int apply(int operand1, int operand2) {
            return operand1 * operand2;
        }
    },
    DIVIDE("/") {
        @Override
        int apply(int operand1, int operand2) {
            double result = (double)operand1 / (double)operand2;

            if ( result > 0)
                return (int)Math.round(result);

            if (String.valueOf(result).contains(".5"))
                return (int)Math.floor(result);

            return (int)Math.round(result);
        }
    };

    private String symbol;

    Operation(String symbol ) {
        this.symbol = symbol;
    }

    @Override
    public String toString() {
        return symbol;
    }

    abstract int apply(int operand1, int operand2);
}
