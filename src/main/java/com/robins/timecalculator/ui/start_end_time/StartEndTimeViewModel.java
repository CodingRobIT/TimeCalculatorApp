package com.robins.timecalculator.ui.start_end_time;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class StartEndTimeViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public StartEndTimeViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is dashboard fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}