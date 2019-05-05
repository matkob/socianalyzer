package com.mkobiers.socianalyzer.model;

import java.util.Objects;

public class MatrixAddress {
    private int address;
    private String person1;
    private String person2;

    MatrixAddress(String person1, String person2) {
        if (person1.compareTo(person2) >= 0) {
            this.address = Objects.hash(person1, person2);
            this.person1 = person1;
            this.person2 = person2;
        } else {
            this.address = Objects.hash(person2, person1);
            this.person1 = person2;
            this.person2 = person1;
        }
    }

    public String getPerson1() {
        return person1;
    }

    public String getPerson2() {
        return person2;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof MatrixAddress) {
            return this.address == ((MatrixAddress) obj).address;
        }
        return false;
    }

    @Override
    public int hashCode() {
        return this.address;
    }

    @Override
    public String toString() {
        return "MatrixAddress{" +
                "person1='" + person1 + '\'' +
                ", person2='" + person2 + '\'' +
                '}';
    }
}
