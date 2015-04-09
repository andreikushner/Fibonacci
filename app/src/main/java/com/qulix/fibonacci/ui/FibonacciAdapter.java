package com.qulix.fibonacci.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.qulix.fibonacci.fibonacci.R;
import com.qulix.fibonacci.ui.holder.FibonacciRowViewHolder;

import java.math.BigInteger;

public class FibonacciAdapter extends ArrayAdapter<BigInteger> {

    private final LayoutInflater mInflater;


    public FibonacciAdapter(final Context context) {
        super(context, 0, 0);
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getView(
            final int position,
            final View convertView,
            final ViewGroup parent) {
        final View vi;
        final FibonacciRowViewHolder holder;
        if (convertView == null) {
            vi = mInflater.inflate(R.layout.fibonacci_row, null);
            holder = new FibonacciRowViewHolder(vi);
            vi.setTag(holder);
        } else {
            vi = convertView;
            holder = (FibonacciRowViewHolder) vi.getTag();
        }

        holder.bindItem(position);

        return vi;
    }

    @Override
    public int getCount() {
        return Integer.MAX_VALUE;
    }
}
