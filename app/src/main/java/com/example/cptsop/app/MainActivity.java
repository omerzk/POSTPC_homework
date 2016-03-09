package com.example.cptsop.app;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends Activity {
    private ArrayList<String> logicalList;
    private EditText input = null;
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        logicalList = new ArrayList<String>();
        adapter = new AlternatingAdapter(this,
                android.R.layout.simple_list_item_1,
                logicalList);

        ListView uiList = (ListView) findViewById(R.id.list);
        input = (EditText) findViewById(R.id.input);
        uiList.setAdapter(adapter);
        final Context context = this;
        uiList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                Log.d("--", "long");
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Options");
                builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Log.d("delete", String.valueOf(position));
                        deleteBullet(position);
                    }
                });
                builder.show();
                return true;
            }
        });
//        for (int i = 0; i < 200; i++) {
//            addBullet(i);
//        }
    }

    private void addBullet() {
        logicalList.add(input.getText().toString());
        input.setText("");
        adapter.notifyDataSetChanged();
    }


    private void deleteBullet(int position) {
        Log.d("----", "delete: " + String.valueOf(position));
        logicalList.remove(position);
        adapter.notifyDataSetChanged();
    }

    public String getItem(int position) {
        return logicalList.get(position);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        Log.d("----------------------", "options");
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        addBullet();
        return true;
    }


}
