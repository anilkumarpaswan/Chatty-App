package com.chatty;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.chatty.apiConfiguration.ApiConfiguration;
import com.chatty.httpRequestProcessor.HttpRequestProcessor;
import com.chatty.httpRequestProcessor.Response;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;

import org.json.JSONException;
import org.json.JSONObject;

public class RegistrationActivity extends AppCompatActivity {
    private EditText edtTxt1, edtTxt2, edtTxt3, edtTxt4, edtTxt5, edtTxt6;
    private TextView txt1, txt2, txt3, txt4, txt5, txt6, txt7;
    private Button btn;

    private String name, adress, mobno, username, password, email;
    private ApiConfiguration apiConfiguration;
    private Response response;
    private HttpRequestProcessor httpRequestProcessor;
    private String jsonPostString, jsonResponseString;
    private String baseUrl, registerUrl;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registration_activity);
        TextView txt1 = (TextView) findViewById(R.id.txt1);
        TextView txt2 = (TextView) findViewById(R.id.txt2);
        TextView txt3 = (TextView) findViewById(R.id.txt3);
        TextView txt4 = (TextView) findViewById(R.id.txt4);
        TextView txt5 = (TextView) findViewById(R.id.txt5);
        TextView txt6 = (TextView) findViewById(R.id.txt6);
        TextView txt7 = (TextView) findViewById(R.id.txt7);
        final EditText edtTxt1 = (EditText) findViewById(R.id.username);
        final EditText edtTxt2 = (EditText) findViewById(R.id.password);
        final EditText edtTxt3 = (EditText) findViewById(R.id.edtTxt3);
        final EditText edtTxt4 = (EditText) findViewById(R.id.edtTxt4);
        final EditText edtTxt5 = (EditText) findViewById(R.id.edtTxt5);
        final EditText edtTxt6 = (EditText) findViewById(R.id.edtTxt6);
        Button btn = (Button) findViewById(R.id.btn);

        response = new Response();
        apiConfiguration = new ApiConfiguration();
        httpRequestProcessor = new HttpRequestProcessor();
        baseUrl = apiConfiguration.getApi();
        registerUrl = baseUrl + "AccountAPI/SaveApplicationUser";

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                name = edtTxt1.getText().toString().trim();
                adress = edtTxt2.getText().toString().trim();
                email = edtTxt3.getText().toString().trim();
                mobno = edtTxt4.getText().toString().trim();
                username = edtTxt5.getText().toString().trim();
                password = edtTxt6.getText().toString().trim();

                new RegistrationTask().execute(name, adress, email, mobno, username, password);
                //Toast.makeText(RegistrationActivity.this,"Registered Successful",Toast.LENGTH_LONG).show();

            }
        });
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
       // client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    public void click(View view) {
        Intent intent = new Intent(this, Login_Activity.class);
        startActivity(intent);
    }
 
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("Registration Page") // TODO: Define a title for the content shown.
                // TODO: Make sure this auto-generated URL is correct.
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }

   /* @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        AppIndex.AppIndexApi.start(client, getIndexApiAction());
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        AppIndex.AppIndexApi.end(client, getIndexApiAction());
        client.disconnect();
    }
*/
    private class RegistrationTask extends AsyncTask<String, String, String> {
        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(RegistrationActivity.this);
            progressDialog.setMessage("Wait...");
            progressDialog.show();


        }

        @Override
        protected String doInBackground(String... params) {
            name = params[0];
            adress = params[1];
            email = params[2];
            mobno = params[3];
            username = params[4];
            password = params[5];
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("Name", name);
                jsonObject.put("Address", adress);
                jsonObject.put("EmailId", email);
                jsonObject.put("Phone", mobno);
                jsonObject.put("UserName", username);
                jsonObject.put("Password", password);

                jsonPostString = jsonObject.toString();
                response = httpRequestProcessor.pOSTRequestProcessor(jsonPostString, registerUrl);
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
            Log.d("Response String", s);
            try {
                JSONObject jsonObject = new JSONObject(s);
                int responseData = jsonObject.getInt("responseData");
                if (responseData == 1) {
                    Toast.makeText(RegistrationActivity.this, "User Registered Successfully", Toast.LENGTH_LONG).show();
                    startActivity(new Intent(RegistrationActivity.this, Login_Activity.class));
                } else if (responseData == 2) {
                    Toast.makeText(RegistrationActivity.this, "User Already exists.Please Try again...", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(RegistrationActivity.this, "Some unexpected error.Please try Again...", Toast.LENGTH_LONG).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}



