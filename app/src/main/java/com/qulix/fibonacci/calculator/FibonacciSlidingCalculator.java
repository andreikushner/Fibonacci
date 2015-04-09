package com.qulix.fibonacci.calculator;

import java.math.BigInteger;


public class FibonacciSlidingCalculator {
    private long currentIndex = 0;

    private Calculator mCurrent  = new FixedValue(BigInteger.ZERO);
    private Calculator mNext     = new FixedValue(BigInteger.ONE);

    public Calculator forIndex(final long requiredIndex) {

        while (requiredIndex != currentIndex) {
            if (requiredIndex > currentIndex) {
                moveForward();
            } else {
                moveBackward();
            }
        }

        return mCurrent;
    }

    public void moveForward() {
        final Calculator current = mCurrent;

        mCurrent = mNext;
        mNext = new Forward(current, mNext);

        ++currentIndex;
    }

    public void moveBackward() {

        final Calculator next = mNext;

        mNext = mCurrent;

        mCurrent = new Backward(mCurrent, next);

        --currentIndex;
    }

}