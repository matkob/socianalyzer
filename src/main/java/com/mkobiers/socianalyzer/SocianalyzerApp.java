package com.mkobiers.socianalyzer;

import com.mkobiers.socianalyzer.algo.FloydWarshall;
import com.mkobiers.socianalyzer.io.InputReader;
import com.mkobiers.socianalyzer.io.OutputWriter;
import com.mkobiers.socianalyzer.logic.Generator;
import com.mkobiers.socianalyzer.model.Matrix;
import org.apache.commons.cli.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class SocianalyzerApp {

    private final static Logger logger = LoggerFactory.getLogger(SocianalyzerApp.class);
    private static Options options;

    public static void main(String[] args) {
        options = new Options();
        options.addOption(new Option("m", "mode", true, "running mode <input | auto | full>"));
        options.addOption(new Option("i", "input", true, "input file"));
        options.addOption(new Option("o", "output", true, "output file"));
        options.addOption(new Option("n", "nodes", true, "number of nodes in network"));
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
            case "AUTO": runAutoMode(line); break;
            case "FULL":
                default: runFullMode(line); break;

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
        }
        InputReader reader = new InputReader(inFile);
        OutputWriter writer = new OutputWriter(outFile);

        Matrix input = reader.readInputData();
        FloydWarshall.calcShortestPaths(input);
        List<Matrix> integrals = FloydWarshall.extractIntegralMatrices(input);
        integrals.forEach(FloydWarshall::calcSeparationRate);

        writer.writeResults(integrals, true);
    }

    private static void runAutoMode(CommandLine line) {
        int difficulty;
        int nodes;
        String inFile;

        if (!line.hasOption("i")) {
            logger.error("no input specified");
            displayHelpAndExit();
        }
        inFile = line.getOptionValue("i");

        if (!line.hasOption("d")) {
            logger.info("using default difficulty: 60");
            difficulty = 60;
        } else {
            difficulty = Integer.valueOf(line.getOptionValue("d"));
        }

        if (!line.hasOption("n")) {
            logger.info("using default number of nodes: 100");
            nodes = 100;
        } else {
            nodes = Integer.valueOf(line.getOptionValue("n"));
        }

        Generator.generateTestData(difficulty, nodes, inFile);
    }

    private static void runFullMode(CommandLine line) {

    }

    private static void displayHelpAndExit() {
        String header = "Socianalyzer\n\n";
        String footer = "\nMateusz Kobierski";

        HelpFormatter formatter = new HelpFormatter();
        formatter.printHelp("socianalyzer", header, options, footer, true);
        System.exit(0);
    }

}
