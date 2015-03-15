package com.swpbiz.mysimpletweets.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.widget.Toast;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.swpbiz.mysimpletweets.TwitterApplication;
import com.swpbiz.mysimpletweets.TwitterClient;
import com.swpbiz.mysimpletweets.models.Tweet;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONObject;

public class UserTimelineFragment extends TweetsListFragment {
    private TwitterClient client;

    public static UserTimelineFragment newInstance(String screenName) {
        UserTimelineFragment userTimelineFragment = new UserTimelineFragment();
        Bundle args = new Bundle();
        args.putString("screen_name", screenName);
        userTimelineFragment.setArguments(args);
        return userTimelineFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        client = TwitterApplication.getRestClient();  // singleton client
        populateTimeline(true);
    }

    private void populateTimeline(boolean reset) {
        if (reset) Tweet.lastLowestId = Long.MAX_VALUE;

        client.getUserTimeLine(getArguments().getString("screen_name"), new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray json) {
                addAll(Tweet.fromJsonArray(json));
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                Toast.makeText(getActivity(), errorResponse.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
