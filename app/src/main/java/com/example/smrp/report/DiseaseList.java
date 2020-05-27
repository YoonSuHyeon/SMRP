package com.example.smrp.report;

public class DiseaseList {
    private String Str_disease;
    private String Str_symptom;
    private String Str_probability;
    private String Str_department;


    public void setDisease(String d){Str_disease = d;}
    public String getDisease(){return Str_disease;}

    public void setSymptom(String s){Str_symptom = s;}
    public String getSymptom(){return Str_symptom;}

    public void setProbability(String p){Str_probability = p;}
    public String getProbability(){return Str_probability;}

    public void setDepartment(String d){Str_department = d;}
    public String getDepartment(){return Str_department;}

}
