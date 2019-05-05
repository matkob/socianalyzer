package com.mkobiers.socianalyzer.model;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;

public class Matrix {
    private Map<MatrixAddress, MatrixCell> matrix;
    private Set<String> vertices;
    private int separationRate;

    public Matrix() {
        matrix = new HashMap<>();
        vertices = new HashSet<>();
    }

    public MatrixCell add(String person1, String person2, MatrixCell entry) {
        MatrixAddress address = new MatrixAddress(person1, person2);
        vertices.add(person1);
        vertices.add(person2);
        return matrix.put(address, entry);
    }

    public MatrixCell get(String person1, String person2) {
        if (person1.equals(person2)) {
            return new MatrixCell(0, 0);
        }
        MatrixAddress address = new MatrixAddress(person1, person2);
        return matrix.getOrDefault(address, new MatrixCell());
    }

    public Set<String> getVertices() {
        return vertices;
    }

    public void forEach(BiConsumer<? super MatrixAddress, ? super MatrixCell> func) {
        matrix.forEach(func);
    }

    public int getSeparationRate() {
        return separationRate;
    }

    public void setSeparationRate(int separationRate) {
        this.separationRate = separationRate;
    }
}
