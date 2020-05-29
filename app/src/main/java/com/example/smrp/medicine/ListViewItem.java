package com.example.smrp.medicine;

import java.io.Serializable;

public class ListViewItem implements Serializable {
    private String url; //약 이미지
    private String name; //약 이름
    private String itemSeq; //약 식별번호
    private String time; //시간

    public ListViewItem(String url, String name,String itemSeq,String time){
        this.url = url;
        this.name = name;
        this.itemSeq=itemSeq;
        this.time=time;
    }
    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getItemSeq() {
        return itemSeq;
    }

    public void setItemSeq(String itemSeq) {
        this.itemSeq = itemSeq;
    }




    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


}
