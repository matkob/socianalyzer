package com.mkobiers.socianalyzer.algo;

import com.mkobiers.socianalyzer.model.Matrix;
import com.mkobiers.socianalyzer.model.MatrixAddress;

import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Mateusz Kobierski
 *
 * Problem nr 14 - Stopien separacji w sieci spolecznosciowej
 */

public class KruskalMST {

    public static void calcMST(Matrix matrix) {
        List<MatrixAddress> edges = matrix.getEdges();
        Set<String> visited = new HashSet<>();
        Set<String> nodes = matrix.getNodes();
        edges.sort(Comparator.comparingInt(address -> matrix.get(address.getPerson1(), address.getPerson2()).getDays()));

        int mst = 0;
        for (int i = 0; visited.size() < nodes.size(); i++) {
            if (!(visited.contains(edges.get(i).getPerson1()) && visited.contains(edges.get(i).getPerson2()))) {
                visited.add(edges.get(i).getPerson1());
                visited.add(edges.get(i).getPerson2());
                mst += matrix.get(edges.get(i).getPerson1(), edges.get(i).getPerson2()).getDays();
            }
        }
        matrix.setMST(mst);
    }
}
