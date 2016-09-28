package com.ruth.knittingcounter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    int rows;
    int current;
    String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
        current = sharedPref.getInt("current", 1);
        rows = sharedPref.getInt(current + "_row", 1);
        name = sharedPref.getString(current + "_name", "new project");
        updateScreen();

        Button incrementButton = (Button) findViewById(R.id.plus);
        incrementButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                rows++;
                updateScreen();

                SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putInt(current + "_row", rows);
                editor.apply();
            }
        });
    }

    public void updateScreen() {
        TextView rowDisplay = (TextView) findViewById(R.id.rows);
        rowDisplay.setText("" + rows);
        TextView projectName = (TextView) findViewById(R.id.project);
        projectName.setText(name);
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
            editor.putInt(current+"_row", rows);
            editor.apply();
            updateScreen();
            return true;
        }

        if (id == R.id.add) {
            editDialog(true);
            return true;
        }

        if (id == R.id.edit) {
            editDialog(false);
            return true;
        }

        if (id == R.id.switchProject) {
            switchProjectDialog();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void editDialog(final boolean isNew) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Edit your counter");
        LayoutInflater inflater = this.getLayoutInflater();
        final View v = inflater.inflate(R.layout.edit_dialog, null);
        builder.setView(v);
        EditText newName = (EditText) v.findViewById(R.id.newName);
        newName.setText(name);

        builder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                if (isNew) {
                    current = current++;
                }
                EditText newName = (EditText) v.findViewById(R.id.newName);
                name = newName.getText().toString();
                updateScreen();
                SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putString(current + "_name", name);
                editor.apply();
            }
        });
        builder.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User cancelled the dialog
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void switchProjectDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Pick a counter");

        builder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {

            }
        });
        builder.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User cancelled the dialog
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
