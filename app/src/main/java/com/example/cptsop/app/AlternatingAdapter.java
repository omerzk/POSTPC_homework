package com.example.cptsop.app;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;


class AlternatingAdapter extends ArrayAdapter<String> {
    private final int[] colors = {Color.RED, Color.BLUE};

    public AlternatingAdapter(Context context, int resource, ArrayList<String> items) {
        super(context, resource, items);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.item, parent, false);
            holder = new ViewHolder(convertView.findViewById(R.id.itemText));

            convertView.setTag(holder);

        } else holder = (ViewHolder) convertView.getTag();
        holder.text.setText(((MainActivity) getContext()).getItem(position));
        holder.text.setTextColor(colors[position % 2]);

        return convertView;
    }

    private static class ViewHolder {
        final TextView text;

        public ViewHolder(View textView) {
            text = (TextView) textView;
        }
    }


}
