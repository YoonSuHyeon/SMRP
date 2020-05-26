package com.example.smrp.medicine;

public class ListViewItem {
    private String url;
    private String name;
    private String itemSeq;
    private String time;

    public ListViewItem(String url, String name,String itemSeq,String time) {
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
