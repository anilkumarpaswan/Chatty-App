package com.chatty;

/**
 * Created by Shreya on 23-Apr-18.
 */
import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;


public class MyAdapter {
    /*private Context context;
    private ArrayList<SingleRow> singleRowArrayList;
    private LayoutInflater inflater;
    private SingleRow s;

    public MyAdapter(Context context, ArrayList<SingleRow> singleRowArrayList) {
        this.context = context;
        this.singleRowArrayList = singleRowArrayList;

    }

    @Override
    public int getCount() {
        return singleRowArrayList.size();

    }

    @Override
    public Object getItem(int i) {
        return singleRowArrayList.get(i);
    }

    @Override
    public long getItemId(int i) {

        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(R.layout.singlerow, viewGroup, false);

        TextView txtname = (TextView) view.findViewById(R.id.txtusername);
        TextView txtphone= (TextView)view.findViewById(R.id.txtEmail);
        ImageView iv=(ImageView) view.findViewById(R.id.iv);


        SingleRow s = singleRowArrayList.get(i);
        int image= s.getImage();
        String username=s.getUsername();
        String email=s.getEmail();


        txtname.setText(s.getUsername());
        txtphone.setText(s.getEmail());

        return view;
    }*/

}
