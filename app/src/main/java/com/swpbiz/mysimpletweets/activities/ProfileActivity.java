package com.swpbiz.mysimpletweets.activities;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;
import com.swpbiz.mysimpletweets.R;
import com.swpbiz.mysimpletweets.TwitterApplication;
import com.swpbiz.mysimpletweets.TwitterClient;
import com.swpbiz.mysimpletweets.fragments.UserTimelineFragment;
import com.swpbiz.mysimpletweets.models.User;

import org.apache.http.Header;
import org.json.JSONObject;

import java.io.IOException;

public class ProfileActivity extends ActionBarActivity {
    TwitterClient client;
    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        client = TwitterApplication.getRestClient();

        String screenName = getIntent().getStringExtra("screen_name");
        user = getIntent().getParcelableExtra("user");
        if (user != null) {
            populateProfileHeader(user);
        } else {
            if (screenName != null) {
                client.getUsersShow(screenName, new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        populateProfileHeader(User.fromJson(response));
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                        if (throwable instanceof IOException) {
                        } else {
                            Log.d("debug", errorResponse.toString());
                        }
                    }
                });
            }
        }

        if (savedInstanceState == null) {
            UserTimelineFragment userTimelineFragment = UserTimelineFragment.newInstance(screenName);
            android.support.v4.app.FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.flContainer, userTimelineFragment);
            ft.commit();
        }

        setupActionBar();
    }

    private void setupActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable((Color.parseColor("#55acee"))));
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    private void populateProfileHeader(User user) {
        TextView tvName = (TextView) findViewById(R.id.tvUsername);
        TextView tvScreenName = (TextView) findViewById(R.id.tvScreenName);
        TextView tvTagLine = (TextView) findViewById(R.id.tvTagLine);
        TextView tvFollowers = (TextView) findViewById(R.id.tvFollowers);
        TextView tvFollowing = (TextView) findViewById(R.id.tvFollowings);
        ImageView ivProfileImage = (ImageView) findViewById(R.id.ivProfileImage);
//        ImageView ivUserHeaderBackground = (ImageView) findViewById(R.id.ivUserHeaderBackground);
        final RelativeLayout rlProfile = (RelativeLayout) findViewById(R.id.rlUserHeader);

        tvName.setText(user.getName());
        tvScreenName.setText("@" +user.getScreenName());
        tvTagLine.setText((user.getTagLine()));
        tvFollowers.setText(user.getFollowersCount() + " Follower");
        tvFollowing.setText(user.getFriendsCount() + " Following");
        Picasso.with(this).load(user.getProfileImageUrl()).into(ivProfileImage);
//        Picasso.with(this).load(user.getProfileBackgroundImageUrl()).centerCrop().fit().into(ivUserHeaderBackground);
    }
}
