package com.mkobiers.socianalyzer.input;

import org.junit.jupiter.api.Test;

public class InputReaderTest {

    @Test
    public void doesReaderRead() {
        InputReader reader = new InputReader();
        reader.readInputData();
    }
}
