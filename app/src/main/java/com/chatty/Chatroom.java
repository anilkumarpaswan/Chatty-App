package com.chatty;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.chatty.Pojos.ChatMessage;
import com.chatty.Pojos.MyRecievedMessages;
import com.chatty.customAdapter.MessageAdapter;
import com.chatty.httpRequestProcessor.HttpRequestProcessor;
import com.chatty.apiConfiguration.ApiConfiguration;
import com.chatty.httpRequestProcessor.Response;
import com.chatty.intentService.ChatService;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class Chatroom extends AppCompatActivity {
    private ListView lv;
    private EditText edtMessage;
    MyRecievedMessages myRecievedMessagess;
    private HttpRequestProcessor httpRequestProcessor;
    private Response response;
    private String baseURL, urlSubmitMessage, jsonStringToPost, jsonResponseString, loggedInUserID, sentmessage;
    private String friendID;
    String friend_ID;
    ArrayList<String> strings = new ArrayList<>();
    String user_ID;
    private ArrayAdapter<String> arrayAdapter;
    private ArrayAdapter<MyRecievedMessages> arrayAdapterchat;
    private List<MyRecievedMessages> myRecievedMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatroom);
        Button btnSend = findViewById(R.id.btnSend);
        edtMessage = findViewById(R.id.edtMessage);
        TextView friendName = findViewById(R.id.txtName);
        lv = findViewById(R.id.lvMessage);
        httpRequestProcessor = new HttpRequestProcessor();
        response = new Response();
        ApiConfiguration apiConfiguration = new ApiConfiguration();
        String name = getIntent().getStringExtra("Friend_Name");
        friend_ID = getIntent().getStringExtra("Friend_ID");
        user_ID = getIntent().getStringExtra("UserID");
        System.out.println(name);
        friendName.setText(name);
        myRecievedMessage = new ArrayList<>();
        arrayAdapterchat = new MessageAdapter(this, R.layout.item_chat_left, myRecievedMessage, user_ID);
        lv.setAdapter(arrayAdapterchat);
        baseURL = apiConfiguration.getApi();
        urlSubmitMessage = baseURL + "MessageAPI/SubmitMessage";
        new getMySentMessages().execute(user_ID, friend_ID, baseURL + "MessageAPI/getMySentMessage");
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sentmessage = edtMessage.getText().toString().trim();
                if (edtMessage.getText().toString().trim().equals("")) {
                    Toast.makeText(Chatroom.this, "Please input some text...", Toast.LENGTH_SHORT).show();
                } else {
                    new SubmitMessagesTask().execute(user_ID, sentmessage, friend_ID);
                    edtMessage.setText("");
                }
            }
        });
    }

    public void view(ArrayList<String> strings) {
        System.out.println("Chatroom.view");
        arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, strings) {
            @NonNull
            @Override
            public View getView(int position, View convertView, @NonNull ViewGroup parent) {
                // Cast the current view as a TextView
                TextView tv = (TextView) super.getView(position, convertView, parent);
                tv.setGravity(Gravity.RIGHT | Gravity.END | Gravity.CENTER_VERTICAL);
                return tv;
            }
        };
        lv.setAdapter(arrayAdapter);

    }

    @SuppressLint("StaticFieldLeak")
    public class SubmitMessagesTask extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... params) {
            loggedInUserID = params[0];
            sentmessage = params[1];
            friendID = params[2];
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("SenderId", loggedInUserID);
                jsonObject.put("MessageBody", sentmessage);
                jsonObject.put("ReceiverId", friendID);
                jsonStringToPost = jsonObject.toString();
                System.out.println("jsonStringToPost = " + jsonStringToPost);
                response = httpRequestProcessor.pOSTRequestProcessor(jsonStringToPost, urlSubmitMessage);
                jsonResponseString = response.getJsonResponseString();

            } catch (JSONException e) {
                e.printStackTrace();
            }

            return jsonResponseString;
        }


        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            try {
                JSONObject jsonObject = new JSONObject(s);
                int responseData = jsonObject.getInt("responseData");
                if (responseData == 1) {
                    new getMySentMessages().execute(loggedInUserID, friend_ID, baseURL + "MessageAPI/getMySentMessage");
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }

    @SuppressLint("StaticFieldLeak")
    public class getMySentMessages extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... params) {
            loggedInUserID = params[0];
            friendID = params[1];
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("SenderId", loggedInUserID);
                jsonObject.put("ReceiverID", friendID);
                jsonStringToPost = jsonObject.toString();
                System.out.println("jsonStringToPost = " + jsonStringToPost);
                response = httpRequestProcessor.pOSTRequestProcessor(jsonStringToPost, params[2]);
                jsonResponseString = response.getJsonResponseString();

            } catch (JSONException e) {
                e.printStackTrace();
            }

            return jsonResponseString;
        }


        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            try {
                JSONObject jsonObject = new JSONObject(s);
                JSONArray jsonArray = jsonObject.getJSONArray("responseData");
                strings = new ArrayList<>();
                if (jsonArray.length() != 0) {
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject object = (JSONObject) jsonArray.get(i);
                        myRecievedMessagess = new MyRecievedMessages(object.getString("Id"),
                                object.getString("SenderId"),
                                object.getString("ReceiverId"),
                                object.getString("Subject"),
                                object.getString("MessageBody"),
                                object.getString("ParentMessageId"),
                                object.getString("CreatedDate"),
                                object.getString("IsActive"),
                                object.getString("SenderName"),
                                object.getString("ReceiverName"));
                        if (!object.getString("MessageBody").equalsIgnoreCase("null") && !object.getString("MessageBody").equalsIgnoreCase(""))
                            myRecievedMessage.add(myRecievedMessagess);
                    }
                    arrayAdapterchat.notifyDataSetChanged();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }
}
