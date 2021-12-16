package com.nempyxa.mathtr.model.math;

import com.nempyxa.mathtr.exception.InvalidSettingsException;
import com.nempyxa.mathtr.exception.QuestionGenerationException;
import com.nempyxa.mathtr.log.Logger;
import com.nempyxa.mathtr.model.core.Question;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class MathQuestionGenerator {

    private final MathQuestionGenerationParameters mParameters;
    private final int mUniqueInterval;

    private static final int GENERATION_ATTEMPTS = 3;

    private static final int MEMORY_SIZE = 100;
    private List<Question> mGeneratedQuestions;
    private int mGeneratedCount = 0;

    private Random mRandom = new Random();

    /**
     * Create question generator with given parameters
     *
     * @param parameters Parameters used to generate questions
     */
    public MathQuestionGenerator(MathQuestionGenerationParameters parameters) {
        this(parameters, MEMORY_SIZE);
    }

    /**
     * Create question generator with given parameters and uniqueness period
     *
     * @param parameters     Parameters used to generate questions
     * @param uniqueInterval Uniqueness interval. Try to generate unique questions within each
     *                       interval.
     */
    public MathQuestionGenerator(MathQuestionGenerationParameters parameters, int uniqueInterval) {
        List<Operation> validOperations = validateParameters(parameters);
        mParameters = new MathQuestionGenerationParameters(
                parameters.getLeftArgMin(),
                parameters.getLeftArgMax(),
                parameters.getRightArgMin(),
                parameters.getRightArgMax(),
                validOperations);
        this.mUniqueInterval = Math.min(Math.max(1, uniqueInterval), MEMORY_SIZE);
        mGeneratedQuestions = new ArrayList<>(MEMORY_SIZE);
    }

    private List<Operation> validateParameters(MathQuestionGenerationParameters parameters) {
        ArrayList<Operation> validOperations = new ArrayList<>();
        ArrayList<Operation> invalidOperations = new ArrayList<>();
        for (Operation operation : parameters.getOperations()) {
            if (validOperations.contains(operation)) continue;
            if (isOperationValid(operation, parameters)) {
                validOperations.add(operation);
            } else {
                invalidOperations.add(operation);
            }
        }
        if (validOperations.size() != 0) {
            return validOperations;
        } else {
            throw new InvalidSettingsException("No operation is applicable to given intervals");
        }
    }

    public Question generate() {
        return generate(0);
    }

    public Question generate(int iteration) {
        Question result;
        Operation operation = mParameters.getOperations().get(mRandom.nextInt(mParameters.getOperations().size()));
        switch (operation) {
            case ADDITION:
                result = generateAddition();
                break;
            case SUBTRACTION:
                result = generateSubtraction();
                break;
            case MULTIPLICATION:
                result = generateMultiplication();
                break;
            case DIVISION:
                result = generateDivision();
                break;
            default:
                throw new IllegalArgumentException("Invalid operation: " + operation.getStringValue());
        }
        if (iteration < GENERATION_ATTEMPTS && !checkUnique(result)) {
            result = generate(iteration + 1);
        } else {
            mGeneratedCount++;
            mGeneratedQuestions.add(result);
            if (mGeneratedQuestions.size() > MEMORY_SIZE) {
                mGeneratedQuestions.remove(0);
            }
        }
        return result;
    }

    private Question generateAddition() {
        int leftArg = mRandom.nextInt(mParameters.getLeftArgMax() - mParameters.getLeftArgMin() + 1) + mParameters.getLeftArgMin();
        int rightArg = mRandom.nextInt(mParameters.getRightArgMax() - mParameters.getRightArgMin() + 1) + mParameters.getRightArgMin();
        return new MathQuestion(leftArg, rightArg, Operation.ADDITION);
    }

    private Question generateSubtraction() {
        // Variants:
        // 1.
        //        [.....*]
        //    [*.......]
        // 2. (invalid)
        //[....]
        //           [....]
        // 3.
        //           [*......*]
        //[*....*]
        // 4.
        //       [*.....*]
        //    [......]
        // 5.
        //    [*...*]
        //  [.........]
        // 6.
        //[.....*..........*]
        //    [.......]

        // firstLeft = firstMin > secondMin ? firstMin : secondMin
        // firstRight = firstMax
        //
        // first = generate(firstLeft, firstRight)
        //
        // secondLeft = secondMin
        // secondRight = first > secondMax ? first : secondMax

        int leftMin = Math.max(mParameters.getLeftArgMin(), mParameters.getRightArgMin());
        int leftMax = mParameters.getLeftArgMax();
        int leftArg = mRandom.nextInt(leftMax - leftMin + 1) + leftMin;

        int rightMin = mParameters.getRightArgMin();
        int rightMax = Math.min(mParameters.getRightArgMax(), leftArg);

        int rightArg = rightMin;
        if (rightMax != rightMin) {
            rightArg = mRandom.nextInt(rightMax - rightMin + 1) + rightMin;
        }
        return new MathQuestion(leftArg, rightArg, Operation.SUBTRACTION);
    }

    private Question generateMultiplication() {
        int leftArg = mRandom.nextInt(mParameters.getLeftArgMax() - mParameters.getLeftArgMin() + 1) + mParameters.getLeftArgMin();
        int rightArg = mRandom.nextInt(mParameters.getRightArgMax() - mParameters.getRightArgMin() + 1) + mParameters.getRightArgMin();
        return new MathQuestion(leftArg, rightArg, Operation.MULTIPLICATION);
    }

    private Question generateDivision() {
        int leftMin = mParameters.getLeftArgMin();
        int leftMax = mParameters.getLeftArgMax();
        int rightMin = mParameters.getRightArgMin();
        int rightMax = Math.min(mParameters.getLeftArgMax(), mParameters.getRightArgMax());

        int iteration = 0;
        while (iteration < 10) {
            int rightArg = mRandom.nextInt(rightMax - rightMin + 1) + rightMin;
            int minRatio = (int) Math.ceil((double) leftMin / rightArg);
            int maxRatio = (int) Math.floor((double) leftMax / rightArg);
            Logger.d("" + rightArg + ", " + minRatio + ", " + maxRatio);
            if (minRatio <= maxRatio) {
                int leftArg = rightArg * (mRandom.nextInt(maxRatio - minRatio + 1) + minRatio);
                return new MathQuestion(leftArg, rightArg, Operation.DIVISION);
            }
            iteration++;
        }
        throw new QuestionGenerationException();
    }

    /**
     * Checks whether given operation is valid for given intervals of numbers.
     * If operation gives at least one pair of numbers from given intervals for which
     * result is positive integer - operation is valid.
     * @param operation Operation
     * @return true if operation is valid, false otherwise
     */
    private boolean isOperationValid(Operation operation, MathQuestionGenerationParameters parameters) {
        switch (operation) {
            case ADDITION:
            case MULTIPLICATION:
                return true;
            case SUBTRACTION:
                return isSubtractionValid(parameters);
            case DIVISION:
                return isDivisionValid(parameters);
        }
        return true;
    }

    private boolean isDivisionValid(MathQuestionGenerationParameters parameters) {
        int leftMin = parameters.getLeftArgMin();
        int leftMax = parameters.getLeftArgMax();
        int rightMin = parameters.getRightArgMin();
        int rightMax = Math.min(parameters.getLeftArgMax(), parameters.getRightArgMax());

        if (rightMin == 0) return false;

        for (int left = leftMin; left <= leftMax; left++) {
            for (int right = rightMin; right <= rightMax; right++) {
                if (left % right == 0) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean isSubtractionValid(MathQuestionGenerationParameters parameters) {
        return parameters.getLeftArgMax() >= parameters.getRightArgMin();
    }

    private boolean checkUnique(Question question) {
        // TODO: implement
        return true;
    }
}
