package com.example.smrp.hospital;

import com.google.gson.annotations.SerializedName;

public class Response_body {
    @SerializedName("header") Header header;//List<Header> header_list;
    //public List<Header> getheader_list(){return header_list;}

    @SerializedName("body") Body body;

}
