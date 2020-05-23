package com.example.smrp.searchMed;

import java.util.ArrayList;

public class SelectedItem {
    String medicineName;
    ArrayList<String> shape;
    ArrayList<String> color;
    ArrayList<String> formula;
    ArrayList<String> line;

    public SelectedItem(String medicineName,ArrayList<String> shape, ArrayList<String> color, ArrayList<String> formula, ArrayList<String> line) {
        this.medicineName=medicineName;
        this.shape = shape;
        this.color = color;
        this.formula = formula;
        this.line = line;
    }

    public String getMedicineName() {
        return medicineName;
    }

    public void setMedicineName(String medicineName) {
        this.medicineName = medicineName;
    }

    public ArrayList<String> getShape() {
        return shape;
    }

    public void setShape(ArrayList<String> shape) {
        this.shape = shape;
    }

    public ArrayList<String> getColor() {
        return color;
    }

    public void setColor(ArrayList<String> color) {
        this.color = color;
    }

    public ArrayList<String> getFormula() {
        return formula;
    }

    public void setFormula(ArrayList<String> formula) {
        this.formula = formula;
    }

    public ArrayList<String> getLine() {
        return line;
    }

    public void setLine(ArrayList<String> line) {
        this.line = line;
    }
}
