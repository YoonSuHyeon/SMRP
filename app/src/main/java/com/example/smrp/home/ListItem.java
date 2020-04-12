package com.example.smrp.home;

import android.graphics.drawable.Drawable;

public class ListItem {
    private Drawable Dra_icon ;
    private String Str_date ;
    private String Str_contents ;
    private String Str_name;

    public void setIcon(Drawable icon) {
        Dra_icon = icon ;
    }
    public void setName(String n) {
        Str_name = n ;
    }
    public void setContents(String c) {
        Str_contents = c ;
    }
    public void setDate(String d) {
        Str_date = d ;
    }
    public Drawable getIcon() {
        return this.Dra_icon ;
    }
    public String getName() {
        return this.Str_name ;
    }
    public String getContents() {
        return this.Str_contents;
    }
    public String getDate() {
        return this.Str_date;
    }
}