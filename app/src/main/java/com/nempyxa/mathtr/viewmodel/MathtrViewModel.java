package com.nempyxa.mathtr.viewmodel;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.nempyxa.mathtr.data.Storage;
import com.nempyxa.mathtr.log.Logger;
import com.nempyxa.mathtr.model.QuestionProvider;
import com.nempyxa.mathtr.model.QuestionSource;
import com.nempyxa.mathtr.model.core.Question;
import com.nempyxa.mathtr.model.math.MathAnswer;
import com.nempyxa.mathtr.model.math.MathQuestionGenerationParameters;
import com.nempyxa.mathtr.model.math.MathQuestionSource;

public class MathtrViewModel extends AndroidViewModel {

    private QuestionProvider mProvider;
    private Question mCurrentQuestion;
    private int mQuestionsPlayed;

    private MutableLiveData<String> mQuestionText;

    public MathtrViewModel(Application application) {
        super(application);
        initializeGame();
        SharedPreferences prefs = getApplication().getSharedPreferences(Storage.NAME, Context.MODE_PRIVATE);
        Storage.resetSettingsChanged(getApplication());
        mQuestionText = new MutableLiveData<>("");
    }

    public void checkSettingsChanged() {
        if (Storage.getSettingsChanged(getApplication())) {
            Storage.resetSettingsChanged(getApplication());
            initializeGame();
            // TODO: reset UI
        }
    }

    private void initializeGame() {
        MathQuestionGenerationParameters parameters = Storage.readParameters(getApplication());
        QuestionSource source = new MathQuestionSource(parameters);
        mProvider = new QuestionProvider(source);
        mProvider.init(state -> {
            if (state == QuestionProvider.State.READY) {
                startGame();
            }
        });
    }

    private void startGame() {
        mCurrentQuestion = null;
        mQuestionsPlayed = 0;
        playNext();
    }

    public void playNext() {
        mCurrentQuestion = mProvider.getNext();
        mQuestionsPlayed++;
        Logger.d("Playing question " + mQuestionsPlayed + ": " + mCurrentQuestion.stringPresentation());
        // TODO: why do I have to use postValue here instead of setValue?
        mQuestionText.postValue(mCurrentQuestion.stringPresentation());
    }

    public void verify(String answer) {
        try {
            boolean correct = mCurrentQuestion.verify(new MathAnswer(Integer.parseInt(answer)));
            if (correct) {
                playNext();
            }
        } catch (NumberFormatException nfe) {
            // ignore
        }
    }

    public LiveData<String> getQuestionText() {
        return mQuestionText;
    }
}