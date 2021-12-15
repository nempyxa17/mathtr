package com.nempyxa.mathtr.model;

import com.nempyxa.mathtr.model.QuestionSource;
import com.nempyxa.mathtr.model.core.Question;

import java.util.ArrayList;
import java.util.List;

public abstract class GeneratingQuestionSource implements QuestionSource {

    protected abstract Question generateNext();

    @Override
    public Question loadNext() {
        return generateNext();
    }

    @Override
    public List<Question> loadNext(int number) {
        // TODO: check number validity
        ArrayList<Question> result = new ArrayList<>();
        for (int i = 0; i < number; i++) {
            result.add(loadNext());
        }
        return result;
    }

    @Override
    public List<Question> loadAll() {
        throw new UnsupportedOperationException();
    }

    @Override
    public int size() {
        return SIZE_UNKNOWN;
    }
}
