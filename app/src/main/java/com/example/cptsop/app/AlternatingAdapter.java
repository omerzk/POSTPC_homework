package com.example.cptsop.app;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;


class AlternatingAdapter extends ArrayAdapter<TodoItem> {
    private final int[] colors = {Color.RED, Color.CYAN};

    public AlternatingAdapter(Context context, int resource, ArrayList<TodoItem> items) {
        super(context, resource, items);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.item, parent, false);
            holder = new ViewHolder(convertView.findViewById(R.id.itemText), convertView.findViewById(R.id.itemDate));
            convertView.setTag(holder);
        } else holder = (ViewHolder) convertView.getTag();

        TodoItem todo = ((MainActivity) getContext()).getItem(position);
        holder.text.setText(todo.task);
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
        holder.date.setText(sdf.format(todo.due));

        holder.text.setTextColor(colors[todo.isOverdue() ? 0 : 1]);
        holder.date.setTextColor(colors[todo.isOverdue() ? 0 : 1]);

        return convertView;
    }

    private static class ViewHolder {
        final TextView text;
        final TextView date;

        public ViewHolder(View textView, View itemDate) {
            text = (TextView) textView;
            date = (TextView) itemDate;
        }
    }


}
