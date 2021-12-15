package com.nempyxa.mathtr.model.string;

import android.view.View;

import com.nempyxa.mathtr.model.core.Answer;
import com.nempyxa.mathtr.model.core.AnswerImpl;
import com.nempyxa.mathtr.model.math.MathAnswer;

import androidx.annotation.NonNull;
import lombok.Getter;

@Getter
public class StringAnswer extends AnswerImpl {

    private String mValue;

    public StringAnswer(String value) {
        if (value == null || value.equals("")) {
            throw new IllegalArgumentException("Null or empty value is not a valid answer");
        }
        mValue = value;
    }

    @Override
    public String stringPresentation() {
        return mValue;
    }

    @Override
    public boolean compare(Answer answer) {
        boolean result = false;
        if (answer instanceof StringAnswer) {
            result = mValue.equals(((StringAnswer) answer).getValue());
        }
        return result;
    }
}
