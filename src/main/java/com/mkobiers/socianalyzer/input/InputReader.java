package com.mkobiers.socianalyzer.input;

import com.mkobiers.socianalyzer.model.Matrix;
import com.mkobiers.socianalyzer.model.MatrixCell;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class InputReader {

    private Logger logger = LoggerFactory.getLogger(InputReader.class);
    private final String PATH = "in.txt";

    public Matrix readInputData(){
        Matrix matrix = new Matrix();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(PATH));
            String line;
            while ((line = reader.readLine()) != null) {
                logger.info(line);
                String[] data = line.split(" ");
                if (data.length == 3) {
                    int days = Integer.valueOf(data[2]);
                    matrix.add(data[0], data[1], new MatrixCell(days));
                }
            }
        } catch (IOException e) {
            logger.error("failed to open input file");
        }
        return matrix;
    }
}
