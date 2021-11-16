package com.chatty;

import android.app.ProgressDialog;
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
import android.widget.Button;
import android.widget.TextView;
import com.chatty.AddFriend;
import com.chatty.RecyclerViewAddFriend;
import com.chatty.apiConfiguration.ApiConfiguration;
import com.chatty.httpRequestProcessor.HttpRequestProcessor;
import com.chatty.httpRequestProcessor.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class FragmentAddFriend extends Fragment {
    View v;
    private TextView name_friend, emailId_friend;
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private ArrayList<AddFriend> addFriendArrayList;
    private HttpRequestProcessor httpRequestProcessor;
    private String response;
    private ApiConfiguration apiConfiguration;
    private String baseURL, urlAddFriend;
    private String jsonPostString, jsonResponseString;
    private String name;
    private String emailId;
    private String message;
    private boolean success;
    private int userID;
    private Button btnAccept, btnReject;
    private String MemberId, friendId, memberName, friendName, userName, errorMessage;
    private AddFriend addfrnd;
    private SharedPreferences sp;
    private String TAG="surbhi";
    int MODE_PRIVATE = 0;

    public FragmentAddFriend() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_addfriend_recyclerview, container, false);

        recyclerView = v.findViewById(R.id.addfriend_recyclerview);
        getActivity().setTitle("AddFriends");
        //findviewbyid
        name_friend = v.findViewById(R.id.name_friend);
        emailId_friend = v.findViewById(R.id.email_friend);
        addFriendArrayList = new ArrayList<>();

        RecyclerViewAddFriend recyclerViewAddFriend = new RecyclerViewAddFriend(getContext(), addFriendArrayList);
        //recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        recyclerView.setAdapter(recyclerViewAddFriend);

        //Initialization
        httpRequestProcessor = new HttpRequestProcessor();
        //response = new Response();
        response = String.valueOf(new Response());
        apiConfiguration = new ApiConfiguration();
        sp = getActivity().getSharedPreferences("My_Pref", MODE_PRIVATE);
        String memberID = sp.getString("ApplicationUserId_key", "");
        Log.d("MemberID for my FriedRequest", ""+memberID);

        //Getting BaseURL
        baseURL = apiConfiguration.getApi();
        urlAddFriend = baseURL + "ApplicationFriendAPI/MyFriendRequest/" + memberID;
        new AddFriendTask().execute(friendName,emailId);
        return v;
    }

    public class AddFriendTask extends AsyncTask<String, String, String> {
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
            System.out.println("urlAddFriend = "+urlAddFriend);
            jsonResponseString = httpRequestProcessor.gETRequestProcessor(urlAddFriend);
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
                addFriendArrayList = new ArrayList<>();

                if (jsonArray.length() != 0) {
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject object = (JSONObject) jsonArray.get(i);
                        String friendName = object.getString("FriendName");
                        String EmailId = object.getString("EmailId");
                        String mId=object.getString("MemberId");
                        Log.d(TAG, "  "+mId);
                        String applicationId=object.getString("ApplicationFriendAssociationId");
                        Log.d(TAG, " "+applicationId);
                      //  String MemberId = object.getString("ApplicationUserId");
                        //int friendId=object.getInt("FriendId")
                        addfrnd = new AddFriend(friendName,EmailId,mId,applicationId);
                        addFriendArrayList.add(addfrnd);
                        Log.d(TAG, ""+addFriendArrayList);
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            RecyclerViewAddFriend recyclerViewAddFriend = new RecyclerViewAddFriend(getActivity(), addFriendArrayList);
            recyclerView.setAdapter(recyclerViewAddFriend);

        }
    }
}
