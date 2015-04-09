package com.qulix.fibonacci.calculator;

import java.math.BigInteger;

public class FixedValue implements Calculator {
    private final BigInteger mFixed;

    public FixedValue(final BigInteger fixed) {
        mFixed = fixed;
    }

    @Override public Cancellable get(final Receiver<BigInteger> onData) {
        onData.receive(mFixed);
        return null;
    }
}
