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

import com.chatty.RegisterUser;
import com.chatty.apiConfiguration.ApiConfiguration;
import com.chatty.httpRequestProcessor.HttpRequestProcessor;
import com.chatty.httpRequestProcessor.Response;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;


public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {

    Context mcontext;
    List<RegisterUser> mData;
    SharedPreferences sp;
    String loggedInUserID;
    String TAG = "Helloo";
    RegisterUser registerUser;
    private List<RegisterUser> listRegisterUser;
    private HttpRequestProcessor httpRequestProcessor;
    private String response;
    private ApiConfiguration apiConfiguration;
    private String baseURL, urlAddFriend;
    private String jsonPostString, jsonResponseString;
    private Button btnAdd;
    private ArrayList<RegisterUser> registerUsers;
    private Response response1;
    private String memberId, friendId, requestId, createdBy, modifiedBy, userName, errorMessage;

    public RecyclerViewAdapter(Context mcontext, List<RegisterUser> mData) {
        this.mcontext = mcontext;
        this.mData = mData;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mcontext).inflate(R.layout.item_registeruser, parent, false);
        MyViewHolder vHolder = new MyViewHolder(v);

        //Initialization
        httpRequestProcessor = new HttpRequestProcessor();
        response1 = new Response();

        apiConfiguration = new ApiConfiguration();
        //Getting base url
        baseURL = apiConfiguration.getApi();
        urlAddFriend = baseURL + "ApplicationFriendAPI/AddFriendRequest";
        sp = mcontext.getSharedPreferences("My_Pref", MODE_PRIVATE);

        return vHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {

        holder.tv_name.setText(mData.get(position).getName());
        holder.tv_email.setText(mData.get(position).getEmailID());
        holder.btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser = mData.get(position);
                String memberID = registerUser.getMemberId();
                loggedInUserID = sp.getString("ApplicationUserId_key", "");
                Log.d(TAG, "  " + memberID + loggedInUserID);
                Toast.makeText(mcontext, "Button clicked for Name " + registerUser.getName(), Toast.LENGTH_SHORT).show();
                new AddFriendTask().execute(memberID, loggedInUserID);

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
        private Button btnAdd;


        public MyViewHolder(View itemView) {
            super(itemView);

            tv_name = (TextView) itemView.findViewById(R.id.name_reg);
            tv_email = (TextView) itemView.findViewById(R.id.email_reg);
            btnAdd = (Button) itemView.findViewById(R.id.btnAdd);

        }
    }

    public class AddFriendTask extends AsyncTask<String, String, String> {
        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(mcontext);
            progressDialog.setMessage("Please Wait...");
            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {
            memberId = params[0];
            loggedInUserID = params[1];
            Log.d(TAG, "  " + memberId + loggedInUserID);
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("MemberId",loggedInUserID );
                jsonObject.put("FriendId", memberId);
                jsonObject.put("RequestBy", loggedInUserID);
                jsonObject.put("CreatedBy", loggedInUserID);
                jsonObject.put("ModifiedBy", loggedInUserID);
                jsonPostString = jsonObject.toString();
                System.out.println("jsonfor add friend = " + jsonPostString);
                response1 = httpRequestProcessor.pOSTRequestProcessor(jsonPostString, urlAddFriend);
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
                if (responseData == 2) {
                    Toast.makeText(mcontext, "Request send successfully", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(mcontext, "error", Toast.LENGTH_SHORT).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
