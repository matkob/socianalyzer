package com.mkobiers.socianalyzer.logic;

import com.mkobiers.socianalyzer.model.Matrix;
import com.mkobiers.socianalyzer.model.MatrixCell;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Random;

public class Generator {

    private final static Logger logger = LoggerFactory.getLogger(Generator.class);

    public static Matrix generateTestData(int difficulty, int nodes, String file) {
        Matrix generated = new Matrix();
        Random rnd = new Random();

        double diffRate = (double) difficulty / 100;
        try (PrintWriter writer = new PrintWriter(file)) {
            for (int i = 0; i < nodes; i++) {
                for (int j = 0; j < i; j++) {
                    if (rnd.nextDouble() % 1 < diffRate) {
                        int days = rnd.nextInt() % 100;
                        MatrixCell cell = new MatrixCell(days > 0 ? days : -days + 1);
                        generated.put("node" + i, "node" + j, cell);
                        writer.println("node" + i + " " + "node" + j + " " + cell.getDays());
                    }
                }
            }
        } catch (IOException e) {
            logger.error("error creating output file \"{}\"", file);
            System.exit(1);
        }
        return generated;
    }
}
