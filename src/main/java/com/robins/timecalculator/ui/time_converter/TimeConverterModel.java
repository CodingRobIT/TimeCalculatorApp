package com.robins.timecalculator.ui.time_converter;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class TimeConverterModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public TimeConverterModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is Time Calculator fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}