package com.qulix.fibonacci.calculator;

import java.math.BigInteger;

public class Backward extends Combined {

    public Backward(final Calculator current, final Calculator prev) {
        super(current, prev);
    }

    @Override public BigInteger asyncCalculate(final BigInteger prev,
                                               final BigInteger current) {
        return current.subtract(prev);
    }
}
