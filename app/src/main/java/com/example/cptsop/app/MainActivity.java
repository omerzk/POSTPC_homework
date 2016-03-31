package com.example.cptsop.app;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends Activity {
    private ArrayList<TodoItem> logicalList;
    private ArrayAdapter<TodoItem> adapter;
    private DBOpenhelper DBHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DBHelper = new DBOpenhelper(this.getApplicationContext());
        logicalList = DBHelper.GetAll();

        adapter = new AlternatingAdapter(this,
                android.R.layout.simple_list_item_1,
                logicalList);
        final Pattern callRegex = Pattern.compile(".*call ([\\d-]+).*", Pattern.CASE_INSENSITIVE);
        ListView uiList = (ListView) findViewById(R.id.list);
        uiList.setAdapter(adapter);

        final Context context = this;
        uiList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                Log.d("--", "long");
                final Dialog dialog = new Dialog(context);
                dialog.setContentView(R.layout.dialog);
                dialog.setTitle(logicalList.get(position).task);
                Matcher m = callRegex.matcher(logicalList.get(position).task);
                if (m.find()) {
                    final String number = m.group(1);
                    Button callButton = (Button) dialog.findViewById(R.id.CallButton);
                    callButton.setText("Call " + number);
                    callButton.setVisibility(View.VISIBLE);
                    callButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(Intent.ACTION_DIAL);
                            intent.setData(Uri.parse("tel:" + number));
                            startActivity(intent);
                        }
                    });
                }

                Button deleteButton = (Button) dialog.findViewById(R.id.DeleteButton);
                deleteButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        deleteBullet(position);
                        dialog.dismiss();
                    }
                });

                dialog.show();

                return true;
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1) {
            if (resultCode == Activity.RESULT_OK) {
                String todoText = data.getStringExtra("title");
                Date date = (Date) data.getSerializableExtra("dueDate");
                insert(new TodoItem(date, todoText));
                adapter.notifyDataSetChanged();
            } else if (resultCode == Activity.RESULT_CANCELED) {
                Log.d("Dialog", "canceled");
            }
        }
    }

    private void insert(TodoItem item) {
        logicalList.add(item);
        DBHelper.insert(item);
    }
    public void deleteBullet(int position) {
        Log.d("----", "delete: " + String.valueOf(position));
        DBHelper.delete(logicalList.get(position));
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
        Intent intent = new Intent(this, AddNewTodoItemActivity.class);
        startActivityForResult(intent, 1);
        return true;
    }


}
