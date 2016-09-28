package com.ruth.knittingcounter;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    int rows;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
        rows = sharedPref.getInt("row", 1);
        updateNumber();

        Button incrementButton = (Button) findViewById(R.id.plus);
        incrementButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                rows = rows+1;
                updateNumber();

                SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putInt("row", rows);
                editor.apply();
            }
        });
    }

    public void updateNumber() {
        TextView rowDisplay = (TextView) findViewById(R.id.rows);
        rowDisplay.setText(""+rows);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.reset) {
            rows = 1;
            SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putInt("row", rows);
            editor.apply();
            updateNumber();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
