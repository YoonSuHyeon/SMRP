package com.example.smrp.ui.search_pharmacy;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

public class Search_Pharmacy_ViewModel {
    private MutableLiveData<String> mText;

    public Search_Pharmacy_ViewModel() {

    }

    public LiveData<String> getText() {
        return mText;
    }
}
