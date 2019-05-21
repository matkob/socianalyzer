package com.mkobiers.socianalyzer.algo;

import com.mkobiers.socianalyzer.input.InputReader;
import com.mkobiers.socianalyzer.model.Matrix;
import org.junit.jupiter.api.Test;

import java.util.List;

public class KruskalMSTTest {

    @Test
    public void basicTest() {
        InputReader reader = new InputReader();
        Matrix input = reader.readInputData();
        FloydWarshall.calcShortestPaths(input);
        List<Matrix> integrals = FloydWarshall.extractIntegralMatrices(input);
        integrals.forEach(FloydWarshall::calcSeparationRate);
        integrals.forEach(KruskalMST::calcMST);
        integrals.forEach(matrix -> {
            System.out.println(matrix.getSeparationRate());
            System.out.println(matrix.getMST());
        });
    }

}
