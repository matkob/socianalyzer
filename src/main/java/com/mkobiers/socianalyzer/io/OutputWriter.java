package com.mkobiers.socianalyzer.io;

import com.mkobiers.socianalyzer.model.Matrix;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

/**
 * Mateusz Kobierski
 *
 * Problem nr 14 - Stopien separacji w sieci spolecznosciowej
 */

public class OutputWriter {

    private final static Logger logger = LoggerFactory.getLogger(OutputWriter.class);
    private String outFile;

    public OutputWriter(String path) {
        this.outFile = path;
    }

    public void writeResults(List<Matrix> matrices, boolean verbose) {
        try (PrintWriter printWriter = new PrintWriter(outFile)) {
            for (Matrix matrix : matrices) {
                printWriter.println("Network of " + matrix.getNodes().size() + " people");
                printWriter.println("Separation rate: " + matrix.getSeparationRate());
                printWriter.println("MST: " + matrix.getMST() + "\n");
                if (verbose) {
                    logger.info("Network of " + matrix.getNodes().size() + " people");
                    logger.info("Separation rate: " + matrix.getSeparationRate());
                    logger.info("MST: " + matrix.getMST() + "\n");
                }
            }
        } catch (IOException e) {
            logger.error("error creating output file \"{}\"", outFile);
            System.exit(1);
        }
    }
}
