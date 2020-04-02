package com.example.smrp.mask;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MaskViewModel extends ViewModel {
    private MutableLiveData<String> mText;

    public MaskViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is mask fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}
