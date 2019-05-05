package com.mkobiers.socianalyzer.model;

public class MatrixCell {

    private int days;
    private int familiarity;

    public MatrixCell() {
        this.days = -1;
        this.familiarity = Integer.MAX_VALUE;
    }

    public MatrixCell(int days) {
        this.days = days;
        this.familiarity = 1;
    }

    public MatrixCell(int days, int familiarity) {
        this.days = days;
        this.familiarity = familiarity;
    }

    public int getDays() {
        return days;
    }

    public void setDays(int days) {
        this.days = days;
    }

    public int getFamiliarity() {
        return familiarity;
    }

    public void setFamiliarity(int familiarity) {
        this.familiarity = familiarity;
    }

    @Override
    public String toString() {
        return "MatrixCell{" +
                "days=" + days +
                ", familiarity=" + familiarity +
                '}';
    }
}
