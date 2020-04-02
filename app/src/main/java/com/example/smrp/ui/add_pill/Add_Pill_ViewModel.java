package com.example.smrp.ui.add_pill;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class Add_Pill_ViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public Add_Pill_ViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This page is add_pill_fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}