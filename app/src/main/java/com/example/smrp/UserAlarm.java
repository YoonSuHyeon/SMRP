package com.example.smrp;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserAlarm {

    @SerializedName("userName")
    @Expose
    private String userName;
    @SerializedName("alramMedicines")
    @Expose
    private List<AlarmMedicine2> alramMedicines = null;

    public String getUserName() {
        return userName;
    }

    public void setUserId(String userName) {
        this.userName = userName;
    }

    public List<AlarmMedicine2> getAlramMedicines() {
        return alramMedicines;
    }

    public void setAlramMedicines(List<AlarmMedicine2> alramMedicines) {
        this.alramMedicines = alramMedicines;
    }

}