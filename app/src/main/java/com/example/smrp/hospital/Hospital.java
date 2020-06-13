package com.example.smrp.hospital;

public class Hospital {

    private String yadmNm;
    private String clCdNm;
    private String addr;
    //private String hosurl;
    private String telno;
    private String xPos;
    private String yPos;
    private String distance;

    public Hospital(String yadmNm, String clCdNm, String addr, /*String hosurl,*/String telno, String xPos, String yPos, double distance) {


        this.yadmNm = yadmNm;
        this.clCdNm = clCdNm;
        this.addr = addr;
        //this.hosurl = hosurl;
        this.telno = telno;
        this.xPos = xPos;
        this.yPos = yPos;

        int temp = (int)distance;
        if(temp>=1000) {
            int km = temp / 1000;
            int m = temp % 1000;
            m = m / 100;
            this.distance = Integer.toString(km)+"."+Integer.toString(m)+"km";
        }
        else{
            this.distance = Integer.toString(temp)+"m";
        }

    }


    public String getYadmNm() {
        return yadmNm;
    }

    public void setYadmNm(String yadmNm) {
        this.yadmNm = yadmNm;
    }

    public String getClCdNm() {
        return clCdNm;
    }

    public void setClCdNm(String clCdNm) {
        this.clCdNm = clCdNm;
    }

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

   /* public String getHosurl() {
        return hosurl;
    }

    public void setHosurl(String hosurl) {
        this.hosurl = hosurl;
    }*/

    public String getTelno() {
        return telno;
    }

    public void setTelno(String telno) {
        this.telno = telno;
    }

    public String getxPos() {
        return xPos;
    }

    public void setxPos(String xPos) {
        this.xPos = xPos;
    }

    public String getyPos() {
        return yPos;
    }

    public void setyPos(String yPos) {
        this.yPos = yPos;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = Double.toString(distance);
    }

}
