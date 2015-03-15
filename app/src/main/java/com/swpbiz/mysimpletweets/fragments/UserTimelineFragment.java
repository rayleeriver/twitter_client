package com.swpbiz.mysimpletweets.fragments;

import android.os.Bundle;

public class UserTimelineFragment extends TweetsListFragment {

    public static UserTimelineFragment newInstance(String screenName) {
        UserTimelineFragment userTimelineFragment = new UserTimelineFragment();
        Bundle args = new Bundle();
        args.putString("screen_name", screenName);
        userTimelineFragment.setArguments(args);
        return userTimelineFragment;
    }

    @Override
    protected void populateTimeline(boolean reset) {
        super.populateTimeline(reset);
        getClient().getUserTimeLine(getArguments().getString("screen_name"), new MyJsonHttpResponseHandler());
    }
}
