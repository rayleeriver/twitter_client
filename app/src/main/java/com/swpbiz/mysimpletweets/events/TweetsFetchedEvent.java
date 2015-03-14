package com.swpbiz.mysimpletweets.events;

import com.swpbiz.mysimpletweets.models.Tweet;

import org.json.JSONArray;

import java.util.List;

public class TweetsFetchedEvent {
    private List<Tweet> fetchedTweets;

    public TweetsFetchedEvent(JSONArray json) {
        fetchedTweets = Tweet.fromJsonArray(json);
    }

    public List<Tweet> getFetchedTweets() {
        return fetchedTweets;
    }
}
