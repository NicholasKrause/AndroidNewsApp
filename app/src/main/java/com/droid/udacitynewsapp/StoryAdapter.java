package com.droid.udacitynewsapp;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.view.LayoutInflater;
import android.view.View;


import java.util.ArrayList;

/**
 * Created by nickkrause on 5/10/17.
 */
public class StoryAdapter extends ArrayAdapter<Story> {

    public StoryAdapter(Context context) {
        super(context, -1, new ArrayList<Story>());
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView
                    = LayoutInflater.from(getContext()).inflate(R.layout.new_list, parent, false);
        }
        TextView title = (TextView) convertView.findViewById(R.id.title);
        TextView author = (TextView) convertView.findViewById(R.id.auth);
        TextView date = (TextView) convertView.findViewById(R.id.datestamp);
        TextView section = (TextView) convertView.findViewById(R.id.area);

        Story currentStory = getItem(position);
        title.setText(currentStory.getTitle());
        author.setText(currentStory.getAuthor());
        date.setText(currentStory.getDate());
        section.setText(currentStory.getSection());

        return convertView;
    }
}
