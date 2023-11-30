package com.user.lamdafunction;

import java.util.function.Supplier;

public class RandomNumber implements Supplier<Integer> {

    int min = 10;
    int max = 20;

    @Override
    public Integer get() {
       int randomNumber = (int)(Math.random()*(max-min+1)+min);
        return randomNumber;
    }
}
