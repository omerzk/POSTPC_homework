package com.example.cptsop.app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by omer on 10/03/2016.
 */
public class AddNewTodoItemActivity extends Activity {
    TextView text;
    DatePicker datePicker;

    private static Date getDateFromPicker(DatePicker pick) {
        int day = pick.getDayOfMonth();
        int month = pick.getMonth();
        int year = pick.getYear();

        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day);

        return calendar.getTime();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addtodo);
        text = (TextView) findViewById(R.id.todoText);
        datePicker = (DatePicker) findViewById(R.id.datePicker);
    }

    public void onConfirm(View v) {
        Intent returnIntent = new Intent();
        returnIntent.putExtra("title", text.getText().toString());
        returnIntent.putExtra("dueDate", getDateFromPicker(datePicker));
        setResult(RESULT_OK, returnIntent);
        finish();
    }

    public void cancel(View v) {
        setResult(RESULT_CANCELED);
        finish();
    }
}
