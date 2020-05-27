package com.example.smrp.alarm;

import java.time.LocalDate;
import java.util.ArrayList;

public class AlarmMedicine {
    private String userId; //유저아이디
    private String alramName; //알람명
    private Integer dosingPeriod; //복용기간
    private Integer oneTimeDose; //1회 복용량
    private Integer oneTimeCapacity; //1일 복용량
    private String doseType; //복용타입
    private ArrayList<String> itemSeqs; //약들의 일련번호

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getAlramName() {
        return alramName;
    }

    public void setAlramName(String alramName) {
        this.alramName = alramName;
    }

    public Integer getDosingPeriod() {
        return dosingPeriod;
    }

    public void setDosingPeriod(Integer dosingPeriod) {
        this.dosingPeriod = dosingPeriod;
    }

    public Integer getOneTimeDose() {
        return oneTimeDose;
    }

    public void setOneTimeDose(Integer oneTimeDose) {
        this.oneTimeDose = oneTimeDose;
    }

    public Integer getOneTimeCapacity() {
        return oneTimeCapacity;
    }

    public void setOneTimeCapacity(Integer oneTimeCapacity) {
        this.oneTimeCapacity = oneTimeCapacity;
    }

    public String getDoseType() {
        return doseType;
    }

    public void setDoseType(String doseType) {
        this.doseType = doseType;
    }

    public ArrayList<String> getItemSeqs() {
        return itemSeqs;
    }

    public void setItemSeqs(ArrayList<String> itemSeqs) {
        this.itemSeqs = itemSeqs;
    }

    public AlarmMedicine(String userId, String alramName, Integer dosingPeriod, Integer oneTimeDose, Integer oneTimeCapacity, String doseType, ArrayList<String> itemSeqs) {
        this.userId = userId;
        this.alramName = alramName;
        this.dosingPeriod = dosingPeriod;
        this.oneTimeDose = oneTimeDose;
        this.oneTimeCapacity = oneTimeCapacity;
        this.doseType = doseType;
        this.itemSeqs = itemSeqs;
    }

}

