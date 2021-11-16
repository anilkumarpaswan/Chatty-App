package com.chatty;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.chatty.Friend;
import com.chatty.R;
import com.chatty.RecyclerViewFriend;
import com.chatty.apiConfiguration.ApiConfiguration;
import com.chatty.httpRequestProcessor.HttpRequestProcessor;
import com.chatty.httpRequestProcessor.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;



public class FragmentFriends extends Fragment {
    View v;
    int MODE_PRIVATE = 0;
    private ImageView  img_friend;
    private TextView name_friend;
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private List<Friend> listFriend;

    private HttpRequestProcessor httpRequestProcessor;
    private String response;
    private ApiConfiguration apiConfiguration;
    private String baseURL, urlFriends;
    private String jsonPostString, jsonResponseString;
    private String name,memberId;
    private String emailId;
    private String message;
    private boolean success;
    private int userID;
    Friend friend1;
    private ArrayList<Friend> friendArrayList;
    private SharedPreferences sp;
    private String TAG="surbhi";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_friend_recyclerview, container, false);
        recyclerView = v.findViewById(R.id.friend_recyclerview);
        getActivity().setTitle("Friends");

        //findviewbyid


        name_friend = v.findViewById(R.id.name_friend);
        listFriend = new ArrayList<>();
        friendArrayList=new ArrayList<>();

        RecyclerViewFriend recyclerViewFriend = new RecyclerViewFriend(getContext(),listFriend);
        //recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(recyclerViewFriend);

        //Initialization
        httpRequestProcessor = new HttpRequestProcessor();
        response = String.valueOf(new Response());
        apiConfiguration = new ApiConfiguration();
        sp = getActivity().getSharedPreferences("My_Pref", MODE_PRIVATE);
        memberId = sp.getString("ApplicationUserId_key", "");
        Log.d(TAG, ""+memberId);
        //String friendName = sp.getString("FriendName","");
        //Log.d(TAG, " "+friendName);

        //Getting BaseURL
        baseURL = apiConfiguration.getApi();
        urlFriends= baseURL + "ApplicationFriendAPI/MyFriendList/"+memberId;
        new FriendTask().execute();
        return v;


    }

    public class FriendTask extends AsyncTask<String, String, String> {
        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(getActivity());
            progressDialog.setMessage("Please Wait...");
            progressDialog.setCancelable(false);
            progressDialog.show();

        }

        @Override
        protected String doInBackground(String... params) {
            System.out.println("urlFriends = " + urlFriends);
            jsonResponseString= httpRequestProcessor.gETRequestProcessor(urlFriends);
            return jsonResponseString;

        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            progressDialog.dismiss();
            Log.d("Response String", s);

            try {
                JSONObject jsonObject = new JSONObject(s);
                JSONArray jsonArray = jsonObject.getJSONArray("responseData");
                listFriend = new ArrayList<>();

                if (jsonArray.length() != 0) {
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject object = (JSONObject) jsonArray.get(i);
                        String friendName = object.getString("FriendName");
                        String memberID = object.getString("MemberId");
                        String fID=object.getString("FriendId");
                        Log.d(TAG, " "+memberID+fID+friendName);
                        friend1 = new Friend(memberID,fID,friendName);
                        friendArrayList.add(friend1);
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            RecyclerViewFriend recyclerViewFriend = new RecyclerViewFriend(getActivity(), friendArrayList);
            recyclerView.setAdapter(recyclerViewFriend);
        }
    }
}