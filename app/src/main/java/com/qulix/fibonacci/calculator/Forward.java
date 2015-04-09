package com.qulix.fibonacci.calculator;

import java.math.BigInteger;

public class Forward extends Combined {

    public Forward(final Calculator current, final Calculator prev) {
        super(current, prev);
    }

    @Override public BigInteger asyncCalculate(final BigInteger prev,
                                               final BigInteger current) {
        return prev.add(current);
    }
}
