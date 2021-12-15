package com.nempyxa.mathtr.model.math;

import com.nempyxa.mathtr.model.ListQuestionSource;
import com.nempyxa.mathtr.model.QuestionProvider;
import com.nempyxa.mathtr.model.QuestionSource;
import com.nempyxa.mathtr.model.core.Question;
import com.nempyxa.mathtr.model.string.StringQuestion;

import org.junit.After;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.*;

public class MathQuestionSourceTest {

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void someTest() throws InterruptedException {
        List<Operation> operations = Arrays.asList(Operation.ADDITION, Operation.SUBTRACTION, Operation.MULTIPLICATION, Operation.DIVISION);
        MathQuestionGenerationParameters params = new MathQuestionGenerationParameters(1, 9, 1, 9, operations);
        QuestionSource source = new MathQuestionSource(params);
        QuestionProvider mProvider = new QuestionProvider(source);
        CountDownLatch latch = new CountDownLatch(1);
        mProvider.init((state) -> {
            if (state == QuestionProvider.State.READY) {
                latch.countDown();
            }
        });
        boolean result = latch.await(5000, TimeUnit.MILLISECONDS);
        Assert.assertTrue(result);
    }
}