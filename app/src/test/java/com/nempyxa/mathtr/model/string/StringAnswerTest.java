package com.nempyxa.mathtr.model.string;

import org.junit.Test;

import static org.junit.Assert.*;

public class StringAnswerTest {

    private static final String NULL_STRING = null;
    private static final String EMPTY_STRING = "";
    private static final String ANSWER1 = "answer1";
    private static final String ANSWER2 = "answer2";

    @Test(expected = IllegalArgumentException.class)
    public void constructFromNull() {
        StringAnswer answer = new StringAnswer(NULL_STRING);
    }

    @Test(expected = IllegalArgumentException.class)
    public void constructFromEmptyString() {
        StringAnswer answer = new StringAnswer(EMPTY_STRING);
    }

    @Test
    public void stringPresentation() {
        StringAnswer answer = new StringAnswer(ANSWER1);
        assertEquals(ANSWER1, answer.stringPresentation());
        assertNotEquals(ANSWER2, answer.stringPresentation());
    }

    @Test
    public void compare() {
        StringAnswer answer = new StringAnswer(ANSWER1);
        assertTrue(answer.compare(new StringAnswer(ANSWER1)));
        assertFalse(answer.compare(new StringAnswer(ANSWER2)));
    }

    @Test(expected = UnsupportedOperationException.class)
    public void viewPresentation() {
        StringAnswer answer = new StringAnswer(ANSWER1);
        answer.viewPresentation();
    }

    @Test
    public void toStringTest() {
        StringAnswer answer = new StringAnswer(ANSWER1);
        assertEquals(ANSWER1, answer.toString());
        assertNotEquals(ANSWER2, answer.toString());
    }
}