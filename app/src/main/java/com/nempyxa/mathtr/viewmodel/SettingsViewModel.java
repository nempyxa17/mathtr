package com.nempyxa.mathtr.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.nempyxa.mathtr.data.Storage;
import com.nempyxa.mathtr.model.math.MathQuestionGenerationParameters;
import com.nempyxa.mathtr.model.math.MathQuestionGenerator;
import com.nempyxa.mathtr.model.math.Operation;

import java.util.ArrayList;

public class SettingsViewModel extends AndroidViewModel {

    private MutableLiveData<String> mText;

    public SettingsViewModel(Application application) {
        super(application);
        mText = new MutableLiveData<>();
        mText.setValue("This is settings fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }

    public MathQuestionGenerationParameters getSetting() {
        return Storage.readParameters(getApplication());
    }

    public void attemptSettingsChange(int leftMin, int leftMax, int rightMin, int rightMax, boolean addition, boolean multiplication, boolean division, boolean subtraction) {
        ArrayList<Operation> operations = new ArrayList<>();
        if (addition) operations.add(Operation.ADDITION);
        if (multiplication) operations.add(Operation.MULTIPLICATION);
        if (division) operations.add(Operation.DIVISION);
        if (subtraction) operations.add(Operation.SUBTRACTION);
        // TODO: does not look line to validate parameters in constructors - rework.
        MathQuestionGenerationParameters parameters = new MathQuestionGenerationParameters(leftMin, leftMax, rightMin, rightMax, operations);
        MathQuestionGenerator generator = new MathQuestionGenerator(parameters);
        Storage.storeParameters(parameters, getApplication());
        Storage.triggerSettingsChanged(getApplication());
    }
}