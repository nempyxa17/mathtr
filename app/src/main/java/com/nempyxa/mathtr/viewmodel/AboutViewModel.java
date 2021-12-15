package com.nempyxa.mathtr.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.nempyxa.mathtr.BuildConfig;

public class AboutViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public AboutViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Version: " + BuildConfig.VERSION_NAME);
    }

    public LiveData<String> getText() {
        return mText;
    }
}