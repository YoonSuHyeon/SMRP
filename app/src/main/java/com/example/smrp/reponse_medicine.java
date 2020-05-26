package com.example.smrp;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class reponse_medicine implements Serializable {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("itemSeq")
    @Expose
    private String itemSeq;
    @SerializedName("itemName")
    @Expose
    private String itemName;
    @SerializedName("entpSeq")
    @Expose
    private String entpSeq;
    @SerializedName("entpName")
    @Expose
    private String entpName;
    @SerializedName("chart")
    @Expose
    private String chart;
    @SerializedName("itemImage")
    @Expose
    private String itemImage;
    @SerializedName("printFront")
    @Expose
    private String printFront;
    @SerializedName("printBack")
    @Expose
    private String printBack;
    @SerializedName("drugShape")
    @Expose
    private String drugShape;
    @SerializedName("colorClass1")
    @Expose
    private String colorClass1;
    @SerializedName("colorClass2")
    @Expose
    private Object colorClass2;
    @SerializedName("lineFront")
    @Expose
    private String lineFront;
    @SerializedName("lineBack")
    @Expose
    private Object lineBack;
    @SerializedName("lengLong")
    @Expose
    private String lengLong;
    @SerializedName("lengShort")
    @Expose
    private String lengShort;
    @SerializedName("thick")
    @Expose
    private String thick;
    @SerializedName("imgRegistRs")
    @Expose
    private Object imgRegistRs;
    @SerializedName("classNo")
    @Expose
    private String classNo;
    @SerializedName("className")
    @Expose
    private String className;
    @SerializedName("etcOtcName")
    @Expose
    private String etcOtcName;
    @SerializedName("itemPermitDate")
    @Expose
    private String itemPermitDate;
    @SerializedName("formCodeName")
    @Expose
    private String formCodeName;
    @SerializedName("markCodeFrontAnal")
    @Expose
    private String markCodeFrontAnal;
    @SerializedName("markCodeBackAnal")
    @Expose
    private Object markCodeBackAnal;
    @SerializedName("markCodeFrontImg")
    @Expose
    private String markCodeFrontImg;
    @SerializedName("markCodeBackImg")
    @Expose
    private Object markCodeBackImg;
    @SerializedName("itemEngName")
    @Expose
    private Object itemEngName;
    @SerializedName("changeDate")
    @Expose
    private String changeDate;
    @SerializedName("markCodeFront")
    @Expose
    private Object markCodeFront;
    @SerializedName("markCodeBack")
    @Expose
    private Object markCodeBack;
    @SerializedName("ediCode")
    @Expose
    private Object ediCode;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getItemSeq() {
        return itemSeq;
    }

    public void setItemSeq(String itemSeq) {
        this.itemSeq = itemSeq;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getEntpSeq() {
        return entpSeq;
    }

    public void setEntpSeq(String entpSeq) {
        this.entpSeq = entpSeq;
    }

    public String getEntpName() {
        return entpName;
    }

    public void setEntpName(String entpName) {
        this.entpName = entpName;
    }

    public String getChart() {
        return chart;
    }

    public void setChart(String chart) {
        this.chart = chart;
    }

    public String getItemImage() {
        return itemImage;
    }

    public void setItemImage(String itemImage) {
        this.itemImage = itemImage;
    }

    public String getPrintFront() {
        return printFront;
    }

    public void setPrintFront(String printFront) {
        this.printFront = printFront;
    }

    public String getPrintBack() {
        return printBack;
    }

    public void setPrintBack(String printBack) {
        this.printBack = printBack;
    }

    public String getDrugShape() {
        return drugShape;
    }

    public void setDrugShape(String drugShape) {
        this.drugShape = drugShape;
    }

    public String getColorClass1() {
        return colorClass1;
    }

    public void setColorClass1(String colorClass1) {
        this.colorClass1 = colorClass1;
    }

    public Object getColorClass2() {
        return colorClass2;
    }

    public void setColorClass2(Object colorClass2) {
        this.colorClass2 = colorClass2;
    }

    public String getLineFront() {
        return lineFront;
    }

    public void setLineFront(String lineFront) {
        this.lineFront = lineFront;
    }

    public Object getLineBack() {
        return lineBack;
    }

    public void setLineBack(Object lineBack) {
        this.lineBack = lineBack;
    }

    public String getLengLong() {
        return lengLong;
    }

    public void setLengLong(String lengLong) {
        this.lengLong = lengLong;
    }

    public String getLengShort() {
        return lengShort;
    }

    public void setLengShort(String lengShort) {
        this.lengShort = lengShort;
    }

    public String getThick() {
        return thick;
    }

    public void setThick(String thick) {
        this.thick = thick;
    }

    public Object getImgRegistRs() {
        return imgRegistRs;
    }

    public void setImgRegistRs(Object imgRegistRs) {
        this.imgRegistRs = imgRegistRs;
    }

    public String getClassNo() {
        return classNo;
    }

    public void setClassNo(String classNo) {
        this.classNo = classNo;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getEtcOtcName() {
        return etcOtcName;
    }

    public void setEtcOtcName(String etcOtcName) {
        this.etcOtcName = etcOtcName;
    }

    public String getItemPermitDate() {
        return itemPermitDate;
    }

    public void setItemPermitDate(String itemPermitDate) {
        this.itemPermitDate = itemPermitDate;
    }

    public String getFormCodeName() {
        return formCodeName;
    }

    public void setFormCodeName(String formCodeName) {
        this.formCodeName = formCodeName;
    }

    public String getMarkCodeFrontAnal() {
        return markCodeFrontAnal;
    }

    public void setMarkCodeFrontAnal(String markCodeFrontAnal) {
        this.markCodeFrontAnal = markCodeFrontAnal;
    }

    public Object getMarkCodeBackAnal() {
        return markCodeBackAnal;
    }

    public void setMarkCodeBackAnal(Object markCodeBackAnal) {
        this.markCodeBackAnal = markCodeBackAnal;
    }

    public String getMarkCodeFrontImg() {
        return markCodeFrontImg;
    }

    public void setMarkCodeFrontImg(String markCodeFrontImg) {
        this.markCodeFrontImg = markCodeFrontImg;
    }

    public Object getMarkCodeBackImg() {
        return markCodeBackImg;
    }

    public void setMarkCodeBackImg(Object markCodeBackImg) {
        this.markCodeBackImg = markCodeBackImg;
    }

    public Object getItemEngName() {
        return itemEngName;
    }

    public void setItemEngName(Object itemEngName) {
        this.itemEngName = itemEngName;
    }

    public String getChangeDate() {
        return changeDate;
    }

    public void setChangeDate(String changeDate) {
        this.changeDate = changeDate;
    }

    public Object getMarkCodeFront() {
        return markCodeFront;
    }

    public void setMarkCodeFront(Object markCodeFront) {
        this.markCodeFront = markCodeFront;
    }

    public Object getMarkCodeBack() {
        return markCodeBack;
    }

    public void setMarkCodeBack(Object markCodeBack) {
        this.markCodeBack = markCodeBack;
    }

    public Object getEdiCode() {
        return ediCode;
    }

    public void setEdiCode(Object ediCode) {
        this.ediCode = ediCode;
    }

}
