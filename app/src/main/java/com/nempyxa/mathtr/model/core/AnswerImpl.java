package com.nempyxa.mathtr.model.core;

import android.view.View;

import androidx.annotation.NonNull;

public abstract class AnswerImpl implements Answer {

    @Override
    public String toString() {
        return stringPresentation();
    }

    @Override
    public View viewPresentation() {
        throw new UnsupportedOperationException();
    }

}
