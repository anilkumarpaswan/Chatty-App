package com.chatty;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.chatty.AddFriend;
import com.chatty.R;
import com.chatty.apiConfiguration.ApiConfiguration;
import com.chatty.httpRequestProcessor.HttpRequestProcessor;
import com.chatty.httpRequestProcessor.Response;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static android.content.ContentValues.TAG;

public class RecyclerViewAddFriend extends RecyclerView.Adapter<RecyclerViewAddFriend.MyViewHolder> {
    Context context1;
    ArrayList<AddFriend> mData;
    private SharedPreferences sp;
    private Button btnAccept, btnReject;
    private HttpRequestProcessor httpRequestProcessor;
    private String response;
    private ApiConfiguration apiConfiguration;
    private String baseURL, urlAcceptReject;
    private String jsonPostString, jsonResponseString;
    private Response response1;
    private ArrayList<AddFriend> addFriends;
    AddFriend addFriend;
    String applicationId;



    public RecyclerViewAddFriend(Context context1, ArrayList<AddFriend> mData) {
        this.context1 = context1;
        this.mData = mData;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context1).inflate(R.layout.item_addfriend, parent, false);
        MyViewHolder vHolder = new MyViewHolder(v);

        //Initialization
        httpRequestProcessor = new HttpRequestProcessor();
        response1 = new Response();
        apiConfiguration = new ApiConfiguration();
        //Getting base url
        baseURL = apiConfiguration.getApi();
        urlAcceptReject = baseURL + "ApplicationFriendAPI/ActionOnFriendRequest";
        return vHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        holder.tv_name.setText(mData.get(position).getFriendName());
        holder.tv_email.setText(mData.get(position).getEmailID());
        holder.btnAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context1, "Clicked", Toast.LENGTH_SHORT).show();
                addFriend = mData.get(position);
                String name1 = addFriend.getFriendName();
                String friendName=addFriend.getFriendName().toString();
                Log.d(TAG, ""+friendName);
                applicationId = addFriend.getApplicationId().toString();
                Log.d(TAG, "  " + applicationId);
                Toast.makeText(context1, "Button clicked for Name " +name1 , Toast.LENGTH_SHORT).show();
                new FriendAcceptTask().execute(applicationId,friendName);

            }
        });
        holder.btnReject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context1, "Clicked", Toast.LENGTH_SHORT).show();
                addFriend = mData.get(position);
                String name2 = addFriend.getFriendName();
                String friendName=addFriend.getFriendName().toString();
                applicationId = addFriend.getApplicationId().toString();
               Log.d(TAG, "  " + applicationId);
                Toast.makeText(context1, "Button clicked for Name " + name2, Toast.LENGTH_SHORT).show();
                new FriendRejectTask().execute(applicationId,friendName);

            }
        });
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView tv_name;
        private TextView tv_email;
        private Button btnAccept, btnReject;

        public MyViewHolder(View itemView) {
            super(itemView);

            tv_name = (TextView) itemView.findViewById(R.id.name_friend);
            tv_email = (TextView) itemView.findViewById(R.id.email_friend);
            btnAccept = (Button) itemView.findViewById(R.id.btnAccept);
            btnReject = (Button) itemView.findViewById(R.id.btnReject);
        }
    }

    public class FriendAcceptTask extends AsyncTask<String, String, String> {
        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(context1);
            progressDialog.setMessage("Wait...");
            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {
            applicationId = params[0];
            //loggedInUserID = params[1];
            Log.d(TAG, "  " + applicationId);
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("ApplicationFriendAssociationId", applicationId);
                Log.d(TAG, " " + applicationId);
                jsonObject.put("Status", "Accept");
                jsonPostString = jsonObject.toString();
                response1 = httpRequestProcessor.pOSTRequestProcessor(jsonPostString, urlAcceptReject);
                jsonResponseString = response1.getJsonResponseString();

            } catch (JSONException e) {
                e.printStackTrace();
            }
            return jsonResponseString;

        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            progressDialog.dismiss();
            Log.d("Response String", s);
            try {
                JSONObject jsonObject = new JSONObject(s);
                int responseData = jsonObject.getInt("responseData");
                if (responseData == 1) {
                    Toast.makeText(context1, "Request Accept successfully", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context1, "Request already send", Toast.LENGTH_SHORT).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
        public class FriendRejectTask extends AsyncTask<String, String, String> {
            ProgressDialog progressDialog;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                progressDialog = new ProgressDialog(context1);
                progressDialog.setMessage("Wait...");
                progressDialog.show();
            }

            @Override
            protected String doInBackground(String... params) {
                applicationId = params[0];
                //loggedInUserID = params[1];
                Log.d(TAG, "  " + applicationId);
                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put("ApplicationFriendAssociationId", applicationId);
                    Log.d(TAG, " "+applicationId);
                    jsonObject.put("Status","Reject");
                    jsonPostString = jsonObject.toString();
                    response1 = httpRequestProcessor.pOSTRequestProcessor(jsonPostString, urlAcceptReject);
                    jsonResponseString = response1.getJsonResponseString();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                return jsonResponseString;

            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                progressDialog.dismiss();
                Log.d("Response String", s);
                try {
                    JSONObject jsonObject = new JSONObject(s);
                    int responseData = jsonObject.getInt("responseData");
                    if (responseData == 1) {
                        Toast.makeText(context1, "Request Reject successfully", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(context1, "Request is Deleted", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        }
}