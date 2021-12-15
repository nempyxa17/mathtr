package com.nempyxa.mathtr.model;

import com.nempyxa.mathtr.model.core.Question;

import java.util.ArrayList;
import java.util.List;

public class ListQuestionSource implements QuestionSource {

    private List<Question> questions;
    private int mIndex;

    protected ListQuestionSource(List<Question> list) {
        // TODO: check map validity
        this.questions = list;
    }

    @Override
    public int size() {
        return questions.size();
    }

    @Override
    public List<Question> loadAll() {
        return questions;
    }

    @Override
    public List<Question> loadNext(int number) {
        // TODO: validate number
        if (mIndex >= questions.size()) {
            return null;
        }
        ArrayList<Question> result = new ArrayList<>();
        for (int i = mIndex; i < Math.min(questions.size(), mIndex + number); i++) {
            result.add(questions.get(i));
        }
        return result;
    }

    @Override
    public Question loadNext() {
        if (mIndex >= questions.size()) {
            return null;
        }
        return questions.get(mIndex++);
    }
}
