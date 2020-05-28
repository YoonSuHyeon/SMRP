package com.example.smrp.alarm;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.time.LocalDate;
import java.util.ArrayList;

public class Response_AlarmMedicine {
    @SerializedName("alramGroupId")
    @Expose
    private Long alramGroupId; // 알람 아이디 (자동입력)

    @SerializedName("userId")
    @Expose
    private String userId; //유저아이디

    @SerializedName("alramName")
    @Expose
    private String alramName; //알람명

    @SerializedName("dosingPeriod")
    @Expose
    private Integer dosingPeriod; //복용기간

    @SerializedName("startAlram")
    @Expose
    private String startAlram; // 알람 등록 일자

    @SerializedName("finishAlram")
    @Expose
    private String finishAlram; // 알람종료 일자

    @SerializedName("oneTimeDose")
    @Expose
    private Integer oneTimeDose; //1회 복용량

    @SerializedName("oneTimeCapacity")
    @Expose
    private Integer oneTimeCapacity; //1일 복용량

    @SerializedName("doseType")
    @Expose
    private String doseType; //복용타입

    /*@SerializedName("itemSeqs")
    @Expose
    private ArrayList<String> itemSeqs; //약들의 일련번호
*/
    public Long getAlramGroupId() {
        return alramGroupId;
    }

    public void setAlramGroupId(Long alramGroupId) {
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
