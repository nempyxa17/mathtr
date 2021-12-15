package com.nempyxa.mathtr.model.math;

import com.nempyxa.mathtr.exception.InvalidSettingsException;

import java.util.List;

public class MathQuestionGenerationParameters {
    private final int mLeftArgMin;
    private final int mLeftArgMax;
    private final int mRightArgMin;
    private final int mRightArgMax;
    private final List<Operation> mOperations;

    public MathQuestionGenerationParameters(int leftMin, int leftMax, int rightMin, int rightMax, List<Operation> operations) {
        if ((leftMin > leftMax) || (rightMin > rightMax)) {
            throw new InvalidSettingsException("Min value cannot be bigger that max value");
        }
        if (operations == null || operations.size() == 0) {
            throw new InvalidSettingsException("At least one operation should be provided");
        }
        mLeftArgMin = leftMin;
        mLeftArgMax = leftMax;
        mRightArgMin = rightMin;
        mRightArgMax = rightMax;
        mOperations = operations;
    }

    public int getLeftArgMin() {
        return mLeftArgMin;
    }

    public int getLeftArgMax() {
        return mLeftArgMax;
    }

    public int getRightArgMin() {
        return mRightArgMin;
    }

    public int getRightArgMax() {
        return mRightArgMax;
    }

    public List<Operation> getOperations() {
        return mOperations;
    }
}
