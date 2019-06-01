package com.mkobiers.socianalyzer;

import com.mkobiers.socianalyzer.algo.FloydWarshall;
import com.mkobiers.socianalyzer.algo.KruskalMST;
import com.mkobiers.socianalyzer.io.Generator;
import com.mkobiers.socianalyzer.io.InputReader;
import com.mkobiers.socianalyzer.io.OutputWriter;
import com.mkobiers.socianalyzer.model.Matrix;
import org.apache.commons.cli.*;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * Mateusz Kobierski
 *
 * Problem nr 14 - Stopien separacji w sieci spolecznosciowej
 */

public class SocianalyzerApp {

    private final static Logger logger = LoggerFactory.getLogger(SocianalyzerApp.class);
    private static Options options;

    public static void main(String[] args) {
        options = new Options();
        options.addOption(new Option("m", "mode", true, "running mode <input | gen | test>"));
        options.addOption(new Option("i", "input", true, "input file"));
        options.addOption(new Option("o", "output", true, "output file"));
        options.addOption(new Option("p", "people", true, "(max) number of people in network (must be at least 2)"));
        options.addOption(new Option("d", "difficulty", true, "difficulty of auto-generated case [0-100]"));
        options.addOption(new Option("h", "help", false, "displays this message"));

        CommandLineParser parser = new DefaultParser();
        CommandLine line = null;
        try {
            line = parser.parse(options, args);
        } catch (ParseException e) {
            logger.error("argument parsing failed: {}", e.getMessage());
            System.exit(1);

        }

        if (line.hasOption("h")) {
            displayHelpAndExit();
        }

        if (!line.hasOption("m")) {
            logger.error("no mode specified");
            displayHelpAndExit();
        }

        String mode = line.getOptionValue("m").toUpperCase();
        switch (mode) {
            case "INPUT": runInputMode(line); break;
            case "GEN": runGenMode(line); break;
            case "TEST":
                default: runTestMode(line); break;

        }
    }

    private static void runInputMode(CommandLine line) {
        String inFile;
        String outFile;
        if (!line.hasOption("i")) {
            logger.error("no input specified");
            displayHelpAndExit();
        }
        inFile = line.getOptionValue("i");


        if (!line.hasOption("o")) {
            logger.info("using default output file: \"out.txt\"");
            outFile = "out.txt";
        } else {
            outFile = line.getOptionValue("o");
            logger.info("using output file: {}", outFile);
        }
        InputReader reader = new InputReader(inFile);
        OutputWriter writer = new OutputWriter(outFile);

        Matrix input = reader.readInputData();
        FloydWarshall.calcShortestPaths(input);
        List<Matrix> integrals = FloydWarshall.extractIntegralMatrices(input);
        integrals.forEach(FloydWarshall::calcSeparationRate);
        integrals.forEach(KruskalMST::calcMST);

        writer.writeResults(integrals, true);
    }

    private static void runGenMode(CommandLine line) {
        int difficulty;
        int nodes;
        String inFile;
        String outFile;

        if (!line.hasOption("i")) {
            logger.error("no input specified");
            displayHelpAndExit();
        }
        inFile = line.getOptionValue("i");

        if (!line.hasOption("o")) {
            logger.info("using default output file: \"out.txt\"");
            outFile = "out.txt";
        } else {
            outFile = line.getOptionValue("o");
            logger.info("using output file: {}", outFile);
        }
        if (!line.hasOption("d")) {
            logger.info("using default difficulty: 60");
            difficulty = 60;
        } else {
            difficulty = Integer.valueOf(line.getOptionValue("d"));
            if (difficulty > 100 || difficulty < 0) {
                logger.error("difficulty not within bounds");
                displayHelpAndExit();
            }
            logger.info("using difficulty: {}", difficulty);
        }

        if (!line.hasOption("p")) {
            logger.info("using default number of people: 100");
            nodes = 100;
        } else {
            nodes = Integer.valueOf(line.getOptionValue("p"));
            if (nodes < 2) {
                logger.error("network must have at least 2 members!");
                displayHelpAndExit();
            }
            logger.info("using number of people: {}", nodes);
        }

        Matrix generated = Generator.generateTestData(difficulty, nodes, inFile);
        OutputWriter writer = new OutputWriter(outFile);

        FloydWarshall.calcShortestPaths(generated);
        List<Matrix> integrals = FloydWarshall.extractIntegralMatrices(generated);
        integrals.forEach(FloydWarshall::calcSeparationRate);
        integrals.forEach(KruskalMST::calcMST);

        writer.writeResults(integrals, true);
    }

    private static void runTestMode(CommandLine line) {
        int difficulty;
        int nodes;
        String inFile;
        String outFile;

        if (!line.hasOption("i")) {
            logger.error("no input specified");
            displayHelpAndExit();
        }
        inFile = line.getOptionValue("i");

        if (!line.hasOption("o")) {
            logger.info("using default output file: \"out.txt\"");
            outFile = "out.txt";
        } else {
            outFile = line.getOptionValue("o");
            logger.info("using output file: {}", outFile);
        }
        if (!line.hasOption("d")) {
            logger.info("using default difficulty: 60");
            difficulty = 60;
        } else {
            difficulty = Integer.valueOf(line.getOptionValue("d"));
            if (difficulty > 100 || difficulty < 0) {
                logger.error("difficulty not within bounds");
                displayHelpAndExit();
            }
            logger.info("using difficulty: {}", difficulty);
        }

        if (!line.hasOption("p")) {
            logger.info("using default number of people: 150");
            nodes = 150;
        } else {
            nodes = Integer.valueOf(line.getOptionValue("p"));
            if (nodes < 100) {
                logger.error("in testing mode, network must have at least 100 members!");
                displayHelpAndExit();
            }
            logger.info("using number of people: {}", nodes);
        }


        List<Pair<Integer, Long>> times = new ArrayList<>();
        warmUp();

        OutputWriter writer = new OutputWriter(outFile);
        for (int i = nodes - 50; i <= nodes; i++) {
            logger.info("preparing tests for {} people", i);
            Matrix generated = Generator.generateTestData(difficulty, i, inFile);

            long start = System.currentTimeMillis();
            FloydWarshall.calcShortestPaths(generated);
            List<Matrix> integrals = FloydWarshall.extractIntegralMatrices(generated);
            integrals.forEach(FloydWarshall::calcSeparationRate);
            integrals.forEach(KruskalMST::calcMST);
            long end = System.currentTimeMillis();

            times.add(new ImmutablePair<>(i, end - start));
            writer.writeResults(integrals, true);
        }

        writer.writeTimeMeasurements(times);

    }

    private static void displayHelpAndExit() {
        String header = "Socianalyzer\n\n";
        String footer = "\nMateusz Kobierski";

        HelpFormatter formatter = new HelpFormatter();
        formatter.printHelp("socianalyzer", header, options, footer, true);
        System.exit(0);
    }

    private static void warmUp() {
        Map<String, String> m = new HashMap<>();
        Random rnd = new Random();
        for (int i = 0; i < 10000; i++) {
            m.put(String.valueOf(i), String.valueOf(i+10));
        }
        for (int i = 0; i < 10000; i++) {
            for (int j = 0; j < 10000; j++) {
                new HashMap<>().put(String.valueOf(i), String.valueOf(i+10));
            }
        }
        System.gc();
    }

}
