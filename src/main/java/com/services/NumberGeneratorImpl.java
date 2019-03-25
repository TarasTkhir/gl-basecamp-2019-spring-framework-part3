package com.services;

import java.util.Random;


public class NumberGeneratorImpl implements  NumberGenerator{

    private static Random random = new Random();

    private int bound =6;

    @Override
    public void setBound(int bound) {
        this.bound = bound;
    }

    @Override
    public int numberGenerator(){

        return random.nextInt(bound);
    }
}