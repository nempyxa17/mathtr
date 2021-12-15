package com.nempyxa.mathtr.model;

import com.nempyxa.mathtr.model.core.Question;

import java.util.List;

/**
 * Interface to load questions synchronously.
 * Inherit this one to load questions from file, network or anywhere else.
 */
public interface QuestionSource {

    /**
     * Identifies unknown number of questions in the source
     */
    public static final int SIZE_UNKNOWN = -1;

    /**
     * Load next question
     * @return Next question in source, null if no more questions available
     */
    Question loadNext();

    /**
     * Load next number of questions
     * @param number Number of questions to load
     * @return List of loaded questions
     */
    List<Question> loadNext(int number);

    /**
     * Load all available questions
     * @return List of loaded questions. List will contain only one element is size is unknown.
     */
    List<Question> loadAll();

    /**
     * Get total number of questions in the source.
     * @return Total number of questions or {@link #SIZE_UNKNOWN}
     */
    int size();
}
