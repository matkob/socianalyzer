package com.mkobiers.socianalyzer.model;

/**
 * Mateusz Kobierski
 *
 * Problem nr 14 - Stopien separacji w sieci spolecznosciowej
 */

public class MatrixCell {

    private final int days;
    private final boolean familiar;
    private int path;

    public MatrixCell() {
        this.days = 0;
        this.familiar = false;
        this.path = Integer.MAX_VALUE;
    }

    public MatrixCell(int days) {
        this.days = days;
        this.familiar = true;
        this.path = 1;
    }

    public MatrixCell(int days, int path) {
        this.days = days;
        this.familiar = true;
        this.path = path;
    }

    public int getDays() {
        return days;
    }

    public boolean isFamiliar() {
        return familiar;
    }

    public int getPath() {
        return path;
    }

    public void setPath(int path) {
        this.path = path;
    }

    @Override
    public String toString() {
        return "MatrixCell{" +
                "days=" + days +
                ", familiar=" + familiar +
                ", path=" + path +
                '}';
    }
}
