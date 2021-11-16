package com.chatty;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
//import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;


import com.chatty.R;
import com.chatty.RecyclerViewAdapter;
import com.chatty.RegisterUser;
import com.chatty.apiConfiguration.ApiConfiguration;
import com.chatty.httpRequestProcessor.HttpRequestProcessor;
import com.chatty.httpRequestProcessor.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class FragmentRegisterUser extends Fragment {

    View v;
    //private ImageView iv, img_chat;
    private TextView name_reg, emailId_reg;
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private List<RegisterUser> listRegisterUser;
    private HttpRequestProcessor httpRequestProcessor;
    private String response;
    private ApiConfiguration apiConfiguration;
    private String baseURL, urlMemberListing, urlAddFriend;
    private String jsonPostString, jsonResponseString;
    private String name;
    private String emailId;
    private String message;
    private boolean success;
    private int userID;
    private Button btnAdd;
    RegisterUser registeredMemberModel;
    private ArrayList<RegisterUser> registerUsers;
    private Response response1;
    private String memberId, friendId, requestBy, createdBy, modifiedBy, userName, errorMessage;

    public FragmentRegisterUser() {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_reg_recyclerview, container, false);
        recyclerView = v.findViewById(R.id.reg_recyclerview);
        getActivity().setTitle("Registered User");
        //findviewbyid
        name_reg = v.findViewById(R.id.name_reg);
        emailId_reg = v.findViewById(R.id.email_reg);
        listRegisterUser = new ArrayList<>();

        RecyclerViewAdapter recyclerViewAdapter = new RecyclerViewAdapter(getContext(), listRegisterUser);
        //recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(recyclerViewAdapter);

        //Initialization
        httpRequestProcessor = new HttpRequestProcessor();
        //response = new Response();
        response = String.valueOf(new Response());
        apiConfiguration = new ApiConfiguration();


        //Getting BaseURL
        baseURL = apiConfiguration.getApi();
        urlMemberListing = baseURL + "ApplicationFriendAPI/GetApplicationMemberList";
        new RegisterUserTask().execute();
        return v;

    }
    public class RegisterUserTask extends AsyncTask<String, String, String> {
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
            response = httpRequestProcessor.gETRequestProcessor(urlMemberListing);
            //jsonResponseString = response.getJsonResponseString();
            return response;

        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            progressDialog.dismiss();
            Log.d("Response String", s);

            try {
                JSONObject jsonObject = new JSONObject(s);
                JSONArray jsonArray = jsonObject.getJSONArray("responseData");
                registerUsers = new ArrayList<>();

                if (jsonArray.length() != 0) {
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject object = (JSONObject) jsonArray.get(i);
                        String name = object.getString("Name");
                        String emailId = object.getString("EmailId");
                        String memberID = object.getString("MemberId");
                        //  String photo = object.getString("photo");
                        registeredMemberModel = new RegisterUser(name, emailId, memberID);

                        registerUsers.add(registeredMemberModel);
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            RecyclerViewAdapter recyclerViewAdapter = new RecyclerViewAdapter(getActivity(), registerUsers);
            recyclerView.setAdapter(recyclerViewAdapter);
        }
    }
}

