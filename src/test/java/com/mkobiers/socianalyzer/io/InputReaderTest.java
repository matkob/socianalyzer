package com.mkobiers.socianalyzer.io;

import org.junit.jupiter.api.Test;

/**
 * Mateusz Kobierski
 *
 * Problem nr 14 - Stopien separacji w sieci spolecznosciowej
 */

public class InputReaderTest {

    @Test
    public void doesReaderRead() {
        InputReader reader = new InputReader("in.txt");
        reader.readInputData();
    }
}
