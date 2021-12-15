package com.nempyxa.mathtr.model.core;

import android.view.View;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import lombok.Getter;

@Getter
public abstract class QuestionImpl implements Question {

    /**
     * List of variants of answers to this question
     */
    private final List<? extends Answer> mAnswerVariants;

    /**
     * List of correct answers to this question
     */
    private final List<? extends Answer> mCorrectAnswers;

    /**
     * Construct new question
     * @param answer Correct answer to the question
     */
    protected QuestionImpl(Answer answer) {
        this(Collections.singletonList(answer));
    }

    /**
     * Construct new question with given answers
     * @param correctAnswers List of correct answers to the question
     */
    protected QuestionImpl(List<Answer> correctAnswers) {
        this(correctAnswers, null);
    }

    /**
     * Construct new question with given correct answer and answer variants
     * @param correctAnswer Correct answer to the question
     * @param answerVariants List of answer variants to the question
     */
    protected QuestionImpl(Answer correctAnswer, List<Answer> answerVariants) {
        this(Collections.singletonList(correctAnswer), answerVariants);
    }

    /**
     * Construct new question with given correct answers and answer variants
     * @param correctAnswers List of correct answers to the question
     * @param answerVariants List of answer variants to the question
     */
    protected QuestionImpl(List<Answer> correctAnswers, List<Answer> answerVariants) {
        if (correctAnswers == null || correctAnswers.size() == 0) {
            throw new IllegalArgumentException("At least one correct answer should be provided");
        }
        if (correctAnswers.contains(null)) {
            throw new IllegalArgumentException("null is not a valid answer");
        }
        if (answerVariants != null && answerVariants.contains(null)) {
            throw new IllegalArgumentException("null is not a valid variant");
        }
        mAnswerVariants = answerVariants;
        mCorrectAnswers = correctAnswers;
    }

    public boolean verify(Answer... answer) {
        return verify(Arrays.asList(answer));
    }

    public boolean verify(List<Answer> answers) {
        boolean result = true;
        // TODO: re-implement using streams and comparator
        if (answers != null && answers.size() != 0 && !answers.contains(null) && answers.size() == mCorrectAnswers.size()) {
            for (Answer correctAnswer : mCorrectAnswers) {
                boolean found = false;
                for (Answer answer : answers) {
                    if (correctAnswer.compare(answer)) {
                        found = true;
                        break;
                    }
                }
                if (!found) {
                    result = false;
                    break;
                }
            }
        } else {
            result = false;
        }
        return result;
    }

    @Override
    public String toString() {
        return stringPresentation();
    }

    @Override
    public View viewPresentation() {
        throw new UnsupportedOperationException();
    }
}
