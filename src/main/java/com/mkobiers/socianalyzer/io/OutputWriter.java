package com.mkobiers.socianalyzer.io;

import com.mkobiers.socianalyzer.model.Matrix;
import org.apache.commons.lang3.tuple.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Comparator;
import java.util.List;
import java.util.function.Function;

/**
 * Mateusz Kobierski
 *
 * Problem nr 14 - Stopien separacji w sieci spolecznosciowej
 */

public class OutputWriter {

    private final static Logger logger = LoggerFactory.getLogger(OutputWriter.class);
    private Function<Integer, Long> bigOFunc;
    private String outFile;

    public OutputWriter(String path) {
        this.outFile = path;
        this.bigOFunc = (n) -> (long) (n * n * n);
    }

    public void writeResults(List<Matrix> matrices, boolean verbose) {
        try (PrintWriter printWriter = new PrintWriter(outFile)) {
            for (Matrix matrix : matrices) {
                printWriter.println("Network of " + matrix.getNodes().size() + " people");
                printWriter.println("Separation rate: " + matrix.getSeparationRate());
                printWriter.println("MST: " + matrix.getMST() + "\n");
                if (verbose) {
                    logger.info("Network of {} people", matrix.getNodes().size());
                    logger.info("Separation rate: {}", matrix.getSeparationRate());
                    logger.info("MST: {}\n", matrix.getMST());
                }
            }
        } catch (IOException e) {
            logger.error("error creating output file \"{}\"", outFile);
            System.exit(1);
        }
    }

    public void writeTimeMeasurements(List<Pair<Integer, Long>> times) {
        times.sort(Comparator.comparingInt(Pair::getLeft));
        int medianIndex = times.size() > 1 ? times.size()/2 : 0;
        long medianPractical = times.get(medianIndex).getRight();
        long medianTheoretical = bigOFunc.apply(times.get(medianIndex).getLeft());

        try (PrintWriter printWriter = new PrintWriter(outFile)) {
            times.forEach(time -> {
                printWriter.println(time.getLeft() + "    " + time.getRight() + "    " + calcAccuracy(time.getRight(), time.getLeft(), medianPractical, medianTheoretical));
                logger.info("{}    {}    {}", time.getLeft(), time.getRight(), calcAccuracy(time.getRight(), time.getLeft(), medianPractical, medianTheoretical));
            });
        } catch (IOException e) {
            logger.error("error creating output file \"{}\"", outFile);
            System.exit(1);
        }

    }

    private double calcAccuracy(long actual, int n, long medianPractical, long medianTheoretical) {
        return ((double)actual*medianTheoretical)/(bigOFunc.apply(n)*medianPractical);
    }
}
