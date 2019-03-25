package com.services;

import org.junit.Test;

import static org.junit.Assert.*;

public class NumberGeneratorImplTest {

    @Test
    public void whenMethodGeneratorCalledThenReturnRandomNumberBetweenZeroAndTwo_PositiveTest() {

        NumberGenerator numberGenerator = new NumberGeneratorImpl();
        numberGenerator.setBound(3);
        int i = numberGenerator.numberGenerator();
        assertEquals(true, (i <= 2) && (i >= 0));
    }

    @Test
    public void whenMethodGeneratorCalledThenReturnRandomNumberBetweenZeroAndTwo_NegativeTest() {

        NumberGenerator numberGenerator = new NumberGeneratorImpl();
        numberGenerator.setBound(3);
        int i = numberGenerator.numberGenerator();
        assertNotEquals(true, (i > 2) || (i < 0));
    }
}