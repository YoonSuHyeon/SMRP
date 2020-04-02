package com.example.smrp.pharmacy;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class PharmacyViewModel extends ViewModel {
    private MutableLiveData<String> mText;

    public PharmacyViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is Pharmacy fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}
