package com.example.smrp.lookup;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class LookUpViewModel extends ViewModel {
    private MutableLiveData<String> mText;

    public LookUpViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is lookup fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}
