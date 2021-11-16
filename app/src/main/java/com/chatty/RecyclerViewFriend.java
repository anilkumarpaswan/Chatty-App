package com.chatty;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.chatty.Friend;
import com.chatty.R;

import java.util.List;



public class RecyclerViewFriend extends RecyclerView.Adapter<RecyclerViewFriend.MyViewHolder>
{
    Context context2;
    List<Friend> mData1;

    public RecyclerViewFriend(Context context2, List<Friend> friendList) {
        this.context2 = context2;
        this.mData1 = friendList;
    }

    @Override
    public  MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context2).inflate(R.layout.item_friends, parent, false);
        MyViewHolder vHolder = new MyViewHolder(v);
        return vHolder;
    }
    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        holder.tv_name.setText(mData1.get(position).getFriendName());
        // holder.bind(mData1.get(position),listener);
        //Akhil (Uncommented this)
       holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String friendName = mData1.get(position).getFriendName();
                String friendID = mData1.get(position).getFriendId();
                String memberID = mData1.get(position).getMemberId();
                System.out.println("friendName = " + friendName);
                System.out.println("friendID = " + friendID);
                System.out.println("memberID = " + memberID);
                Intent intent = new Intent(context2, Chatroom.class);
                intent.putExtra("Friend_Name", friendName);
                intent.putExtra("Friend_ID", friendID);
                intent.putExtra("UserID", memberID);
                context2.startActivity(intent);
            }
        });
       //Akhil (Till here)
    }


    @Override
    public int getItemCount() {
        return mData1.size();
    }
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView tv_name;


        public MyViewHolder(View itemView) {
            super(itemView);
            tv_name =  itemView.findViewById(R.id.name_friend);

        }
    }
}
