package com.example.smrp.inquiry;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class InquiryViewModel extends ViewModel {
    private MutableLiveData<String> mText;

    public InquiryViewModel() {

    }

    public LiveData<String> getText() {
        return mText;
    }
}
