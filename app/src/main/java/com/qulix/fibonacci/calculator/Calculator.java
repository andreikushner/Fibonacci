package com.qulix.fibonacci.calculator;

import java.math.BigInteger;

public interface Calculator {
    public Cancellable get(Receiver<BigInteger> onData);
}

