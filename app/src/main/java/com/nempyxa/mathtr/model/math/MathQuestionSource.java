package com.nempyxa.mathtr.model.math;

import com.nempyxa.mathtr.model.GeneratingQuestionSource;
import com.nempyxa.mathtr.model.core.Question;

public class MathQuestionSource extends GeneratingQuestionSource {

    private MathQuestionGenerator mGenerator;

    public MathQuestionSource(MathQuestionGenerationParameters parameters) {
        mGenerator = new MathQuestionGenerator(parameters);
    }

    @Override
    protected Question generateNext() {
        return mGenerator.generate();
    }
}
