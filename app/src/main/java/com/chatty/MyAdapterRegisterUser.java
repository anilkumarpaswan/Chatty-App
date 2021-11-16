package com.chatty;

import android.support.v7.widget.RecyclerView;
import android.widget.BaseAdapter;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.chatty.R;

import java.util.ArrayList;


public class MyAdapterRegisterUser {
   /* private Context context;
    private ArrayList<RegisterUser> registerUserArrayList;
    private LayoutInflater inflater;

    public MyAdapterRegisterUser(Context context, ArrayList<RegisterUser> registerUserArrayList) {
        this.context = context;
        this.registerUserArrayList = registerUserArrayList;
    }

    @Override
    public int getCount() {
        return registerUserArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return registerUserArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {


        inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(R.layout.item_registeruser, parent, false);

        //find view of item_registeruser

        //ImageView iv=convertView.findViewById(R.id.iv);
        TextView name_reg=convertView.findViewById(R.id.name_reg);
        TextView email_reg=convertView.findViewById(R.id.email_reg);

        //fetch data from Datasource
        RegisterUser r=registerUserArrayList.get(position);
        String name=r.getName();
        String emailId=r.getEmailID();

        //load the views with data
        name_reg.setText(name);
        email_reg.setText(emailId);

        return convertView;
    }*/

}