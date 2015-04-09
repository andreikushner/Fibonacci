package com.qulix.fibonacci.calculator;

import android.os.AsyncTask;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

abstract class Combined implements Calculator {

    private Cancellable mCurrentProcessing;

    private Calculator mPrev;
    private Calculator mCurrent;

    private BigInteger mCached;

    private List<Receiver<BigInteger>> mReceivers = new ArrayList<Receiver<BigInteger>>();

    public Combined(final Calculator prev,
                    final Calculator current) {
        mPrev = prev;
        mCurrent = current;
    }

    public Cancellable get(final Receiver<BigInteger> onData) {
        if (mCached != null) {
            onData.receive(mCached);
            return null;
        }

        final boolean gettingNow = mReceivers.size() > 0;

        mReceivers.add(onData);

        if (!gettingNow) {
            run(mPrev, mOnPrevGot);
        }

        return new Cancellable() {
            @Override public void cancel() {
                mReceivers.remove(onData);

                stop();
            }
        };
    }

    private void run(final Calculator calc, final Receiver<BigInteger> receiver) {
        final Cancellable step = calc.get(receiver);

        if (step != null) {
            mCurrentProcessing = step;
        }
    }

    private void stop() {
        if (mReceivers.size() > 0) return;

        if (mCurrentProcessing != null) {
            mCurrentProcessing.cancel();
            mCurrentProcessing = null;
        }
    }

    private final Receiver<BigInteger> mOnPrevGot = new Receiver<BigInteger>() {
        @Override public void receive(final BigInteger prev) {
            run(mCurrent, onCurrentGot(prev));
        }
    };

    private final Receiver<BigInteger> onCurrentGot(final BigInteger prev) {
        return new Receiver<BigInteger>() {
            @Override public void receive(final BigInteger current) {
                mCurrentProcessing = calculateSelf(prev, current, mOnResult);
            }
        };
    }

    private final Receiver<BigInteger> mOnResult = new Receiver<BigInteger>() {
        @Override public void receive(final BigInteger data) {
            mCurrentProcessing = null;

            mPrev = null;
            mCurrent = null;
            mCached = data;

            for (final Receiver<BigInteger> r: mReceivers) {
                r.receive(mCached);
            }
        }
    };

    private Cancellable calculateSelf(final BigInteger prev,
                                      final BigInteger current,
                                      final Receiver<BigInteger> onResult) {

        final AsyncTask task = new AsyncTask<Void, Void, BigInteger>() {

            @Override protected BigInteger doInBackground(Void...args) {
                return asyncCalculate(prev, current);
            }

            @Override protected void onPostExecute(final BigInteger result) {
                onResult.receive(result);
            }

        }.execute();

        return new Cancellable() {
            @Override public void cancel() {
                task.cancel(true);
            }
        };
    }

    public abstract BigInteger asyncCalculate(final BigInteger prev, final BigInteger curr);
}


