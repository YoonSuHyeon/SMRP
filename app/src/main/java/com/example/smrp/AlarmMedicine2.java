package com.example.smrp;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AlarmMedicine2 {

    @SerializedName("alramGroupId")
    @Expose
    private Integer alramGroupId;
    @SerializedName("userId")
    @Expose
    private String userId;
    @SerializedName("alramName")
    @Expose
    private String alramName;
    @SerializedName("dosingPeriod")
    @Expose
    private Integer dosingPeriod;
    @SerializedName("startAlram")
    @Expose
    private String startAlram;
    @SerializedName("finishAlram")
    @Expose
    private String finishAlram;
    @SerializedName("oneTimeDose")
    @Expose
    private Integer oneTimeDose;
    @SerializedName("oneTimeCapacity")
    @Expose
    private Integer oneTimeCapacity;
    @SerializedName("doseType")
    @Expose
    private String doseType;

    public Integer getAlramGroupId() {
        return alramGroupId;
    }

    public void setAlramGroupId(Integer alramGroupId) {
        this.alramGroupId = alramGroupId;
    }

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

    public String getStartAlram() {
        return startAlram;
    }

    public void setStartAlram(String startAlram) {
        this.startAlram = startAlram;
    }

    public String getFinishAlram() {
        return finishAlram;
    }

    public void setFinishAlram(String finishAlram) {
        this.finishAlram = finishAlram;
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

}