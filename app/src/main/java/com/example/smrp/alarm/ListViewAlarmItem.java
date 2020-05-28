package com.example.smrp.alarm;

import java.time.LocalDate;

public class ListViewAlarmItem {
    private String alramName;
    private String startAlram;
    private Long alramGroupId;

    public ListViewAlarmItem(String alramName, String startAlram, Long alramGroupId) {
        this.alramName = alramName;
        this.startAlram = startAlram;
        this.alramGroupId = alramGroupId;
    }

    public String getAlramName() {
        return alramName;
    }

    public void setAlramName(String alramName) {
        this.alramName = alramName;
    }

    public String getStartAlram() {
        return startAlram;
    }

    public void setStartAlram(String startAlram) {
        this.startAlram = startAlram;
    }

    public Long getAlramGroupId() {
        return alramGroupId;
    }

    public void setAlramGroupId(Long alramGroupId) {
        this.alramGroupId = alramGroupId;
    }
}
