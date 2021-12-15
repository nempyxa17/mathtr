package com.nempyxa.mathtr.model.string;

import com.nempyxa.mathtr.model.core.Answer;
import com.nempyxa.mathtr.model.core.QuestionImpl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class StringQuestion extends QuestionImpl {

    private String mQuestion;

    public StringQuestion(String question, String correctAnswer) {
        this(question, Arrays.asList(correctAnswer), null);
    }

    public StringQuestion(String question, List<String> correctAnswers) {
        this(question, correctAnswers, null);
    }

    public StringQuestion(String question, String correctAnswer, List<String> answerVariants) {
        this(question, Arrays.asList(correctAnswer), answerVariants);
    }

    public StringQuestion(String question, List<String> correctAnswers, List<String> answerVariants) {
        super(answersFromStrings(correctAnswers, false), answersFromStrings(answerVariants, true));
        validateQuestion(question);
        mQuestion = question;
    }

    private static void validateQuestion(String question) {
        if (question == null || question.equals("")) {
            throw new IllegalArgumentException("Question cannot be null or empty");
        }
    }

    private static List<Answer> answersFromStrings(List<String> answers, boolean emptyAllowed) {
        List<Answer> result;
        if (answers != null && answers.size() != 0) {
            result = answers.stream().map(StringAnswer::new).collect(Collectors.toList());
        } else {
            if (!emptyAllowed) {
                throw new IllegalArgumentException("Answers list cannot be empty");
            } else {
                result = answers == null ? null : new ArrayList<>();
            }
        }
        return result;
    }

    @Override
    public String stringPresentation() {
        return mQuestion;
    }
}
