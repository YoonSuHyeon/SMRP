package com.example.smrp.searchPrescription;

public class Prescriptionitem {
    private String itemSeq;
    private String stringURL;
    private String text1,text2,text3,text4;



    public Prescriptionitem(String itemSeq, String stringURL, String text1, String text2/*, String text3, String text4*/) { //약 식별번호 / 약 이미지 / 약 이름 / 약 제조사 / 약 포장 /약 의약품정보(일반, 전문)
        this.itemSeq = itemSeq; //알약 식별코드
        this.stringURL = stringURL; //알약 이미지
        this.text1 = text1; //알약 식별포장
        this.text2 = text2; //알약 제품명
        /*this.text3 = text3;//알약 포장
        this.text4 = text4; //약 의약품 정보*/
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

   /* public String getText3() {
        return text3;
    }

    public String getText4() {
        return text4;
    }*/


}
