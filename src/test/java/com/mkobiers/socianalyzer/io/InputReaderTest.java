package com.mkobiers.socianalyzer.io;

import org.junit.jupiter.api.Test;

public class InputReaderTest {

    @Test
    public void doesReaderRead() {
        InputReader reader = new InputReader("in.txt");
        reader.readInputData();
    }
}
