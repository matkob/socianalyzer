package com.mkobiers.socianalyzer.io;

import com.mkobiers.socianalyzer.model.Matrix;
import com.mkobiers.socianalyzer.model.MatrixCell;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * Mateusz Kobierski
 *
 * Problem nr 14 - Stopien separacji w sieci spolecznosciowej
 */

public class InputReader {

    private final static Logger logger = LoggerFactory.getLogger(InputReader.class);
    private String inFile;

    public InputReader(String path) {
        inFile = path;
    }

    public Matrix readInputData(){
        Matrix matrix = new Matrix();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(inFile));
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(" ");
                if (data.length == 3) {
                    int days = Integer.valueOf(data[2]);
                    matrix.put(data[0], data[1], new MatrixCell(days));
                }
            }
        } catch (IOException e) {
            logger.error("failed to open input file");
        }
        return matrix;
    }
}
