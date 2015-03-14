package com.swpbiz.mysimpletweets.models;

import android.text.format.DateUtils;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.inject.Inject;

import dagger.Module;

@Table(name = "tweets")
public class Tweet extends Model {
    // list out the attributes

    public static long lastLowestId = Long.MAX_VALUE;

    @Column(name="uid", unique=true, onUniqueConflict = Column.ConflictAction.REPLACE )
    private long uid;  // tweet's id

    @Column(name="user", onUpdate = Column.ForeignKeyAction.CASCADE, onDelete = Column.ForeignKeyAction.CASCADE)
    private User user;

    @Column(name="body")
    private String body;

    @Column(name="createdAt")
    private String createdAt;

    private String createdAtTimeSpan;

    public Tweet() {
        super();
    }

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
            tweet.save();
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

    public static List<Tweet> getAll() {
        return new Select()
                .from(Tweet.class)
                .execute();
    }

}
