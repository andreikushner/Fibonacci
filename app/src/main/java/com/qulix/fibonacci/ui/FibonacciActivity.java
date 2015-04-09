package com.qulix.fibonacci.ui;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.ListView;

import com.qulix.fibonacci.fibonacci.R;

public class FibonacciActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fibonacci);

        ListView fibListView = (ListView) findViewById(R.id.fib_list_view);
        final FibonacciAdapter adapter = new FibonacciAdapter(this);
        fibListView.setAdapter(adapter);
    }

}
