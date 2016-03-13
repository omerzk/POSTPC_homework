package com.example.cptsop.app;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Date;

public class MainActivity extends Activity {
    private ArrayList<TodoItem> logicalList;
    private ArrayAdapter<TodoItem> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        logicalList = new ArrayList<TodoItem>();
        adapter = new AlternatingAdapter(this,
                android.R.layout.simple_list_item_1,
                logicalList);

        ListView uiList = (ListView) findViewById(R.id.list);
        uiList.setAdapter(adapter);
        final Context context = this;
        uiList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                Log.d("--", "long");
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle(logicalList.get(position).task);
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
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 1) {
            if (resultCode == Activity.RESULT_OK) {
                String todoText = data.getStringExtra("todoText");
                Date date = (Date) data.getSerializableExtra("date");
                logicalList.add(new TodoItem(date, todoText));
                adapter.notifyDataSetChanged();
            } else if (resultCode == Activity.RESULT_CANCELED) {
                Log.d("Dialog", "canceled");
            }
        }
    }

    private void deleteBullet(int position) {
        Log.d("----", "delete: " + String.valueOf(position));
        logicalList.remove(position);
        adapter.notifyDataSetChanged();
    }

    public TodoItem getItem(int position) {
        return logicalList.get(position);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent = new Intent(this, addNewTodoActivity.class);
        startActivityForResult(intent, 1);
        return true;
    }


}
