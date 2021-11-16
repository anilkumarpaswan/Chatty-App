package com.chatty;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.chatty.apiConfiguration.ApiConfiguration;
import com.chatty.httpRequestProcessor.HttpRequestProcessor;
import com.chatty.httpRequestProcessor.Response;
import com.google.android.gms.common.api.GoogleApiClient;

import org.json.JSONException;
import org.json.JSONObject;


public class Login_Activity extends AppCompatActivity {

    Context context;
    int MODE_PRIVATE = 0;
    TextInputLayout email_layout, password_layout;
    private ImageView iv;
    private TextView txtForget, txtLoginForm;
    private String message, address, emailID, phone, userName, id;
    private int userID;
    private SharedPreferences sp;
    private SharedPreferences.Editor editor;
    private Button btn1, btn2, btn3;
    private EditText user_name, user_password;
    private String username, password;
    private ApiConfiguration apiConfiguration;
    private Response response;
    private HttpRequestProcessor httpRequestProcessor;
    private String jsonPostString, jsonResponseString, baseUrl, urlLogin;
    private boolean success;
    private String errorMessage;
    private String jsonStringToPost;


    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);
        btn1 = (Button) findViewById(R.id.btn1);
        btn2 = (Button) findViewById(R.id.btn2);
        user_name = (EditText) findViewById(R.id.username);
        user_password = (EditText) findViewById(R.id.password);

        response = new Response();
        apiConfiguration = new ApiConfiguration();
        httpRequestProcessor = new HttpRequestProcessor();
        baseUrl = apiConfiguration.getApi();
        urlLogin = baseUrl + "AccountAPI/GetLoginUser";

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (user_name.getText().toString().equalsIgnoreCase("") || user_name.getText().toString().isEmpty()) {
                    Toast.makeText(Login_Activity.this, "Username can't be empty.", Toast.LENGTH_SHORT).show();
                    user_name.requestFocus();
                }
                else if (user_password.getText().toString().equalsIgnoreCase("") || user_password.getText().toString().isEmpty()) {
                    Toast.makeText(Login_Activity.this, "Password can't be empty.", Toast.LENGTH_SHORT).show();
                    user_password.requestFocus();
                } else
                    new LoginTask().execute(user_name.getText().toString(), user_password.getText().toString());
            }
        });
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Login_Activity.this, RegistrationActivity.class);
                startActivity(intent);
            }
        });

    }

    public class LoginTask extends AsyncTask<String, String, String> {
        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(Login_Activity.this);
            progressDialog.setMessage("Please Wait...");
            progressDialog.setCancelable(false);
            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("UserName", params[0]);
                jsonObject.put("Password", params[1]);
                jsonStringToPost = jsonObject.toString();
                System.out.println("Payload for Login = " + jsonStringToPost);
                response = httpRequestProcessor.pOSTRequestProcessor(jsonStringToPost, urlLogin);
                jsonResponseString = response.getJsonResponseString();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return jsonResponseString;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            progressDialog.dismiss();
            //Log.d("Response String", s);

            try {
                JSONObject jsonObject = new JSONObject(s);
                errorMessage = jsonObject.getString("ErrorMessage");
                // success = jsonObject.getBoolean("success");
                // Log.d("Success", String.valueOf(success));
                // errorMessage = jsonObject.getString("ErrorMessage");
                String erMsg = "User Authenticated!!";

                if (errorMessage.equals(erMsg)) {
                    username = jsonObject.getString("Name");
                    id = jsonObject.getString("ApplicationUserId");
                    ///String friendname = jsonObject.getString("FriendName");
                    // String mid=jsonObject.getString("MemberId");
                    //Save data in shared preference
                    sp = getSharedPreferences("My_Pref", MODE_PRIVATE);
                    editor = sp.edit();
                    editor.putString("name_key", userName);
                    editor.putString("ApplicationUserId_key", id);
                    //editor.putString("FriendName", friendname);
                    //editor.putString("MemberId",mid);
                    editor.commit();

                    Toast.makeText(Login_Activity.this, "Welcome back " + username, Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(Login_Activity.this, NavigationDrawer.class));
                } else {
                    Toast.makeText(Login_Activity.this, errorMessage, Toast.LENGTH_LONG).show();
                }


            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

}
