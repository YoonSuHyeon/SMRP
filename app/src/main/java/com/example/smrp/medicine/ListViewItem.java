package com.example.smrp.medicine;

public class ListViewItem {
    private String url;
    private String name;

    public String getItemSeq() {
        return itemSeq;
    }

    public void setItemSeq(String itemSeq) {
        this.itemSeq = itemSeq;
    }

    private String itemSeq;
    public ListViewItem(String url, String name,String itemSeq) {
        this.url = url;
        this.name = name;
        this.itemSeq=itemSeq;
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
