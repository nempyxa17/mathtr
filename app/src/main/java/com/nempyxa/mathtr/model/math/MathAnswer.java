package com.nempyxa.mathtr.model.math;

import com.nempyxa.mathtr.model.core.Answer;
import com.nempyxa.mathtr.model.core.AnswerImpl;

import lombok.Getter;

@Getter
public class MathAnswer extends AnswerImpl {
    private final int mValue;

    public MathAnswer(int answer) {
        mValue = answer;
    }

    @Override
    public String stringPresentation() {
        return String.valueOf(mValue);
    }

    @Override
    public boolean compare(Answer answer) {
        boolean result = false;
        if (answer instanceof MathAnswer) {
            result = mValue == ((MathAnswer) answer).getValue();
        }
        return result;
    }
}
