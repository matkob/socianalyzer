package com.mkobiers.socianalyzer.model;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.stream.Collectors;

public class Matrix {
    private Map<MatrixAddress, MatrixCell> matrix;
    private Set<String> nodes;
    private int separationRate;
    private int mst;

    public Matrix() {
        matrix = new HashMap<>();
        nodes = new HashSet<>();
    }

    public MatrixCell put(String person1, String person2, MatrixCell entry) {
        MatrixAddress address = new MatrixAddress(person1, person2);
        nodes.add(person1);
        nodes.add(person2);
        return matrix.put(address, entry);
    }

    public MatrixCell get(String person1, String person2) {
        if (person1.equals(person2)) {
            return new MatrixCell(0, 0);
        }
        MatrixAddress address = new MatrixAddress(person1, person2);
        return matrix.getOrDefault(address, new MatrixCell());
    }

    public Set<String> getNodes() {
        return nodes;
    }

    public List<MatrixAddress> getEdges() {
        return matrix.entrySet().stream().filter(entry -> entry.getValue().isFamiliar()).map(Map.Entry::getKey).collect(Collectors.toList());
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

    public int getMST() {
        return mst;
    }

    public void setMST(int mst) {
        this.mst = mst;
    }
}
