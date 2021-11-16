package com.chatty;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.chatty.Friend;
import com.chatty.R;

import java.util.ArrayList;

/**
 * Created by dell on 4/30/2018.
 */

public class MyAdapterFriend {
   /* private Context context1;
    ArrayList<Friend> friends;
    private LayoutInflater inflater;
    private TextView name_friend;


    public MyAdapterFriend(Context context1, ArrayList<Friend> friends) {
        this.context1 = context1;
        this.friends = friends;
    }

    @Override
    public int getCount() {
        return friends.size();
    }

    @Override
    public Object getItem(int position) {
        return friends.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        inflater = (LayoutInflater) context1.getSystemService(context1.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(R.layout.item_friends, parent, false);

        //find view of item_addfriend
        TextView name_friend=convertView.findViewById(R.id.name_friend);
        //TextView email_friend=convertView.findViewById(R.id.email_friend);

        //fetch data from Datasource
        Friend frnd=friends.get(position);
        String name=frnd.getFriendName();

        //load the views with data
        name_friend.setText(name);
        return convertView;
    }*/
}
