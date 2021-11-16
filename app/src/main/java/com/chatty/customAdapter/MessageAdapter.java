package com.chatty.customAdapter;


import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.chatty.Pojos.ChatMessage;
import com.chatty.Pojos.MyRecievedMessages;
import com.chatty.R;

import java.util.List;

public class MessageAdapter extends ArrayAdapter<MyRecievedMessages> {

    private Activity activity;
    private List<MyRecievedMessages> messages;
    private String user_ID;

    public MessageAdapter(Activity context, int resource, List<MyRecievedMessages> objects,String user_ID) {
        super(context, resource, objects);
        this.activity = context;
        this.messages = objects;
        this.user_ID=user_ID;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);

        int layoutResource = 0; // determined by view type
        MyRecievedMessages chatMessage = getItem(position);
        int viewType = getItemViewType(position);

        if (chatMessage.getSenderId().equalsIgnoreCase(user_ID)) {
            layoutResource = R.layout.item_chat_left;

        } else {
            layoutResource = R.layout.item_chat_right;
        }

        if (convertView != null) {
            holder = (ViewHolder) convertView.getTag();
        } else {
            convertView = inflater.inflate(layoutResource, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }

        //set message content
        holder.msg.setText(chatMessage.getMessageBody());

        return convertView;
    }

    @Override
    public int getViewTypeCount() {
        // return the total number of view types. this value should never change
        // at runtime
        return 2;
    }

    @Override
    public int getItemViewType(int position) {
        // return a value between 0 and (getViewTypeCount - 1)
        return position % 2;
    }

    private class ViewHolder {
        TextView msg;


        ViewHolder(View v) {
            msg = v.findViewById(R.id.txt_msg);

        }
    }
}