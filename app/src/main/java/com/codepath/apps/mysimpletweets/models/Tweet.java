package com.codepath.apps.mysimpletweets.models;

import android.text.format.DateUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class Tweet {
    // list out the attributes

    public static long lastLowestId = Long.MAX_VALUE;
    private String body;
    private long uid;  // tweet's id
    private String createdAt;
    private User user;
    private String createdAtTimeSpan;

    // parse json and store the data
    public static Tweet fromJson(JSONObject json) {
        Tweet tweet = new Tweet();
        try {
            tweet.body = json.getString("text");
            tweet.uid = json.getLong("id");
            if (tweet.uid < lastLowestId) {
                lastLowestId = tweet.uid;
            } else if (tweet.uid == lastLowestId) {
                // overlapping item from pagination, skip it.
                return null;
            }
            tweet.createdAt = json.getString("created_at");
            tweet.user = User.fromJson(json.getJSONObject("user"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return tweet;
    }

    public static List<Tweet> fromJsonArray(JSONArray json) {
        List<Tweet> tweets = new ArrayList<Tweet>();
        for (int i = 0; i < json.length(); i++) {
            try {
                JSONObject tweetJson = json.getJSONObject(i);
                Tweet tweet = fromJson(tweetJson);
                if (tweet != null) {
                    tweets.add(tweet);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return tweets;
    }


    // encapsulate state logic or display logic


    // Getters
    public String getBody() {
        return body;
    }

    public long getUid() {
        return uid;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public User getUser() {
        return user;
    }

    public String getCreatedAtTimeSpan() {
        return getRelativeTimeAgo(createdAt);
    }

    public String getRelativeTimeAgo(String rawJsonDate) {
        String twitterFormat = "EEE MMM dd HH:mm:ss ZZZZZ yyyy";
        SimpleDateFormat sf = new SimpleDateFormat(twitterFormat, Locale.ENGLISH);
        sf.setLenient(true);

        String relativeDate = "";
        try {
            long dateMillis = sf.parse(rawJsonDate).getTime();
            relativeDate = DateUtils.getRelativeTimeSpanString(dateMillis,
                    System.currentTimeMillis(), DateUtils.SECOND_IN_MILLIS, DateUtils.FORMAT_ABBREV_ALL).toString();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        relativeDate = relativeDate.replace(" mins", "m");
        relativeDate = relativeDate.replace(" min", "m");
        relativeDate = relativeDate.replace(" hours", "h");
        relativeDate = relativeDate.replace(" hour", "h");
        relativeDate = relativeDate.replace(" days", "d");
        relativeDate = relativeDate.replace(" day", "d");

        return relativeDate;
    }
}
