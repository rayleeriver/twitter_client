package com.swpbiz.mysimpletweets.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.swpbiz.mysimpletweets.R;
import com.swpbiz.mysimpletweets.TwitterApplication;
import com.swpbiz.mysimpletweets.TwitterClient;
import com.swpbiz.mysimpletweets.adapters.EndlessScrollListener;
import com.swpbiz.mysimpletweets.adapters.TweetsArrayAdapter;
import com.swpbiz.mysimpletweets.models.Tweet;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public abstract class TweetsListFragment extends Fragment {

    private TweetsArrayAdapter tweetsAdapter;
    private ArrayList<Tweet> tweets;
    private ListView lvTweets;
    private SwipeRefreshLayout swipeRefreshLayout;

    private TwitterClient client;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_tweets_list, container, false);
        lvTweets = (ListView) v.findViewById(R.id.lvTweets);
        lvTweets.setOnScrollListener(new EndlessScrollListener() {
            @Override
            public void onLoadMore(int page, int totalItemsCount) {
                populateTimeline(page==0?true:false);
            }
        });
        lvTweets.setAdapter(tweetsAdapter);
        tweetsAdapter.clear();

        swipeRefreshLayout = (SwipeRefreshLayout) v.findViewById(R.id.swipe_refresh_layout);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                populateTimeline(true);
            }
        });
        return v;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        tweets = new ArrayList<Tweet>();
        tweetsAdapter = new TweetsArrayAdapter(getActivity(), tweets);
        client = TwitterApplication.getRestClient();  // singleton client
        populateTimeline(true);

        super.onCreate(savedInstanceState);
    }

    public void addAll(List<Tweet> tweets) {
        tweetsAdapter.addAll(tweets);
    }

    protected void populateTimeline(boolean reset) {
        if (reset) {
            Tweet.lastLowestId = Long.MAX_VALUE;
            tweetsAdapter.clear();
        }
    }

    protected class MyJsonHttpResponseHandler extends JsonHttpResponseHandler {
        @Override
        public void onSuccess(int statusCode, Header[] headers, JSONArray json) {
            addAll(Tweet.fromJsonArray(json));

            if (swipeRefreshLayout != null) {
                swipeRefreshLayout.setRefreshing(false);
            }
        }

        @Override
        public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
            Toast.makeText(getActivity(), errorResponse.toString(), Toast.LENGTH_SHORT).show();
        }
    }

    public TwitterClient getClient() {
        return client;
    }


}
