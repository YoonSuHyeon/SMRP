package com.example.smrp.searchPrescription;

public class Prescriptionitem {
    private String itemSeq;
    private String stringURL;
    private String text1,text2,text3,text4;



    public Prescriptionitem(String itemSeq, String stringURL, String text1, String text2, String text3, String text4) {
        this.itemSeq = itemSeq;
        this.stringURL = stringURL;
        this.text1 = text1;
        this.text2 = text2;
        this.text3 = text3;
        this.text4 = text4;
    }
    public String getItemSeq() {
        return itemSeq;
    }

    public String getStringURL() {
        return stringURL;
    }

    public String getText1() {
        return text1;
    }

    public String getText2() {
        return text2;
    }

    public String getText3() {
        return text3;
    }

    public String getText4() {
        return text4;
    }


}
