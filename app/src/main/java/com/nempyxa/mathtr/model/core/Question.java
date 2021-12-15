package com.nempyxa.mathtr.model.core;

import java.util.List;

/**
 * Question representation with ability to verify answers for correctness
 */
public interface Question extends Presentable {

    /**
     * Verify correctness of given answer(s)
     *
     * @param answer Answer to check
     * @return true if the list of answers is of the same size and the list of correct answers
     * and lists are equal element-wise (order does not matter),
     * false otherwise
     */
    boolean verify(Answer... answer);

    /**
     * Verify given answers against correct answers.
     *
     * @param answers Answers to check
     * @return true if the list of answers is of the same size and the list of correct answers
     * and lists are equal element-wise (order does not matter),
     * false otherwise
     */
    boolean verify(List<Answer> answers);
}
