package com.example.smrp.inquiry;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class InquiryViewModel extends ViewModel {
    private MutableLiveData<String> mText;

    public InquiryViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is Inquiry fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}
