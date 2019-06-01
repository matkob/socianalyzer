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
        Set<String> nodes = input.getNodes();
        nodes.forEach(n1 -> {
            nodes.forEach(n2 -> {
                nodes.forEach(n3 -> {
                    int newPath = input.get(n2, n1).getPath() + input.get(n1, n3).getPath();
                    if (newPath >= 0 && newPath < input.get(n2, n3).getPath()) {
                        MatrixCell newConn = input.get(n2, n3);
                        newConn.setPath(newPath);
                        input.put(n2, n3, newConn);
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
        Set<String> nodes = input.getNodes();
        Set<String> visited = new HashSet<>();
        List<Matrix> matrices = new ArrayList<>();
        nodes.forEach(v1 -> {
            if (!visited.contains(v1)) {
                Matrix m = new Matrix();
                analyzeConnections(input, m, visited, v1);
                matrices.add(m);

            }
        });
        return matrices;
    }

    private static void analyzeConnections(Matrix input, Matrix m, Set<String> checked, String node) {
        Set<String> nodes = input.getNodes();
        checked.add(node);

        nodes.forEach(n -> {
            if (!checked.contains(n) && input.get(node, n).getPath() != Integer.MAX_VALUE) {
                m.put(node, n, input.get(node, n));
            }
        });
        nodes.forEach(n -> {
            if (!checked.contains(n) && input.get(node, n).getPath() != Integer.MAX_VALUE) {
                analyzeConnections(input, m, checked, n);
            }
        });
    }
}
