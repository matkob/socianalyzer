package com.mkobiers.socianalyzer.algo;

import com.mkobiers.socianalyzer.model.Matrix;
import com.mkobiers.socianalyzer.model.MatrixCell;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

public class FloydWarshall {

    public static void analyze(Matrix input) {
        Set<String> vertices = input.getVertices();
        vertices.forEach(v1 -> {
            vertices.forEach(v2 -> {
                vertices.forEach(v3 -> {
                    int newFamiliarity = input.get(v2, v1).getFamiliarity() + input.get(v1, v3).getFamiliarity();
                    if (newFamiliarity >= 0 && newFamiliarity < input.get(v2, v3).getFamiliarity()) {
                        MatrixCell newConn = input.get(v2, v3);
                        newConn.setFamiliarity(newFamiliarity);
                        input.add(v2, v3, newConn);
                    }
                });
            });
        });
    }

    public static List<Matrix> getIntegralMatrices(Matrix input) {
        Set<String> vertices = input.getVertices();
        Set<String> checkedVertices = new HashSet<>();
        List<Matrix> matrices = new ArrayList<>();
        vertices.forEach(v1 -> {
            if (!checkedVertices.contains(v1)) {
                Matrix m = new Matrix();
                analyzeConnections(input, m, checkedVertices, v1);
                matrices.add(m);

            }
        });
        return matrices;
    }

    private static void analyzeConnections(Matrix input, Matrix m, Set<String> checked, String vertice) {
        checked.add(vertice);
        input.getVertices().forEach(v -> {
            if (!checked.contains(v) && input.get(vertice, v).getFamiliarity() != Integer.MAX_VALUE) {
                m.add(vertice, v, input.get(vertice, v));
            }
        });
        input.getVertices().forEach(v -> {
            if (!checked.contains(v) && input.get(vertice, v).getFamiliarity() != Integer.MAX_VALUE) {
                analyzeConnections(input, m, checked, v);
            }
        });
    }

    public static void calcSeparationRate(List<Matrix> matrices) {
        matrices.forEach(m -> {
            AtomicInteger rate = new AtomicInteger(-1);
            m.forEach((matrixAddress, matrixCell) -> {
                if (matrixCell.getFamiliarity() > rate.get()) {
                    rate.set(matrixCell.getFamiliarity());
                }
            });
            m.setSeparationRate(rate.get());
        });
    }
}
