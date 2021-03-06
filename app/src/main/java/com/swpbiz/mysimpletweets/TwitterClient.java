package com.swpbiz.mysimpletweets;

import android.content.Context;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.swpbiz.mysimpletweets.models.Tweet;
import com.codepath.oauth.OAuthBaseClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.scribe.builder.api.Api;
import org.scribe.builder.api.TwitterApi;

/*
 * 
 * This is the object responsible for communicating with a REST API. 
 * Specify the constants below to change the API being communicated with.
 * See a full list of supported API classes: 
 *   https://github.com/fernandezpablo85/scribe-java/tree/master/src/main/java/org/scribe/builder/api
 * Key and Secret are provided by the developer site for the given API i.e dev.twitter.com
 * Add methods for each relevant endpoint in the API.
 * 
 * NOTE: You may want to rename this object based on the service i.e TwitterClient or FlickrClient
 * 
 */
public class TwitterClient extends OAuthBaseClient {
    public static final Class<? extends Api> REST_API_CLASS = TwitterApi.class; // Change this
    public static final String REST_URL = "https://api.twitter.com/1.1"; // Change this, base API URL
    public static final String REST_CONSUMER_KEY = "NsjxrOl7YciuS3EPs6j7MAqqR";       // Change this
    public static final String REST_CONSUMER_SECRET = "GNyz9uPYXItD32502Ugq6Esos8uLPJtfcP9b5BMuMSSqkIY8u8"; // Change this
    public static final String REST_CALLBACK_URL = "oauth://cpsimpletweets"; // Change this (here and in manifest)

    public TwitterClient(Context context) {
        super(context, REST_API_CLASS, REST_URL, REST_CONSUMER_KEY, REST_CONSUMER_SECRET, REST_CALLBACK_URL);
    }

    public void getUserTimeLine(String screenName, AsyncHttpResponseHandler jsonHttpResponseHandler) {
        String apiUrl = getApiUrl("statuses/user_timeline.json");
        RequestParams params = new RequestParams();
        params.put("screen_name", screenName);
        client.get(apiUrl, params, jsonHttpResponseHandler);
    }

    public void getUserProfile(AsyncHttpResponseHandler jsonHttpResponseHandler) {
        String apiUrl = getApiUrl("account/verify_credentials.json");
        client.get(apiUrl, null, jsonHttpResponseHandler);
    }

    public void getUsersShow(String screenName, AsyncHttpResponseHandler jsonHttpResponseHandler) {
        String apiUrl = getApiUrl("users/show.json");
        RequestParams params = new RequestParams();
        params.put("screen_name", screenName);
        client.get(apiUrl, params, jsonHttpResponseHandler);
    }

    public void getHomeTimeline(AsyncHttpResponseHandler jsonHttpResponseHandler) {
        String apiUrl = getApiUrl("statuses/home_timeline.json");
        RequestParams params = new RequestParams();
        if (Tweet.lastLowestId != Long.MAX_VALUE) {
            params.put("max_id", Tweet.lastLowestId);
        }
        client.get(apiUrl, params, jsonHttpResponseHandler);
    }

    public void getMentionsTimeline(JsonHttpResponseHandler jsonHttpResponseHandler) {
        String apiUrl = getApiUrl("statuses/mentions_timeline.json");
        RequestParams params = new RequestParams();
        client.get(apiUrl, params, jsonHttpResponseHandler);
    }

    public void tweet(String body, AsyncHttpResponseHandler handler) {
        String apiUrl = getApiUrl("statuses/update.json");
        RequestParams params = new RequestParams();
        params.put("status", body);
        client.post(apiUrl, params, handler);
    }

	/* 1. Define the endpoint URL with getApiUrl and pass a relative path to the endpoint
     * 	  i.e getApiUrl("statuses/home_timeline.json");
	 * 2. Define the parameters to pass to the request (query or body)
	 *    i.e RequestParams params = new RequestParams("foo", "bar");
	 * 3. Define the request method and make a call to the client
	 *    i.e client.get(apiUrl, params, handler);
	 *    i.e client.post(apiUrl, params, handler);
	 */
}