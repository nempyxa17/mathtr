package com.nempyxa.mathtr.model.core;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class QuestionTest {

    private static final TestAnswer ANSWER_NULL = null;
    private static final List<Answer> ANSWER_LIST_NULL = null;

    private class TestQuestion extends QuestionImpl {

        public TestQuestion(Answer answer) {
            super(answer);
        }

        public TestQuestion(List<Answer> correctAnswers) {
            super(correctAnswers);
        }

        public TestQuestion(TestAnswer correctAnswer, List<Answer> answerVariants) {
            super(correctAnswer, answerVariants);
        }

        public TestQuestion(List<Answer> correctAnswers, List<Answer> answerVariants) {
            super(correctAnswers, answerVariants);
        }

        @Override
        public String stringPresentation() {
            return null;
        }
    }

    private class TestAnswer extends AnswerImpl {

        public TestAnswer() {
        }

        @Override
        public boolean compare(Answer answer) {
            return false;
        }

        @Override
        public String stringPresentation() {
            return null;
        }
    }

    @Test(expected = IllegalArgumentException.class)
    public void nullAnswers() {
        TestQuestion question = new TestQuestion(ANSWER_LIST_NULL, Collections.singletonList(new TestAnswer()));
    }

    @Test(expected = IllegalArgumentException.class)
    public void emptyAnswers() {
        TestQuestion question = new TestQuestion(new ArrayList<>(), Collections.singletonList(new TestAnswer()));
    }

    @Test(expected = IllegalArgumentException.class)
    public void nullAnswer() {
        TestQuestion question = new TestQuestion(Arrays.asList(new TestAnswer(), null), Collections.singletonList(new TestAnswer()));
    }

    @Test(expected = IllegalArgumentException.class)
    public void nullVariant() {
        ArrayList<Answer> listWithNull = new ArrayList<>();
        listWithNull.add(new TestAnswer());
        listWithNull.add(null);
        TestQuestion question = new TestQuestion(Collections.singletonList(new TestAnswer()), listWithNull);
    }

    @Test(expected = IllegalArgumentException.class)
    public void constructWithSingleAnswerNull() {
        TestQuestion question = new TestQuestion(ANSWER_NULL);
    }

    @Test(expected = IllegalArgumentException.class)
    public void constructWithAnswerListNull() {
        TestQuestion question = new TestQuestion(ANSWER_LIST_NULL);
    }

    @Test(expected = IllegalArgumentException.class)
    public void constructWithAnswerListContainingNull() {
        ArrayList<Answer> listWithNull = new ArrayList<>();
        listWithNull.add(new TestAnswer());
        listWithNull.add(null);
        TestQuestion question = new TestQuestion(listWithNull);
    }
}