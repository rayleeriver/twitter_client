package com.swpbiz.mysimpletweets.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.squareup.otto.Bus;
import com.swpbiz.mysimpletweets.R;
import com.swpbiz.mysimpletweets.adapters.TweetsArrayAdapter;
import com.swpbiz.mysimpletweets.models.Tweet;
import com.swpbiz.mysimpletweets.models.User;

import java.util.ArrayList;
import java.util.List;

public class TweetsListFragment extends Fragment{

    private TweetsArrayAdapter tweetsAdapter;
    private ArrayList<Tweet> tweets;
    private ListView lvTweets;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_tweets_list, container, false);
        lvTweets = (ListView) v.findViewById(R.id.lvTweets);
        lvTweets.setAdapter(tweetsAdapter);
        tweetsAdapter.clear();
        return v;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        tweets = new ArrayList<Tweet>();
        tweetsAdapter = new TweetsArrayAdapter(getActivity(), tweets);
        super.onCreate(savedInstanceState);
    }

    public void addAll(List<Tweet> tweets) {
        tweetsAdapter.addAll(tweets);
    }
}
