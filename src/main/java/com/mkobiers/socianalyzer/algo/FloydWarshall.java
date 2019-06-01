package com.mkobiers.socianalyzer.algo;

import com.mkobiers.socianalyzer.model.Matrix;
import com.mkobiers.socianalyzer.model.MatrixCell;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

public class FloydWarshall {

    public static void calcShortestPaths(Matrix input) {
        Set<String> vertices = input.getVertices();
        vertices.forEach(v1 -> {
            vertices.forEach(v2 -> {
                vertices.forEach(v3 -> {
                    int newPath = input.get(v2, v1).getPath() + input.get(v1, v3).getPath();
                    if (newPath >= 0 && newPath < input.get(v2, v3).getPath()) {
                        MatrixCell newConn = input.get(v2, v3);
                        newConn.setPath(newPath);
                        input.put(v2, v3, newConn);
                    }
                });
            });
        });
    }

    public static void calcSeparationRate(Matrix matrix) {
        AtomicInteger rate = new AtomicInteger(-1);
        matrix.forEach((matrixAddress, matrixCell) -> {
            if (matrixCell.getPath() > rate.get()) {
                rate.set(matrixCell.getPath());
            }
        });
        matrix.setSeparationRate(rate.get());
    }

    public static List<Matrix> extractIntegralMatrices(Matrix input) {
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
        Set<String> vertices = input.getVertices();
        checked.add(vertice);

        vertices.forEach(v -> {
            if (!checked.contains(v) && input.get(vertice, v).getPath() != Integer.MAX_VALUE) {
                m.put(vertice, v, input.get(vertice, v));
            }
        });
        vertices.forEach(v -> {
            if (!checked.contains(v) && input.get(vertice, v).getPath() != Integer.MAX_VALUE) {
                analyzeConnections(input, m, checked, v);
            }
        });
    }
}
