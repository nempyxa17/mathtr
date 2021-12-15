package com.nempyxa.mathtr.model.math;

import com.nempyxa.mathtr.model.core.QuestionImpl;

public class MathQuestion extends QuestionImpl {

    private int mArgLeft;
    private int mArgRight;
    private Operation mOperation;

    public MathQuestion(int argLeft, int argRight, Operation operation) {
        super(new MathAnswer(calculateAnswer(argLeft, argRight, operation)));
        mArgLeft = argLeft;
        mArgRight = argRight;
        mOperation = operation;
    }

    public MathAnswer calculateAnswer() {
        return new MathAnswer(calculateAnswer(mArgLeft, mArgRight, mOperation));
    }

    private static int calculateAnswer(int argLeft, int argRight, Operation operation) {
        switch (operation) {
            case ADDITION:
                return argLeft + argRight;
            case SUBTRACTION:
                return argLeft - argRight;
            case MULTIPLICATION:
                return argLeft * argRight;
            case DIVISION:
                return argLeft / argRight;
            default:
                throw new IllegalArgumentException("Invalid operation: " + operation);
        }
    }

    @Override
    public String stringPresentation() {
        StringBuilder sb = new StringBuilder();
        sb.append(mArgLeft).append(' ').append(mOperation.getStringValue()).append(' ').append(mArgRight);
        return sb.toString();
    }
}
