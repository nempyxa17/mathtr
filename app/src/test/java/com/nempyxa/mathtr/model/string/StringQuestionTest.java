package com.nempyxa.mathtr.model.string;

import com.nempyxa.mathtr.model.string.StringAnswer;
import com.nempyxa.mathtr.model.string.StringQuestion;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class StringQuestionTest {

    private static final String NULL_STRING = null;
    private static final List<String> NULL_ANSWERS_LIST = null;
    private static final List<String> EMPTY_ANSWERS_LIST = new ArrayList<>();
    private static final String EMPTY = "";
    private static final String ANSWER_0 = "0";
    private static final String ANSWER_1 = "1";
    private static final String ANSWER_2 = "2";
    private static final String ANSWER_3 = "3";
    private static final String QUESTION = "What is bigger than 0?";
    private static final String QUESTION_1 = "What is bigger than 1?";

    @Test(expected = IllegalArgumentException.class)
    public void nullQuestionTest() {
        StringQuestion question = new StringQuestion(NULL_STRING, ANSWER_1);
    }

    @Test
    public void nullAnswerTest() {
        try {
            StringQuestion question = new StringQuestion(QUESTION, NULL_STRING);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException iae) {
        }
    }

    @Test
    public void nullAnswerListTest() {
        try {
            StringQuestion question = new StringQuestion(QUESTION, NULL_ANSWERS_LIST);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException iae) {
        }
    }

    @Test(expected = IllegalArgumentException.class)
    public void nullAnswerWithVariantsTest() {
        StringQuestion question = new StringQuestion(QUESTION, NULL_STRING, Collections.singletonList(ANSWER_0));
    }

    @Test(expected = IllegalArgumentException.class)
    public void nullAnswerListWithVariantsTest() {
        StringQuestion question = new StringQuestion(QUESTION, NULL_ANSWERS_LIST, Collections.singletonList(ANSWER_0));
    }

    @Test(expected = IllegalArgumentException.class)
    public void emptyAnswerListTest() {
        StringQuestion question = new StringQuestion(QUESTION, EMPTY_ANSWERS_LIST);
    }

    @Test
    public void nullVariantsListTest() {
        try {
            StringQuestion question = new StringQuestion(QUESTION, ANSWER_0, NULL_ANSWERS_LIST);
        } catch (Exception e) {
            fail("Null should be allowed for variants list");
        }
    }

    @Test
    public void emptyVariantsListTest() {
        try {
            StringQuestion question = new StringQuestion(QUESTION, ANSWER_0, EMPTY_ANSWERS_LIST);
        } catch (Exception e) {
            fail("Empty list should be allowed for variants list");
        }
    }

    @Test
    public void checkCorrectAnswersSmallerSizeNotCorrect() {
        StringQuestion question = new StringQuestion(QUESTION, Arrays.asList(ANSWER_1, ANSWER_2));
        boolean isCorrect = question.verify(new StringAnswer(ANSWER_3));
        assertFalse(isCorrect);
    }

    @Test
    public void checkCorrectAnswersSmallerSizeCorrect() {
        StringQuestion question = new StringQuestion(QUESTION, Arrays.asList(ANSWER_1, ANSWER_2));
        boolean isCorrect = question.verify(new StringAnswer(ANSWER_2));
        assertFalse(isCorrect);
    }

    @Test
    public void checkCorrectAnswersEqualSizeIncorrect() {
        StringQuestion question = new StringQuestion(QUESTION, Arrays.asList(ANSWER_1, ANSWER_2));
        boolean isCorrect = question.verify(new StringAnswer(ANSWER_2), new StringAnswer(ANSWER_3));
        assertFalse(isCorrect);
    }

    @Test
    public void checkCorrectAnswersEqualSizeCorrect() {
        StringQuestion question = new StringQuestion(QUESTION, Arrays.asList(ANSWER_1, ANSWER_2));
        boolean isCorrect = question.verify(new StringAnswer(ANSWER_1), new StringAnswer(ANSWER_2));
        assertTrue(isCorrect);
    }

    @Test
    public void checkCorrectAnswersSizeOneCorrect() {
        StringQuestion question = new StringQuestion(QUESTION, Collections.singletonList(ANSWER_1));
        boolean isCorrect = question.verify(new StringAnswer(ANSWER_1));
        assertTrue(isCorrect);
    }

    @Test
    public void checkCorrectAnswersSizeOneIncorrect() {
        StringQuestion question = new StringQuestion(QUESTION, Collections.singletonList(ANSWER_1));
        boolean isCorrect = question.verify(new StringAnswer(ANSWER_2));
        assertFalse(isCorrect);
    }

    @Test
    public void stringPresentation() {
        StringQuestion question = new StringQuestion(QUESTION, ANSWER_1);
        assertEquals(question.stringPresentation(), QUESTION);
        assertNotEquals(QUESTION_1, question.stringPresentation());
    }

    @Test(expected = UnsupportedOperationException.class)
    public void viewPresentation() {
        StringQuestion question = new StringQuestion(QUESTION, ANSWER_1);
        question.viewPresentation();
    }

    @Test
    public void toStringTest() {
        StringQuestion question = new StringQuestion(QUESTION, ANSWER_1);
        assertEquals(QUESTION, question.toString());
        assertNotEquals(QUESTION_1, question.toString());
    }
}