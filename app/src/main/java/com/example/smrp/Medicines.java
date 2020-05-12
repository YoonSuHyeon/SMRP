package com.example.smrp;

public class Medicines {
    private String shape;
    private String color;
    private String formula;
    private String line;

    public Medicines(String shape, String color, String formula, String line) {
        this.shape = shape;
        this.color = color;
        this.formula = formula;
        this.line = line;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getShape() {
        return shape;
    }

    public void setShape(String shape) {
        this.shape = shape;
    }

    public String getFormula() {
        return formula;
    }

    public void setFormula(String formula) {
        this.formula = formula;
    }

    public String getLine() {
        return line;
    }

    public void setLine(String line) {
        this.line = line;
    }
}
