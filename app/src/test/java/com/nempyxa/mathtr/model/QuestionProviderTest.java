package com.nempyxa.mathtr.model;

import com.nempyxa.mathtr.model.core.Question;
import com.nempyxa.mathtr.model.string.StringQuestion;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class QuestionProviderTest {

    private HashMap<String, String> mStringQuestions;
    private QuestionProvider mProvider;

    @Before
    public void setUp() throws Exception {
        mStringQuestions = new HashMap<>();
        mStringQuestions.put("2 + 2?", "4");
        mStringQuestions.put("What planet are we on?", "Earth");
        mStringQuestions.put("Which is the biggest ocean?", "Pacific");
        mStringQuestions.put("What's the third day of the week?", "Wednesday");
        mStringQuestions.put("Which language is used to create this SW?", "Java");
        mStringQuestions.put("How many fingers on my left hand?", "5");
        mStringQuestions.put("What color is the sky?", "Blue");
        ArrayList<Question> questions = new ArrayList<>();
        for (Map.Entry<String, String> entry : mStringQuestions.entrySet()) {
            StringQuestion question = new StringQuestion(entry.getKey(), entry.getValue());
            questions.add(question);
        }
        QuestionSource source = new ListQuestionSource(questions);
        mProvider = new QuestionProvider(source);
    }

    @Test
    public void providerInitTest() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);
        mProvider.init((state) -> {
            if (state == QuestionProvider.State.READY) {
                latch.countDown();
            }
        });
        boolean result = latch.await(5000, TimeUnit.MILLISECONDS);
        Assert.assertTrue(result);
        Assert.assertEquals(mProvider.getQueueSize(), mStringQuestions.size());
    }

    @Test(expected = UnsupportedOperationException.class)
    public void providerInitNumberTest() throws InterruptedException {
        int numberToPreload = 3;
        CountDownLatch latch = new CountDownLatch(1);
        mProvider.init(numberToPreload, (state) -> {
            if (state == QuestionProvider.State.READY) {
                latch.countDown();
            }
        });
        boolean result = latch.await(5000, TimeUnit.MILLISECONDS);
        Assert.assertTrue(result);
        Assert.assertEquals(mProvider.getQueueSize(), numberToPreload);
    }
}