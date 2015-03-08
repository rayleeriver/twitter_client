package com.codepath.apps.mysimpletweets.activities;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.codepath.apps.mysimpletweets.R;
import com.codepath.apps.mysimpletweets.TwitterApplication;
import com.codepath.apps.mysimpletweets.TwitterClient;
import com.codepath.apps.mysimpletweets.models.User;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.squareup.picasso.Picasso;

import org.apache.http.Header;
import org.json.JSONObject;

public class ComposeActivity extends ActionBarActivity {

    private TwitterClient client;
    private User currentUser;
    private EditText etBody;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compose);
        currentUser = getIntent().getParcelableExtra("currentUser");

        ImageView ivProfileImage = (ImageView) findViewById(R.id.ivProfileImage);
        TextView tvUsername = (TextView) findViewById(R.id.tvUsername);
        TextView tvScreenName = (TextView) findViewById(R.id.tvScreenName);
        etBody = (EditText) findViewById(R.id.etBody);

        Picasso.with(this).load(currentUser.getProfileImageUrl()).into(ivProfileImage);
        tvUsername.setText(currentUser.getName());
        tvScreenName.setText("@" + currentUser.getScreenName());

        ActionBar actionBar = getSupportActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable((Color.parseColor("#994099FF"))));

        client = TwitterApplication.getRestClient();  // singleton client
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_compose, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_tweet) {
            tweet(etBody.getText().toString());
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void tweet(String body) {
        client.tweet(body, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                // TODO:  Handle response code
                Intent i = new Intent();
                i.putExtra("success", true);
                setResult(TimelineActivity.REQUEST_CODE, i);
                finish();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                Toast.makeText(ComposeActivity.this, errorResponse.toString(), Toast.LENGTH_LONG).show();
            }
        });
    }

}
