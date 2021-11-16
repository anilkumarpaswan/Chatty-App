package com.chatty;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.chatty.AddFriend;
import com.chatty.R;

import java.util.ArrayList;

public class MyAdapterAddFriend {
    /*private Context context;
    private ArrayList<AddFriend> addFriends;
    private LayoutInflater inflater;
    private TextView name_friend,email_friend;
    private Button btnAccept,btnReject;

    public MyAdapterAddFriend(Context context, ArrayList<AddFriend> addFriends) {
        this.context = context;
        this.addFriends = addFriends;
    }

    @Override
    public int getCount() {
        return addFriends.size();
    }

    @Override
    public Object getItem(int position) {
        return addFriends.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(R.layout.item_addfriend, parent, false);

        //find view of item_addfriend

        TextView name_friend=convertView.findViewById(R.id.name_friend);
        TextView email_friend=convertView.findViewById(R.id.email_friend);

        //fetch data from Datasource
        AddFriend ad=addFriends.get(position);
        String name=ad.getFriendName();
        String emailId=ad.getEmailID();

        //load the views with data
        name_friend.setText(name);
        email_friend.setText(emailId);

        return convertView;
    }*/
}
