package com.example.smrp.searchMed;

import android.graphics.drawable.Drawable;

public class ListItem {
    private Drawable Dra_icon ;

    private String Str_name;
    private int viewType;
    private String Str_text;
    public void setIcon(Drawable icon) {
        Dra_icon = icon ;
    }
    public void setName(String n) {
        Str_name = n ;
    }
    public void setViewType(int v){viewType = v;}
    public void setText(String t) {
        Str_text = t;
    }
    public Drawable getIcon() {
        return this.Dra_icon ;
    }
    public String getName() {
        return this.Str_name ;
    }
    public int getViewType() {
        return viewType;
    }
    public String getText() {
        return Str_text;
    }
}