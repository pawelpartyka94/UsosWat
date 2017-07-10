package com.example.pawel.usoswat;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * Created by pawel on 20.01.17.
 */

public class testadapter extends ArrayAdapter<String>
{
    private Activity context;
    ArrayList <String> subject = new ArrayList<>();
    ArrayList <String> numberOfSubject = new ArrayList<>();
    ArrayList <String> numberOfSemestr = new ArrayList<>();
    ArrayList <String> degree = new ArrayList<>();

    public testadapter(Activity context, ArrayList<String> subject, ArrayList<String> numberOfSubject, ArrayList<String> numberOfSemestr, ArrayList<String> degree)
    {
        super(context, R.layout.listlook, subject);
        this.context = context;
        this.subject = subject;
        this.numberOfSubject = numberOfSubject;
        this.numberOfSemestr = numberOfSemestr;
        this.degree = degree;
    }

    static class ViewHolder
    {
        public TextView textV1;
        public TextView textV2;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        ViewHolder viewHolder;
        View rowView = convertView;
        if(rowView == null) {
            LayoutInflater layoutInflater = context.getLayoutInflater();
            rowView = layoutInflater.inflate(R.layout.listlook, null, true);
            viewHolder = new ViewHolder();
            viewHolder.textV1 = (TextView) rowView.findViewById(R.id.textV1);
            viewHolder.textV2 = (TextView) rowView.findViewById(R.id.textV2);
            rowView.setTag(viewHolder);
        } else
        {
            viewHolder = (ViewHolder) rowView.getTag();
        }


        viewHolder.textV1.setText(subject.get(position) + "\n" + numberOfSubject.get(position) + "\n"  +  numberOfSemestr.get(position));
        viewHolder.textV2.setText(degree.get(position));

        return rowView;
    }
}
