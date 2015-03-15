package com.swpbiz.mysimpletweets.activities;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import com.astuetz.PagerSlidingTabStrip;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;
import com.swpbiz.mysimpletweets.R;
import com.swpbiz.mysimpletweets.TwitterApplication;
import com.swpbiz.mysimpletweets.TwitterClient;
import com.swpbiz.mysimpletweets.adapters.EndlessScrollListener;
import com.swpbiz.mysimpletweets.adapters.TweetsArrayAdapter;
import com.swpbiz.mysimpletweets.events.TweetsFetchedEvent;
import com.swpbiz.mysimpletweets.fragments.HomeTimelineFragment;
import com.swpbiz.mysimpletweets.fragments.MentionsTimelineFragment;
import com.swpbiz.mysimpletweets.fragments.TweetsListFragment;
import com.swpbiz.mysimpletweets.models.Tweet;
import com.swpbiz.mysimpletweets.models.User;

import org.apache.http.Header;
import org.apache.http.client.HttpResponseException;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

public class TimelineActivity extends ActionBarActivity {
    private static final int REQUEST_CODE = 2000;
    private TwitterClient client;
    private User currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline);
        client = TwitterApplication.getRestClient();  // singleton client

        getUserProfile();
        setupActionBar();

        // get the viewpager
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);

        // set adapter for the view page
        viewPager.setAdapter(new TweetsPagerAdapter(getSupportFragmentManager()));

        // find pager sliding tabs
        PagerSlidingTabStrip tabStrip = (PagerSlidingTabStrip) findViewById(R.id.tabs);

        // attac tabstrip to viewpager
        tabStrip.setViewPager(viewPager);
    }

    private void setupActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setIcon(R.drawable.ic_tweeter);
        actionBar.setBackgroundDrawable(new ColorDrawable((Color.parseColor("#55acee"))));
    }

    public void getUserProfile() {
        client.getUserProfile(new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                currentUser = User.fromJson(response);
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_timeline, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.action_compose) {
//            if (currentUser != null) {
                showComposeDialog();
//                return true;
//            } else {
//                Toast.makeText(this, "Can't detect current user, network issue?", Toast.LENGTH_LONG).show();
//                return false;
//            }
        } else if (id == R.id.action_profile) {
            Intent intent = new Intent(this, ProfileActivity.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

    private void showComposeDialog() {
        Intent intent = new Intent(TimelineActivity.this, ComposeActivity.class);
        intent.putExtra("currentUser", currentUser);
        startActivityForResult(intent, REQUEST_CODE);
    }
//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        if (requestCode == REQUEST_CODE) {
//            Log.d("debug", "onActivityResult");
//            if (data != null) {
//                boolean success = data.getBooleanExtra("success", false);
//                if (success) {
//                    tweetsAdapter.clear();
//                    populateTimeline(true);
//                }
//            }
//        }
//    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
        super.onBackPressed();
    }

    public class TweetsPagerAdapter extends FragmentPagerAdapter {
        final int PAGE_COUNT=2;
        private String tabTitles[] = {"Home", "Mentions"};

        public TweetsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            if (position == 0) {
                return new HomeTimelineFragment();
            } else if (position == 1) {
                return new MentionsTimelineFragment();
            } else {
                return null;
            }
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return tabTitles[position];
        }

        @Override
        public int getCount() {
            return tabTitles.length;
        }
    }
}