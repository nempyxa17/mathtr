package com.nempyxa.mathtr.model.core;

/**
 * Interface that represents the answer to the question.
 */
public interface Answer extends Presentable {

    /**
     * Compare with given answer
     *
     * @param answer Answer to compare with
     * @return true if answers are equal, false otherwise.
     */
    boolean compare(Answer answer);
}
