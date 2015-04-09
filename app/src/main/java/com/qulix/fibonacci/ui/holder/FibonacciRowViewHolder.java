package com.qulix.fibonacci.ui.holder;

import android.content.Context;
import android.os.AsyncTask;
import android.view.View;
import android.widget.TextView;

import com.qulix.fibonacci.calculator.Cancellable;
import com.qulix.fibonacci.calculator.FibonacciSlidingCalculator;
import com.qulix.fibonacci.calculator.Receiver;
import com.qulix.fibonacci.fibonacci.R;

import java.math.BigInteger;


public class FibonacciRowViewHolder {

    private final TextView mIndexField;
    private final TextView mValueField;

    private static FibonacciSlidingCalculator mCalc = new FibonacciSlidingCalculator();

    public FibonacciRowViewHolder(final View rowView) {
        mIndexField = textViewById(rowView, R.id.fibonacci_index);
        mValueField = textViewById(rowView, R.id.fibonacci_value);
    }

    public void bindItem(final long fibIndex) {
        cancel();
        Context context = mIndexField.getContext();
        mIndexField.setText(context.getString(R.string.fibonacci_index_title, fibIndex));

        final Cancellable calculating = mCalc.forIndex(fibIndex).get(new Receiver<BigInteger>() {
            @Override public void receive(final BigInteger value) {
                mCalculating = convertToString(value,
                        new Receiver<String>() {
                            @Override public void receive(final String data) {
                                mValueField.setText(data);
                            }
                        });
            }
        });

        if (calculating != null) {
            mCalculating = calculating;
        }

        mValueField.setText("Calculating ...");
    }

    public void cancel() {
        if (mCalculating != null) {
            mCalculating.cancel();
            mCalculating = null;
        }
    }

    private TextView textViewById(View view, int resId) {
        return (TextView) view.findViewById(resId);
    }

    private Cancellable mCalculating;

    private Cancellable convertToString(final BigInteger integer,
                                        final Receiver<String> onResult) {

        final AsyncTask task = new AsyncTask<Void, Void, String>() {

            @Override protected String doInBackground(Void...args) {
                // take only first 1000, as no more text will fit in item anyway
                final String asString = integer.toString();
                return asString.substring(0,
                        Math.min(1000,
                                asString.length()));
            }

            @Override protected void onPostExecute(final String result) {
                onResult.receive(result);
            }

        }.execute();

        return new Cancellable() {
            @Override public void cancel() {
                task.cancel(true);
            }
        };
    }

}